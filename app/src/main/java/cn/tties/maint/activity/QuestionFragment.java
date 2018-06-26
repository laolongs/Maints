package cn.tties.maint.activity;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AutoCompleteTextView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.tties.maint.R;
import cn.tties.maint.adapter.QuestionListAdapter;
import cn.tties.maint.adapter.QuestionNewListAdapter;
import cn.tties.maint.adapter.QuestionSearchAdapter;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.enums.QuestionStatusType;
import cn.tties.maint.enums.WorkStatusType;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.CompanyParams;
import cn.tties.maint.httpclient.params.QueryNewQuertionParams;
import cn.tties.maint.httpclient.params.QueryQuertionParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.DescriptionResult;
import cn.tties.maint.httpclient.result.OrderResult;
import cn.tties.maint.httpclient.result.QuertionNewResult;
import cn.tties.maint.httpclient.result.QuertionResult;
import cn.tties.maint.httpclient.result.QuestionScheduleResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.PinYinUtils;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.util.XUtils3ImageLoader;
import cn.tties.maint.view.BaseCustomDialog;
import cn.tties.maint.view.DescriptionDialog;
import cn.tties.maint.widget.CustomDatePicker;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 问题列表.
 */
@ContentView(R.layout.fragment_question)
public class QuestionFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener,SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "QuestionFragment";

    @ViewInject(R.id.list_question)
    private ListView lsitQuestion;

    @ViewInject(R.id.equipment_allcompay)
    private LinearLayout allcompay;

    @ViewInject(R.id.equipment_alltime)
    private LinearLayout alltime;
    @ViewInject(R.id.questoin_radiogroup)
    private RadioGroup radioGroup;
    @ViewInject(R.id.ptrlayout_question)
    private PtrClassicFrameLayout questionPtrlayout;
    private List<CompanyResult> companyList;
    private List<String> companyNameList;
    QuertionResult searchEntity= new QuertionResult();;
    public static QuestionFragment mQuestionFragmentInstance;
    private Integer[] companyArray;
    ComapnyDialog comapnyDialog;
    private TimeDialog dialogtime;

    QuestionSearchAdapter adapter;
    ArrayList<CompanyResult> serachList;
    private CustomDatePicker cuys;
    private QuestionNewListAdapter adapterquestion;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mQuestionFragmentInstance = this;
        initImageLoad();
        this.initView();
        this.initData();
    }

    /**
     * 初始化界面.
     */
    private void initView() {
        //全部公司
        allcompay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comapnyDialog=new ComapnyDialog(QuestionFragment.this.getActivity(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<Integer> integers = adapter.getcompanyArrays();
                        if(integers.size()==0){
                            ToastUtil.showShort(QuestionFragment.this.getActivity(),"请选择公司");
                            return;
                        }
                        Integer[] compamyids=new Integer[companyList.size()];
                        for (int i = 0; i < integers.size(); i++) {
                            compamyids[i]=integers.get(i);
                            Log.i(TAG, "onClick:"+compamyids[i]);
                        }
                        searchEntity.setCompanyIds(compamyids);
                        QuestionFragment.this.getQuestionList();
                        comapnyDialog.dismiss();
                    }
                });
                comapnyDialog.show();
                comapnyDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                    }
                });
                comapnyDialog.textSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 循环变量数据源，如果有属性满足过滤条件，则添加到result中
                                String str=comapnyDialog.search.getText().toString();
                                for (int i = 0; i < companyList.size(); i++) {
                                    CompanyResult result = companyList.get(i);
                                    if (null == result.getCompanyShortName()) {
                                        continue;
                                    }
                                    if (result.getCompanyShortName().contains(str)) {
                                        serachList.add(result);
                                        continue;
                                    }
                                }
                                adapter.setCompanyList(serachList);
                                adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //筛选时间
        alltime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialogtime = new TimeDialog(QuestionFragment.this.getActivity(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(StringUtil.isEmpty(dialogtime.edit_starttime.getText().toString())||StringUtil.isEmpty(dialogtime.edit_endtime.getText().toString())){
                            ToastUtil.showShort(QuestionFragment.this.getActivity(),"请选择开始时间或结束时间");
                            return;
                        }
                        //格式化时间
                        SimpleDateFormat CurrentTime= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        String date1=dialogtime.edit_starttime.getText().toString();
                        String date2=dialogtime.edit_endtime.getText().toString();
                        try {
                            Date beginTime=CurrentTime.parse(date1);
                            Date endTime=CurrentTime.parse(date2);
                            Log.i(TAG, "onClick: kaishi "+beginTime.getTime());
                            Log.i(TAG, "onClick: jieshu "+endTime.getTime());
                            if(beginTime.getTime()>endTime.getTime()||beginTime.getTime()==endTime.getTime()){
                                ToastUtil.showShort(QuestionFragment.this.getActivity(),"结束时间需大于开始时间");
                                return;
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        searchEntity.setCreateTimeStart(date1+" 00:00:00");
                        searchEntity.setCreateTimeEnd(date2+" 00:00:00");
                        dialogtime.edit_starttime.setText(date1);
                        dialogtime.edit_endtime.setText(date2);
                        QuestionFragment.this.getQuestionList();
                        dialogtime.dismiss();
                    }
                });
                dialogtime.show();
            }
        });
    }

    private void initImageLoad() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new XUtils3ImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);
    }

    /**
     * 初始化数据.
     */
    private void initData() {
        cuys =  new CustomDatePicker(MyApplication.curActivity, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {    }
        }, "1900-01-01 00:00", "2099-01-01 00:00");
        getCompanyList();
        initSearchEntity();
        initPullRefresh();
        this.getQuestionList();
    }
    private void initSearchEntity() {
        radioGroup.setOnCheckedChangeListener(this);
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
        searchEntity.setStatus(QuestionStatusType.UNSTART.getValue());
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton radioButton = (RadioButton) this.getActivity().findViewById(radioGroup.getCheckedRadioButtonId());
        searchEntity.setStatus(QuestionStatusType.getValue(radioButton.getText().toString()));
        this.getQuestionList();
    }

    private void getCompanyList() {
        adapterquestion = new QuestionNewListAdapter(QuestionFragment.this);
        lsitQuestion.setAdapter(adapterquestion);
        serachList=new ArrayList<>();
        companyList = new ArrayList<>();
        companyNameList = new ArrayList<>();
        CompanyParams params = new CompanyParams();
        params.setMaintStaffId(MyApplication.getUserInfo().getMaintStaffId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                companyList.clear();
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), "获取企业信息失败", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    companyList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<CompanyResult>>() {
                    }.getType());
                }
            }
        });
    }

    public void getQuestionList() {
        QueryNewQuertionParams queryQuertionParams = new QueryNewQuertionParams();
        queryQuertionParams.setStatus(searchEntity.getStatus());
        queryQuertionParams.setMaintStaffId(MyApplication.getUserInfo().getMaintStaffId());
        queryQuertionParams.setCompanyIds(searchEntity.getCompanyIds());
        queryQuertionParams.setCreateTimeStart(searchEntity.getCreateTimeStart());
        queryQuertionParams.setCreateTimeEnd(searchEntity.getCreateTimeEnd());
        Log.i(TAG, "getQuestionList:setStatus "+searchEntity.getStatus());
        Log.i(TAG, "getQuestionList:setCompanyIds "+searchEntity.getCompanyIds());
        Log.i(TAG, "getQuestionList:setCreateTimeStart "+searchEntity.getCreateTimeStart());
        Log.i(TAG, "getQuestionList:setCreateTimeEnd "+searchEntity.getCreateTimeEnd());
        HttpClientSend.getInstance().send(queryQuertionParams, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                QuertionNewResult ret = JsonUtils.deserialize(result, QuertionNewResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), "获取问题数据失败", Toast.LENGTH_SHORT).show();
                    questionPtrlayout.refreshComplete();
                    return;
                }
                List<QuertionNewResult.ResultBean.QuertionListBean> quertionList = new ArrayList<>();
                for (int i = 0; i <ret.getResult().size() ; i++) {
                    for (QuertionNewResult.ResultBean.QuertionListBean quertionListBean:ret.getResult().get(i).getQuertionList() ) {
                        QuertionNewResult.ResultBean.QuertionListBean listBean=new QuertionNewResult.ResultBean.QuertionListBean();
                        listBean.setCompanyShortName(ret.getResult().get(i).getCompanyShortName()==null?"":ret.getResult().get(i).getCompanyShortName());
                        listBean.setCompanyId(quertionListBean.getCompanyId());
                        listBean.setCreateTime(quertionListBean.getCreateTime());
                        listBean.setDealTime(quertionListBean.getDealTime());
                        listBean.setDescription(quertionListBean.getDescription());
                        listBean.setPatrolRecordId(quertionListBean.getPatrolRecordId());
                        listBean.setQuestionId(quertionListBean.getQuestionId());
                        listBean.setQuestionIndex(quertionListBean.getQuestionIndex());
                        listBean.setStatus(quertionListBean.getStatus());
                        listBean.setType(quertionListBean.getType());
                        listBean.setFault_type(quertionListBean.getFault_type());
                        listBean.setQuestionType(quertionListBean.getQuestionType());
                        listBean.setQuestionDescriptionList(quertionListBean.getQuestionDescriptionList());
                        listBean.setQuestionScheduleList(quertionListBean.getQuestionScheduleList());
                        listBean.setPatrolItemId(quertionListBean.getPatrolItemId());
                        quertionList.add(listBean);
                    }
                }
                Log.i(TAG, "onSuccess: "+quertionList);
                adapterquestion.setDataList(quertionList);
                adapterquestion.notifyDataSetChanged();
                questionPtrlayout.refreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                questionPtrlayout.refreshComplete();
            }
        });
    }
    private void initPullRefresh() {
        questionPtrlayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
               QuestionFragment.this.getQuestionList();
            }
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == EventKind.REQUEST_CODE_SELECT) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    if (images.get(0).size > 5242880) {
                        Toast.makeText(x.app(),"不能长传查过5M大小的图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    adapterquestion.showDialog(images);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == EventKind.REQUEST_CODE_PREVIEW) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images == null || images.size() == 0) {
                   adapterquestion.showdeleteDialog();
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        this.getQuestionList();
    }
    class ComapnyDialog extends BaseCustomDialog {
        public TextView textSearch;
        public AutoCompleteTextView search;
        public ListView list;
        public ComapnyDialog(Activity context, View.OnClickListener clickListener) {
            super(context, clickListener);
        }
        @Override
        protected void setContentView() {
            // 指定布局
            this.setContentView(R.layout.dialog_equipment_company);
            textSearch=findViewById(R.id.question_search);
            search=findViewById(R.id.searchView);
            list=findViewById(R.id.question_list);
            adapter=new QuestionSearchAdapter();
            adapter.setCompanyList(companyList);
            list.setAdapter(adapter);
            for (CompanyResult result: companyList) {
                companyNameList.add(result.getCompanyShortName());
            }
            ArrayAdapter<String> searchAdapter=new ArrayAdapter<String>(QuestionFragment.this.getActivity(),android.R.layout.simple_dropdown_item_1line,companyNameList);
            search.setAdapter(searchAdapter);
            super.setContentView();
        }
    }
    class TimeDialog extends BaseCustomDialog {

        public TextView edit_starttime;
        public TextView edit_endtime;
        public TimeDialog(Activity context, View.OnClickListener clickListener) {
            super(context, clickListener);
        }
        @Override
        protected void setContentView() {
            // 指定布局
            this.setContentView(R.layout.dialog_alltiem);
            edit_starttime=findViewById(R.id.edit_starttime);
            edit_endtime=findViewById(R.id.edit_endtime);
            edit_endtime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDatePickerDialog(edit_endtime);
                }
            });
            edit_starttime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDatePickerDialog(edit_starttime);
                }
            });
            super.setContentView();
        }
    }
    public void showDatePickerDialog(final TextView editText) {
        if (cuys.isShow()) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        if (StringUtil.isEmpty(editText.getText().toString())) {
            editText.setText(now.split(" ")[0]);
        }
        cuys =  new CustomDatePicker(MyApplication.curActivity, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                editText.setText(time.split(" ")[0]);
            }
        }, "1900-01-01 00:00", "2099-01-01 00:00");
        cuys.showSpecificTime(false);
        cuys.setIsLoop(false); // 不允许循环滚动
        cuys.show(editText.getText().toString());
    }

}
