package cn.tties.maint.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.TableCompanyAdapter;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.dao.AreaDao;
import cn.tties.maint.dao.IndustryDao;
import cn.tties.maint.entity.AreaEntity;
import cn.tties.maint.entity.IndustryEntity;
import cn.tties.maint.enums.ContractType;
import cn.tties.maint.holder.EleAccountLayoutHolder;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.AddWorkOrderActionParams;
import cn.tties.maint.httpclient.params.CompanyContractPostponeParams;
import cn.tties.maint.httpclient.params.CompanyParams;
import cn.tties.maint.httpclient.params.QueryEleAccountByCompanyIdParams;
import cn.tties.maint.httpclient.params.QueryMaintParams;
import cn.tties.maint.httpclient.params.QueryWorkOrderByCompanyIdParams;
import cn.tties.maint.httpclient.params.UpdateCompanyPersonActionParams;
import cn.tties.maint.httpclient.params.UpdateInfoCompanyParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.EleAccountResult;
import cn.tties.maint.httpclient.result.MbStaffResult;
import cn.tties.maint.httpclient.result.OrderResult;
import cn.tties.maint.util.DateUtils;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.PinYinUtils;
import cn.tties.maint.view.BaseCustomDialog;
import cn.tties.maint.view.CriProgressDialog;
import cn.tties.maint.widget.PtrListViewOnScrollListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 管理企业.
 */
@ContentView(R.layout.fragment_company_check)
public class CompanyListFragment extends BaseFragment {

    private static final String TAG = "CompanyCheckFragment";

    @ViewInject(R.id.table_company)
    private ListView companTable;

    @ViewInject(R.id.searchView)
    private SearchView searchView;

    @ViewInject(R.id.ptrClassicFrameLayout)
    private PtrClassicFrameLayout ptrClassicFrameLayout;

    private static final int GET_ELEACCOUNT = 1;
    private static final int GET_WORKORDER = 2;

    public static CompanyListFragment companyListFragmentInstance;

