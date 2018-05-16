package cn.tties.maint.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.EleBillListViewAdapter;
import cn.tties.maint.bean.EleBillBean;
import cn.tties.maint.bean.ImageBean;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.CompanyParams;
import cn.tties.maint.httpclient.params.QueryEleBillParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.EleAccountResult;
import cn.tties.maint.httpclient.result.EleBillResult;
import cn.tties.maint.util.DateUtils;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.view.HorizontalListView;

/**
 * 电费单上传
 */
@ContentView(R.layout.fragment_ele_bill_upload)
public class EleBillUploadFragment extends BaseFragment {

    private static final String TAG = "ElectricBillUploadFragment";

    @ViewInject(R.id.layout_main)
    private LinearLayout layoutMain;

    @ViewInject(R.id.spinner_company)
    private Spinner spinnerCompany;

    @ViewInject(R.id.spinner_eleAccount)
    private Spinner spinnerEleAccount;

    @ViewInject(R.id.radio_date)
    private RadioGroup radioDate;

    @ViewInject(R.id.textview_companyName)
    private TextView textCompannyName;

    @ViewInject(R.id.textview_eleAccount)
    private TextView textEleAccount;

    @ViewInject(R.id.listview_hor)
    private HorizontalListView listViewHorizon;

    private CompanyResult curCompany;

    private Integer curEleId;

    private Integer curYear;

    private EleBillListViewAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRadio();
        initCompanyList();
    }

    private void initRadio() {
        int thisYear = DateUtils.getNowYear();
        RadioButton radioBtnLastYear = (RadioButton) radioDate.getChildAt(0);
        radioBtnLastYear.setText(String.valueOf(thisYear - 1));
        RadioButton radioBtnThisYear = (RadioButton) radioDate.getChildAt(1);
        radioBtnThisYear.setText(String.valueOf(thisYear));
        curYear = thisYear;
        radioDate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton btn = (RadioButton) radioDate.findViewById(i);
                curYear = Integer.valueOf(btn.getText().toString());
                getBillData();
            }
        });
    }

    private void initCompanyList() {
        CompanyParams params = new CompanyParams();
        //登录用户ID
        params.setCreatorId(MyApplication.getUserInfo().getStaffId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    final List<CompanyResult> list = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<CompanyResult>>() {
                    }.getType());
                    List<String> adapterList = new ArrayList<>();
                    for (CompanyResult companyResult : list) {
                        adapterList.add(companyResult.getCompanyShortName());
                    }
                    ArrayAdapter adapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, adapterList);
                    spinnerCompany.setAdapter(adapter);
                    spinnerCompany.setOnItemSelectedListener(
                            new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    curCompany = list.get(i);
                                    textCompannyName.setText(curCompany.getCompanyShortName());
                                    initEleAccountIdist();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            }
                    );
                    spinnerCompany.setSelection(0);
                }
            }
        });
    }

    private void initEleAccountIdist() {
        List<String> adapterList = new ArrayList<>();
        for (EleAccountResult eleAccountResult : curCompany.getEleAccountList()) {
            adapterList.add(eleAccountResult.getEleNo());
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, adapterList);
        spinnerEleAccount.setAdapter(adapter);
        spinnerEleAccount.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        curEleId = curCompany.getEleAccountList().get(i).getEleAccountId();
                        textEleAccount.setText(String.valueOf(curCompany.getEleAccountList().get(i).getEleNo()));
                        getBillData();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
    }

    private void getBillData() {
        QueryEleBillParams params = new QueryEleBillParams();
        params.setEleAccountId(curEleId);
        params.setYear(curYear);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {

            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    //错误
                } else {
                    List<EleBillResult> list = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<EleBillResult>>() {
                    }.getType());
                    //根据选择年份，确认显示月份数量
                    List<EleBillBean> billBeans = new ArrayList<>();
                    int monthCount = 12;
                    if (DateUtils.getNowYear() == curYear) {
                        monthCount = DateUtils.getIntNowMonth();
                    }
                    //循环月份补齐显示bean
                    for (int i = 1; i <= monthCount; i++) {
                        boolean flag;
                        //过滤当月图片数据
                        EleBillBean bean = new EleBillBean();
                        bean.setYear(curYear);
                        bean.setMonth(i);
                        bean.setEleAccountId(curEleId);
                        do {
                            flag = false;
                            for (EleBillResult eleBillResult : list) {
                                if (eleBillResult.getBillMonth() == i) {
                                    flag = true;
                                    ImageBean imageBean = new ImageBean();
                                    imageBean.setImageUrl(Constants.BASE_URL + eleBillResult.getBillPath());
                                    imageBean.setImageId(eleBillResult.getBillId());
                                    bean.setImage(imageBean);
                                    list.remove(eleBillResult);
                                    break;
                                }
                            }
                        } while (flag);
                        billBeans.add(bean);
                    }
                    adapter = new EleBillListViewAdapter(EleBillUploadFragment.this, layoutMain.getWidth(), listViewHorizon);
                    adapter.setmDataList(billBeans);
                    listViewHorizon.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //添加照片返回
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == EventKind.REQUEST_CODE_SELECT) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    if (images.get(0).size > 5242880) {
                        Toast.makeText(x.app(), "不能长传查过5M大小的图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    adapter.setImageItem(images);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {//预览照片返回
            if (data != null && requestCode == EventKind.REQUEST_CODE_PREVIEW) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images == null || images.size() == 0) {
                    adapter.deleteImage();
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}