    private TableCompanyAdapter companyAdapter;
    private DelayLimitDialog delayLimitDialog;
    private CompanyInfoDialog companyInfoDialog;
    private PersonDialog personDialog;
    private OverhaulDialog overhaulDialog;
    private List<CompanyResult> companyList;
    private List<CompanyResult> serachList;
    private List<EleAccountResult> eleAccountList;
    //创建检修
    private OrderResult addWorkOrderActionResult;
    private List<MbStaffResult> queryMaintResultList;
    private ArrayAdapter<String> operationAdapter;
    private List<String> pinYinList;
    private List<String> pinYinAllList;
    private CriProgressDialog dialog;
    private CompanyResult curCompany;
    private PtrListViewOnScrollListener mPtrListViewOnScrollListener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        companyListFragmentInstance = this;
        dialog = new CriProgressDialog(CompanyListFragment.this.getActivity());
        initView();
        queryCompanyList();
        initPullRefresh();
        getMaintList();
    }

    public void queryCompanyList() {
        CompanyParams params = new CompanyParams();
        //登录用户ID
        params.setCreatorId(MyApplication.getUserInfo().getStaffId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {

            @Override
            public void onSuccess(String result) {
                companyList.clear();
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    //错误
                } else {
                    List<CompanyResult> list = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<CompanyResult>>() {
                    }.getType());
                    for (CompanyResult companyResult : list) {
                        companyList.add(companyResult);
                    }
                }
                pinYinList = new ArrayList<String>();
                pinYinAllList = new ArrayList<String>();
                for (CompanyResult companyResult : companyList) {
                    pinYinList.add(PinYinUtils.getPinYinHeadChar(companyResult.getCompanyName()));
                    pinYinAllList.add(PinYinUtils.getPinYin(companyResult.getCompanyName()));
                }
                companyAdapter.notifyDataSetChanged();
                ptrClassicFrameLayout.refreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    /**
     * 获取运维专员列表.
     */
    private void getMaintList() {
        queryMaintResultList = new ArrayList<>();
        HttpClientSend.getInstance().send(new QueryMaintParams(), new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), "获取运维专员失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> strList = new ArrayList<String>();
                queryMaintResultList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<MbStaffResult>>() {
                }.getType());
                for (MbStaffResult queryMaintResult : queryMaintResultList) {
                    strList.add(queryMaintResult.getStaffName());
                }
                operationAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, strList);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_ELEACCOUNT:
                    companyInfoDialog = new CompanyInfoDialog(CompanyListFragment.this.getActivity(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            companyInfoDialog.validator.validate();
                        }
                    });
                    companyInfoDialog.show();
                    break;
                case GET_WORKORDER:
                    overhaulDialog = new OverhaulDialog(CompanyListFragment.this.getActivity(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddWorkOrderActionParams addWorkOrderActionParams = new AddWorkOrderActionParams();
                            addWorkOrderActionParams.setCompanyId(curCompany.getCompanyId());
                            addWorkOrderActionParams.setStaffId(122);
                            addWorkOrderActionParams.setFromStaffId(MyApplication.getUserInfo().getStaffId());
                            addWorkOrderActionParams.setStartDate(DateUtils.stringToInt(overhaulDialog.editOveraulDate.getText().toString()));
                            addWorkOrderActionParams.setEndDate(DateUtils.stringToInt(overhaulDialog.editOveraulEndDate.getText().toString()));
                            HttpClientSend.getInstance().send(addWorkOrderActionParams, new BaseStringCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    try {
                                        BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                                        if (ret.getErrorCode() != 0) {
                                            Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(x.app(), "创建成功！", Toast.LENGTH_SHORT).show();
                                            overhaulDialog.dismiss();
                                            queryCompanyList();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        addWorkOrderActionResult = null;
                                        Toast.makeText(x.app(), "操作失败", Toast.LENGTH_SHORT).show();

                                    } finally {
                                    }
                                }
                            });
                        }
                    });
                    overhaulDialog.show();
                default:
                    break;
            }
        }

        ;
    };

    private void searchEleAccount() {
        QueryEleAccountByCompanyIdParams params = new QueryEleAccountByCompanyIdParams();
        params.setCompanyId(curCompany.getCompanyId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {

            @Override
            public void onSuccess(String result) {
                eleAccountList.clear();
                dialog.removeDialog();
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), "获取用电户号失败", Toast.LENGTH_SHORT).show();
                } else {
                    eleAccountList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<EleAccountResult>>() {
                    }.getType());
                    Message msg = Message.obtain(handler);
                    msg.what = GET_ELEACCOUNT;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    private void searchWorkOrder() {
        QueryWorkOrderByCompanyIdParams params = new QueryWorkOrderByCompanyIdParams();
        params.setCompanyId(curCompany.getCompanyId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                addWorkOrderActionResult = null;
                if (ret.getErrorCode() == 0) {
                    //Toast.makeText(x.app(), "获取检修时间失败", Toast.LENGTH_SHORT).show();
                    if (ret.getResult() == null) {

                    } else {
                        addWorkOrderActionResult = JsonUtils.deserialize(ret.getResult(), OrderResult.class);
                    }
                    Message msg = Message.obtain(handler);
                    msg.what = GET_WORKORDER;
                    handler.sendMessage(msg);
                }
            }
        });

    }

    private void initView() {
        companyList = new ArrayList<>();
        eleAccountList = new ArrayList<>();
        this.mPtrListViewOnScrollListener = new PtrListViewOnScrollListener(this.ptrClassicFrameLayout, true, false);
        companyAdapter = new MyAdapter(x.app(), companyList);
        companTable.setAdapter(companyAdapter);
        companTable.setOnScrollListener(this.mPtrListViewOnScrollListener);
        companTable.setTextFilterEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {//如果这个文字等于空
                    //清除listview的过滤
                    companyAdapter.setList(companyList);
                    companyAdapter.notifyDataSetChanged();
                } else {
                    serachList = new ArrayList<CompanyResult>();
                    // 过滤条件
                    String str = newText.toLowerCase();
                    // 循环变量数据源，如果有属性满足过滤条件，则添加到result中
                    for (int i = 0; i < companyList.size(); i++) {
                        CompanyResult entity = companyList.get(i);
                        if (entity.getCompanyName().contains(str)) {
                            serachList.add(entity);
                        } else {
                            String pinyinSet = pinYinList.get(i);
                            if (pinyinSet.startsWith(str)) {
                                serachList.add(entity);
                                continue;
                            }
                            String pinyinAllSet = pinYinAllList.get(i);
                            if (pinyinAllSet.startsWith(str)) {
                                serachList.add(entity);
                            }
                        }
                    }
                    companyAdapter.setList(serachList);
                    companyAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }

    private void initPullRefresh() {
        /**
         * 经典 风格的头部实现
         */
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                queryCompanyList();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    public class MyAdapter extends TableCompanyAdapter {

        public MyAdapter(Context context, List list) {
            super(context, list);
        }

        @Override
        public int getCount() {
            int ret = 0;
            if (list != null) {
                ret = list.size();
            }
            return ret;
        }

        @Override
        public CompanyResult getItem(int position) {
            return (CompanyResult) list.get(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getConvertView(convertView);
            final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if (position % 2 == 0) {
                viewHolder.layout.setBackgroundColor((ContextCompat.getColor(x.app(), R.color.table_bg)));
            } else {
                viewHolder.layout.setBackgroundColor((ContextCompat.getColor(x.app(), R.color.white)));
            }
            CompanyResult result = getItem(position);
            viewHolder.text1.setText(result.getCompanyName());
            viewHolder.text2.setText(ContractType.EXECUTING.getInfo());
            viewHolder.btn1.setText("延长期限");
            viewHolder.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curCompany = companyList.get(position);
                    delayLimitDialog = new DelayLimitDialog(CompanyListFragment.this.getActivity(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delayLimitDialog.validator.validate();
                        }
                    });
                    delayLimitDialog.show();
                }
            });
            viewHolder.btn2.setText("修改信息");
            viewHolder.btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.loadDialog("获取信息中...");
                    curCompany = companyList.get(position);
                    searchEleAccount();
                }
            });
            viewHolder.btn3.setText("修改联系人");
            viewHolder.btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curCompany = companyList.get(position);
                    personDialog = new PersonDialog(CompanyListFragment.this.getActivity(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            personDialog.validator.validate();
                        }
                    });
                    personDialog.show();

                }
            });
            viewHolder.btn4.setText("创建检修");
            viewHolder.btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curCompany = companyList.get(position);
                    searchWorkOrder();
                }
            });
            return convertView;
        }
    }

    class DelayLimitDialog extends BaseCustomDialog {

        //public Spinner spinnerMonthCount;
        @NotEmpty(message = "结束日期")
        public EditText editEndDate;
        @NotEmpty(message = "月服务费")
        public EditText editMonthMoney;

        public TextView textName;

        public DelayLimitDialog(Activity context, View.OnClickListener clickListener) {
            super(context, clickListener);
        }

        @Override
        protected void setContentView() {
            // 指定布局
            this.setContentView(R.layout.dialog_company_delay);

            textName = (TextView) findViewById(R.id.text_companyname);
            textName.setText(curCompany.getCompanyName());

            editMonthMoney = (EditText) findViewById(R.id.edit_month_money);
            editMonthMoney.setText(String.valueOf(curCompany.getMonthMoney()));
            editEndDate = (EditText) findViewById(R.id.edit_endDate);
            // editEndTime.setText(String.valueOf(curCompany.getMonthMoney()));
            editEndDate.setText(setNullEdit(curCompany.getEndDate().toString()));
            setDataEditText(editEndDate);
            super.setContentView();
        }

        @Override
        public void onValidationSucceeded() {
            String fee = editMonthMoney.getText().toString().trim();
            CompanyContractPostponeParams contractPostponeParams = new CompanyContractPostponeParams();
            contractPostponeParams.setCompanyId(curCompany.getCompanyId());
            contractPostponeParams.setMonthMoney(Double.valueOf(fee));
            contractPostponeParams.setEndDate(DateUtils.stringToInt(editEndDate.getText().toString()));
            HttpClientSend.getInstance().send(contractPostponeParams, new BaseStringCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                        if (ret.getErrorCode() != 0) {
                            Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(x.app(), "修改成功！", Toast.LENGTH_SHORT).show();
                            delayLimitDialog.dismiss();//关闭弹窗
                            queryCompanyList();
                        }
                    } catch (Exception e) {
                        Toast.makeText(x.app(), "操作失败", Toast.LENGTH_SHORT).show();
                    } finally {
                    }
                }
            });
        }
    }

    class CompanyInfoDialog extends BaseCustomDialog {

        @NotEmpty(message = "公司不能为空")
        public TextView textName;
        @NotEmpty(message = "公司简称不能为空")
        public TextView editCompanyShortName;
        @NotEmpty(message = "公司地址不能为空")
        public EditText editCompanyAddr;
        @NotEmpty(message = "代理人不能为空")
        public EditText editAgentName;
        @NotEmpty(message = "电号不能为空")
        public EditText editEleAccount;
        @NotEmpty(message = "容量不能为空")
        public EditText editEleVolume;
        @NotEmpty(message = "联系电话不能为空")
        public EditText editSmsTel;
        @NotEmpty(message = "变压器容量不能为空")
        public EditText editTransformerCapacity;
        @NotEmpty(message = "变压器个数不能为空")
        public EditText editTransformerCount;

        private Spinner spinnerIndustry;

        private Spinner spinnerArea;

        public LinearLayout layoutEleAccount;

        public ImageView imageAdd;

        List<EditText> eleAccountEditList;

        List<EditText> volumeEditList;

        private List<AreaEntity> areaList;

        private List<IndustryEntity> industryList;


        public TextView textMaintTel;

        @ViewInject(R.id.spinner_maint)
        private Spinner spinnerMaint;

        public CompanyInfoDialog(Activity context, View.OnClickListener clickListener) {
            super(context, clickListener);
        }

        @Override
        protected void setContentView() {
            // 指定布局
            this.setContentView(R.layout.dialog_company_info);

            textName = (TextView) findViewById(R.id.text_companyname);
            textName.setText(setNullEdit(curCompany.getCompanyName()));
            editCompanyShortName = (EditText) findViewById(R.id.edit_company_short_name);
            editCompanyShortName.setText(setNullEdit(curCompany.getCompanyShortName()));
            editCompanyAddr = (EditText) findViewById(R.id.edit_company_addr);
            editCompanyAddr.setText(setNullEdit(curCompany.getCompanyAddr()));
            editAgentName = (EditText) findViewById(R.id.edit_agent_name);
            editAgentName.setText(setNullEdit(curCompany.getAgentName()));
            editSmsTel = (EditText) findViewById(R.id.edit_sms_tel);
            editSmsTel.setText(setNullEdit(curCompany.getSmsTel()));
            editTransformerCapacity = (EditText) findViewById(R.id.edit_transformer_capacity);
            editTransformerCapacity.setText(setNullEdit(curCompany.getTransformerCapacity()));
            editTransformerCount = (EditText) findViewById(R.id.edit_transformer_count);
            editTransformerCount.setText(setNullEdit(setNullEdit(curCompany.getTransformerCount())));
            editEleAccount = (EditText) findViewById(R.id.edit_ele_account);
            editEleVolume = (EditText) findViewById(R.id.edit_ele_volume);
            textMaintTel = (TextView) findViewById(R.id.text_maint_tel);
            spinnerMaint = (Spinner) findViewById(R.id.spinner_maint);
            spinnerMaint.setAdapter(operationAdapter);
            for (int i = 0; i < queryMaintResultList.size(); i++) {
                MbStaffResult result = queryMaintResultList.get(i);
                if (result.getStaffId().equals(curCompany.getMaintStaffId())) {
                    spinnerMaint.setSelection(i);
                    textMaintTel.setText(result.getStaffTel());
                    break;
                }
            }
            spinnerMaint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    textMaintTel.setText(queryMaintResultList.get(i).getStaffTel());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            eleAccountEditList = new ArrayList<EditText>();
            eleAccountEditList.add(editEleAccount);

            volumeEditList = new ArrayList<EditText>();
            volumeEditList.add(editEleVolume);

            layoutEleAccount = (LinearLayout) findViewById(R.id.layout_ele_account);
            layoutEleAccount.removeAllViews();
            imageAdd = (ImageView) findViewById(R.id.image_add);

            if (eleAccountList != null && eleAccountList.size() > 0) {
                editEleAccount.setText(eleAccountList.get(0).getEleNo());
                editEleAccount.setId(eleAccountList.get(0).getEleAccountId());
                editEleVolume.setText(String.valueOf(eleAccountList.get(0).getVolume()));
                for (int i = 1; i < eleAccountList.size(); i++) {
                    final EleAccountLayoutHolder holder = new EleAccountLayoutHolder(layoutEleAccount);
                    EleAccountResult entity = eleAccountList.get(i);

                    final EditText temp = holder.editEleAccount;
                    temp.setText(entity.getEleNo());
                    temp.setId(entity.getEleAccountId());
                    final EditText temp2 = holder.editEleVolume;
                    temp2.setText(String.valueOf(entity.getVolume()));
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layoutEleAccount.removeView(holder.contentView);
                            eleAccountList.remove(temp);
                            volumeEditList.remove(temp2);
                        }
                    });
//                    layoutEleAccount.addView(holder.contentView);
                    eleAccountEditList.add(temp);
                    volumeEditList.add(temp2);
                }
            }

            imageAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EleAccountLayoutHolder holder = new EleAccountLayoutHolder(layoutEleAccount);
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layoutEleAccount.removeView(holder.contentView);
                            eleAccountList.remove(holder.editEleAccount);
                            volumeEditList.remove(holder.editEleVolume);
                        }
                    });
                    layoutEleAccount.addView(holder.contentView);
                    eleAccountEditList.add(holder.editEleAccount);
                    volumeEditList.add(holder.editEleVolume);
                }
            });

            spinnerArea = (Spinner) findViewById(R.id.spinner_area);
            areaList = AreaDao.getInstance().queryProvince();
            List<String> areaStr = new ArrayList<>();
            for (AreaEntity entity : areaList) {
                areaStr.add(entity.getAreaName());
            }
            ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, areaStr);
            spinnerArea.setAdapter(areaAdapter);
            for (int i = 0; i < areaList.size(); i++) {
                AreaEntity result = areaList.get(i);
                if (result.getAreaId().equals(curCompany.getAreaId())) {
                    spinnerArea.setSelection(i);
                    break;
                }
            }

            spinnerIndustry = (Spinner) findViewById(R.id.spinner_industry);
            industryList = IndustryDao.getInstance().queryAll();
            List<String> industryStr = new ArrayList<>();
            for (IndustryEntity entity : industryList) {
                industryStr.add(entity.getIndustryName());
            }
            ArrayAdapter<String> industryAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, industryStr);
            spinnerIndustry.setAdapter(industryAdapter);

            for (int i = 0; i < industryList.size(); i++) {
                IndustryEntity result = industryList.get(i);
                if (result.getIndustryId().equals(curCompany.getIndustryId())) {
                    spinnerIndustry.setSelection(i);
                    break;
                }
            }
            super.setContentView();
        }

        @Override
        public void onValidationSucceeded() {
            UpdateInfoCompanyParams updateInfoCompanyParams = new UpdateInfoCompanyParams();
            updateInfoCompanyParams.setCompanyId(curCompany.getCompanyId());
            updateInfoCompanyParams.setCompanyName(curCompany.getCompanyName());
            updateInfoCompanyParams.setCompanyShortName(editCompanyShortName.getText().toString());
            updateInfoCompanyParams.setCompanyAddr(editCompanyAddr.getText().toString());
            updateInfoCompanyParams.setAgentName(editAgentName.getText().toString());
            List<EleAccountResult> eleAccountEntityList = new ArrayList<>();
            for (int i = 0; i < eleAccountList.size(); i++) {
//                if (!TextUtils.isEmpty(eleAccountEditList.get(i).getText())) {
                EleAccountResult eleAccountEntity = new EleAccountResult();
                eleAccountEntity.setCompanyId(curCompany.getCompanyId());
                eleAccountEntity.setEleNo(eleAccountEditList.get(i).getText().toString());
                eleAccountEntity.setEleAccountId(eleAccountEditList.get(i).getId());
                eleAccountEntity.setVolume(Integer.valueOf(volumeEditList.get(i).getText().toString()));
                eleAccountEntityList.add(eleAccountEntity);
//                }
            }
            updateInfoCompanyParams.setEleAccounts(JsonUtils.getJsonArrayStr(eleAccountEntityList));
            eleAccountList = eleAccountEntityList;
            updateInfoCompanyParams.setSmsTel(editSmsTel.getText().toString());
            updateInfoCompanyParams.setTransformerCapacity(Integer.valueOf(editTransformerCapacity.getText().toString()));
            updateInfoCompanyParams.setTransformerCount(Integer.valueOf(editTransformerCount.getText().toString()));

            int selectMaintIndex = spinnerMaint.getSelectedItemPosition();
            MbStaffResult selectMaint = queryMaintResultList.get(selectMaintIndex);
            updateInfoCompanyParams.setMaintStaffId(selectMaint.getStaffId());
            updateInfoCompanyParams.setCompanyName(textName.getText().toString());

            int selectAreaIndex = spinnerArea.getSelectedItemPosition();
            AreaEntity areaEntity = areaList.get(selectAreaIndex);
            updateInfoCompanyParams.setAreaId(areaEntity.getAreaId());
            int selectIndustryIndex = spinnerIndustry.getSelectedItemPosition();
            IndustryEntity industryEntity = industryList.get(selectIndustryIndex);
            updateInfoCompanyParams.setIndustryId(industryEntity.getIndustryId());

            HttpClientSend.getInstance().send(updateInfoCompanyParams, new BaseStringCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                        if (ret.getErrorCode() != 0) {
                            Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(x.app(), "修改成功！", Toast.LENGTH_SHORT).show();
                            companyInfoDialog.dismiss();
                            queryCompanyList();
                        }
                    } catch (Exception e) {
                        Toast.makeText(x.app(), "操作失败", Toast.LENGTH_SHORT).show();
                    } finally {
                    }
                }
            });
        }
    }

    class PersonDialog extends BaseCustomDialog {

        public TextView textName;
        @NotEmpty(message = "商务联系人不能为空")
        public EditText editBusinessName;
        @NotEmpty(message = "商务联系人电话不能为空")
        public EditText editBusinessTel;
        @NotEmpty(message = "财务联系人不能为空")
        public EditText editFinanceName;
        @NotEmpty(message = "财务联系人电话不能为空")
        public EditText editFinanceTel;
        @NotEmpty(message = "技术联系人不能为空")
        public EditText editTechName;
        @NotEmpty(message = "技术联系人电话不能为空")
        public EditText editTechTel;

        public PersonDialog(Activity context, View.OnClickListener clickListener) {
            super(context, clickListener);
        }

        @Override
        protected void setContentView() {
            // 指定布局
            this.setContentView(R.layout.dialog_company_person);

            textName = (TextView) findViewById(R.id.text_companyname);
            textName.setText(curCompany.getCompanyName());

            editBusinessName = (EditText) findViewById(R.id.edit_business_name);
            editBusinessName.setText(setNullEdit(curCompany.getBusinessName()));
            editBusinessTel = (EditText) findViewById(R.id.edit_business_tel);
            editBusinessTel.setText(setNullEdit(curCompany.getBusinessTel()));
            editFinanceName = (EditText) findViewById(R.id.edit_finance_name);
            editFinanceName.setText(setNullEdit(curCompany.getFinanceName()));
            editFinanceTel = (EditText) findViewById(R.id.edit_finance_tel);
            editFinanceTel.setText(setNullEdit(curCompany.getFinanceTel()));
            editTechName = (EditText) findViewById(R.id.edit_tech_name);
            editTechName.setText(setNullEdit(curCompany.getTechName()));
            editTechTel = (EditText) findViewById(R.id.edit_tech_tel);
            editTechTel.setText(setNullEdit(curCompany.getTechTel()));

            super.setContentView();
        }

        @Override
        public void onValidationSucceeded() {
            UpdateCompanyPersonActionParams updateCompanyPersonActionParams = new UpdateCompanyPersonActionParams();
            updateCompanyPersonActionParams.setCompanyId(curCompany.getCompanyId());
            updateCompanyPersonActionParams.setBusinessName(editBusinessName.getText().toString());
            updateCompanyPersonActionParams.setBusinessTel(editBusinessTel.getText().toString());
            updateCompanyPersonActionParams.setFinanceName(editFinanceName.getText().toString());
            updateCompanyPersonActionParams.setFinanceTel(editFinanceTel.getText().toString());
            updateCompanyPersonActionParams.setTechName(editTechName.getText().toString());
            updateCompanyPersonActionParams.setTechTel(editTechTel.getText().toString());
            HttpClientSend.getInstance().send(updateCompanyPersonActionParams, new BaseStringCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                        if (ret.getErrorCode() != 0) {
                            Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(x.app(), "修改成功！", Toast.LENGTH_SHORT).show();
                            personDialog.dismiss();
                            queryCompanyList();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(x.app(), "操作失败", Toast.LENGTH_SHORT).show();
                    } finally {
                    }
                }
            });
        }
    }

    class OverhaulDialog extends BaseCustomDialog {

        public EditText editOveraulDate;

        public EditText editOveraulEndDate;

        public TextView textName;

        public OverhaulDialog(Activity context, View.OnClickListener clickListener) {
            super(context, clickListener);

        }

        @Override
        protected void setContentView() {
            // 指定布局
            this.setContentView(R.layout.dialog_company_overaul);

            textName = (TextView) findViewById(R.id.text_companyname);
            textName.setText(curCompany.getCompanyName());

            editOveraulDate = (EditText) findViewById(R.id.edit_overaul_date);
            editOveraulEndDate = (EditText) findViewById(R.id.edit_overaul_endate);
            if (addWorkOrderActionResult == null) {
                editOveraulDate.setText(DateUtils.format(new Date()));
                editOveraulEndDate.setText(DateUtils.format(new Date()));
            } else {
                editOveraulDate.setText(addWorkOrderActionResult.getStartDate().toString());
                editOveraulEndDate.setText(addWorkOrderActionResult.getEndDate().toString());
            }
            setDataEditText(editOveraulEndDate);
            setDataEditText(editOveraulDate);
            super.setContentView();
        }
    }
}
