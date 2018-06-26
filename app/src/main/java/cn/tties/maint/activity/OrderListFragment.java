package cn.tties.maint.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.TableOrderAdapter;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.enums.RoleType;
import cn.tties.maint.enums.WorkStatusType;
import cn.tties.maint.enums.WorkType;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.SelectOrderParams;
import cn.tties.maint.httpclient.params.UpdateOrderStatusParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.OrderResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.PinYinUtils;
import cn.tties.maint.view.ConfirmDialog;
import cn.tties.maint.view.DescriptionDialog;
import cn.tties.maint.view.MyPopupWindow;
import cn.tties.maint.view.SafetyDialog;
import cn.tties.maint.widget.PtrListViewOnScrollListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 工单管理.
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_order_list)
public class OrderListFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "OrderListFragment";

    @ViewInject(R.id.spinner_kind)
    private Spinner spinnerKind;

    @ViewInject(R.id.table_order)
    private ListView orderTable;

    @ViewInject(R.id.radiogroup)
    private RadioGroup radioGroup;

    @ViewInject(R.id.ptrlayout_order)
    private PtrClassicFrameLayout orderPtrlayout;

    private TableOrderAdapter orderAdapter;

    private ArrayAdapter<String> orderTypeAdapter;

    private OrderResult searchEntity;

    private List<OrderResult> orderList;

    private PtrListViewOnScrollListener mPtrListViewOnScrollListener;

    private ConfirmDialog dialog ;

    private CompanyResult curCompany;
    private Integer curEleId;
    private String curEleNo;
    private SafetyDialog safetyDialog;
    String orderWorkName;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initSearchEntity();
        this.initView();
        this.initData();
        this.initPullRefresh();
        dialog = new ConfirmDialog(OrderListFragment.this.getContext());
    }
    @SuppressLint("ValidFragment")
    public OrderListFragment(Integer curEleId,String curEleNo, CompanyResult curCompany){
        this.curEleId=curEleId;
        this.curEleNo=curEleNo;
        this.curCompany=curCompany;
    }
    @Override
    public void changeEleAccountNextSteps(Integer curEleId, String curEleNo, CompanyResult curCompany) {
        this.curEleId=curEleId;
        this.curEleNo=curEleNo;
        this.curCompany=curCompany;

    }


    private void initView() {
        orderAdapter = new MyAdapter(x.app(), orderList);
        this.mPtrListViewOnScrollListener = new PtrListViewOnScrollListener(this.orderPtrlayout, true, false);
        orderTable.setAdapter(orderAdapter);
        orderTable.setOnScrollListener(this.mPtrListViewOnScrollListener);

        radioGroup.setOnCheckedChangeListener(this);
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
        initOrderTypeSearchView();

//        initSearchView();
    }

    /**
     * 初始化刷新.
     */
    private void initPullRefresh() {
        orderPtrlayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                OrderListFragment.this.queryWorkOrderList();
            }
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    private void initOrderTypeSearchView() {
        List<String> orderKinds = new ArrayList<>();
        orderKinds.add("全部");
        orderKinds.add(WorkType.ADORN_AMMETER.getInfo());
        orderKinds.add(WorkType.PATROL.getInfo());
        orderKinds.add(WorkType.REMOVE_FAULT.getInfo());
        orderKinds.add(WorkType.PRETTIFT.getInfo());
        orderKinds.add(WorkType.REMOVE_DUST.getInfo());
        orderTypeAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, orderKinds);
        spinnerKind.setAdapter(orderTypeAdapter);
        spinnerKind.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            searchEntity.setWorkType(null);
                        } else {
                            searchEntity.setWorkType(WorkType.getValue(orderTypeAdapter.getItem(i)));
                        }
                        OrderListFragment.this.queryWorkOrderList();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
    }

    private void initData() {
        this.queryWorkOrderList();
    }

    private void initSearchEntity() {
        searchEntity = new OrderResult();
        searchEntity.setStaffId(MyApplication.getUserInfo().getMaintStaffId());
        searchEntity.setStatus(WorkStatusType.UNSTART.getValue());
    }

    /**
     * 根据查询对象searchEntity查询工单列表.
     */
    private void queryWorkOrderList() {
        SelectOrderParams selectOrderParams = new SelectOrderParams();
        selectOrderParams.setStaffId(MyApplication.getUserInfo().getMaintStaffId());
        if (null != searchEntity.getStatus()) {
            selectOrderParams.setStatus(searchEntity.getStatus());
        }
        if (null != searchEntity.getWorkType()) {
            selectOrderParams.setWorkType(searchEntity.getWorkType());
        }
        Log.i(TAG, "queryWorkOrderList111: "+MyApplication.getUserInfo().getMaintStaffId());
        Log.i(TAG, "queryWorkOrderList222: "+searchEntity.getStatus());
        Log.i(TAG, "queryWorkOrderList333: "+searchEntity.getWorkType());
        HttpClientSend.getInstance().send(selectOrderParams, new BaseStringCallback() {

            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    orderPtrlayout.refreshComplete();
                    return;
                }
                orderList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<OrderResult>>() {}.getType());
                //这里不会获取到企业的status，因为与工单status冲突
                orderAdapter.setList(orderList);
                orderAdapter.notifyDataSetChanged();
                orderPtrlayout.refreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                orderPtrlayout.refreshComplete();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton radioButton = (RadioButton) this.getActivity().findViewById(radioGroup.getCheckedRadioButtonId());
        searchEntity.setStatus(WorkStatusType.getValue(radioButton.getText().toString()));
        this.queryWorkOrderList();
    }

    public class MyAdapter extends TableOrderAdapter {

        public MyAdapter(Context context, List list) {
            super(context, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final OrderResult orderEntity = (OrderResult) this.getItem(position);
            int unselecttv = Color.parseColor("#888888");
            int selecttv = Color.parseColor("#ffffff");
            convertView = getConvertView(convertView);
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.textWorkType.setText(orderEntity.getWorkTypeType().getInfo());
            viewHolder.textCompanyName.setText(orderEntity.getCompany().getCompanyShortName());
            viewHolder.textCompanyAddr.setText(orderEntity.getCompany().getCompanyAddr());
            viewHolder.textStartDate.setText(orderEntity.getCreateTime());
            //技术人员名字  运维人员
            viewHolder.textTechName.setText(orderEntity.getCompany().getTechName());
            viewHolder.textTechTel.setText(orderEntity.getCompany().getTechTel());
            if(orderEntity.getWorkTypeType()==WorkType.PATROL){
                viewHolder.textQuestionCount.setText(orderEntity.getInTime().toString()+"/"+orderEntity.getCompany().getMonthlyTourNumber()+"次");
            }else{
                viewHolder.textQuestionCount.setText(orderEntity.getInTime().toString()+"次");
            }
            viewHolder.textTourCount.setText(orderEntity.getXunShiCount().toString()+"次");
            //完成按钮附bg操作
            GradientDrawable background =(GradientDrawable)viewHolder.btnSave.getBackground();
            if (orderEntity.getStatusType() == WorkStatusType.UNSTART) {
                // XXX：特殊处理
                viewHolder.btnSave.setText(WorkType.getTpye(orderEntity.getWorkType()).getOpr());
                viewHolder.btnSave.setVisibility(View.VISIBLE);
                viewHolder.btnSuccess.setVisibility(View.GONE);
                background.setStroke(2, x.app().getResources().getColor(R.color.root_check));
                background.setColor(x.app().getResources().getColor(R.color.root_check));
                viewHolder.btnSave.setTextColor(selecttv);
            } else if (orderEntity.getStatusType() == WorkStatusType.STARTING) {
                viewHolder.btnSuccess.setVisibility(View.VISIBLE);
                viewHolder.btnSuccess.setText(WorkType.getTpye(orderEntity.getWorkType()).getGoon());
                viewHolder.btnSave.setText("完成");
                viewHolder.btnSave.setVisibility(View.VISIBLE);
                background.setStroke(2, x.app().getResources().getColor(R.color.root_check));
                background.setColor(x.app().getResources().getColor(R.color.root_check));
                viewHolder.btnSave.setTextColor(selecttv);
            } else {
                viewHolder.btnSuccess.setVisibility(View.GONE);
                viewHolder.btnSave.setVisibility(View.VISIBLE);
                background.setStroke(2, x.app().getResources().getColor(R.color.common_text_min_gray));
                background.setColor(x.app().getResources().getColor(R.color.background));
                viewHolder.btnSave.setText("已完成");
                viewHolder.btnSave.setTextColor(unselecttv);

            }
            if(orderEntity.getWorkTypeType()==WorkType.ADORN_AMMETER){//电表装置
                orderWorkName="电表装置";
                viewHolder.textQuestion.setText("采集点数");
                viewHolder.textTour.setText("历史配置");
            }else if(orderEntity.getWorkTypeType()==WorkType.REMOVE_FAULT){//消缺
                orderWorkName="消缺";
                viewHolder.textQuestion.setText("处理问题");
                viewHolder.textTour.setText("历史消缺");
            }else if(orderEntity.getWorkTypeType()==WorkType.PRETTIFT){//美化安规
                orderWorkName="美化安规";
                viewHolder.textQuestion.setText("本月美化");
                viewHolder.textTour.setText("历史美化");
            }else if(orderEntity.getWorkTypeType()==WorkType.PATROL){//电房巡视
                orderWorkName="电房巡视";
                viewHolder.textQuestion.setText("本月巡视");
                viewHolder.textTour.setText("历史巡视");
            }else if(orderEntity.getWorkTypeType()==WorkType.REMOVE_DUST){//除尘清理
                orderWorkName="除尘清理";
                viewHolder.textQuestion.setText("本月清理");
                viewHolder.textTour.setText("历史清理");
            }
            viewHolder.btnSuccess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBusBean eventBusBean=new EventBusBean();
                    eventBusBean.setKind(EventKind.EVENT_COMPANY_ISFORORDER);
                    eventBusBean.setSuccess(true);
                    EventBus.getDefault().post(eventBusBean);
                    dealStaringWordOrderForMaint(orderEntity.getWorkType(),orderEntity.getWorkOrderId());
                }
            });
            viewHolder.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NoFastClickUtils.isFastClick()) {
                        return;
                    }
                    //这里调用企业微信接口发送信息
//                    safetyDialog =  new SafetyDialog(OrderListFragment.this, MyApplication.getUserInfo().getStaffId().intValue(), orderEntity.getWorkOrderId(),orderWorkName);
                    //未换新数据表 所以运维人员ID写死了
                    safetyDialog =  new SafetyDialog(OrderListFragment.this, 2, orderEntity.getWorkOrderId(),orderWorkName);
                    safetyDialog.loading();
                    //跳过成功后
                    safetyDialog.setOnClickSkipSuccess(new SafetyDialog.OnClickSkipSuccess() {
                        @Override
                        public void SkipSuccessListenter() {
                            Log.i(TAG, "SkipSuccessListenter:跳过按钮 ");
                            sendorder(orderEntity);
                        }
                    });
                    //提交成功后
                    safetyDialog.setOnClickSubmitSuccess(new SafetyDialog.OnClickSubmitSuccess() {
                        @Override
                        public void SubmitSuccessListenter() {
                            Log.i(TAG, "SkipSuccessListenter:提交按钮 ");
                            sendorder(orderEntity);
                        }
                    });

//
                }
            });
            return convertView;
        }
    }

    //发起更新表单请求
    public void sendorder(final OrderResult orderEntity){
        final UpdateOrderStatusParams updateOrderStatusParams = new UpdateOrderStatusParams();
                    if (orderEntity.getStatusType() == WorkStatusType.UNSTART) {
                        updateOrderStatusParams.setStatus(WorkStatusType.STARTING.getValue());
                    } else if (orderEntity.getStatusType() == WorkStatusType.STARTING) {
                        updateOrderStatusParams.setStatus(WorkStatusType.END.getValue());
                    }
                    updateOrderStatusParams.setIsSend(1);//不发送短信
                    updateOrderStatusParams.setWorkOrderId(orderEntity.getWorkOrderId());
                    updateOrderStatusParams.setWorkType(orderEntity.getWorkType());
                    Log.i(TAG, "sendorder:getStatus "+updateOrderStatusParams.getStatus());
                    Log.i(TAG, "sendorder: getWorkOrderId"+updateOrderStatusParams.getWorkOrderId());
                    HttpClientSend.getInstance().send(updateOrderStatusParams, new BaseAlertCallback("更新工单状态成功","更新工单状态失败"){
                        @Override
                        public void onSuccessed(String result) {
                            // XXX:运维专员工单从未开始到进行中,进行特殊处理
            //                            PatrolFragment.patrolFragmentInstance.getWorkOrderId();
                            if (updateOrderStatusParams.getStatus() == WorkStatusType.STARTING.getValue()
                                    && (orderEntity.getWorkType() == WorkType.ADORN_AMMETER.getValue()
                                    || orderEntity.getWorkType() == WorkType.PATROL.getValue()
                                    || orderEntity.getWorkType() == WorkType.REMOVE_FAULT.getValue()
                                    || orderEntity.getWorkType() == WorkType.PRETTIFT.getValue()
                                    || orderEntity.getWorkType() == WorkType.REMOVE_DUST.getValue()
                            )
                                    ) {
                                EventBusBean eventBusBean=new EventBusBean();
                                eventBusBean.setKind(EventKind.EVENT_COMPANY_ISFORORDER);
                                eventBusBean.setSuccess(true);
                                EventBus.getDefault().post(eventBusBean);
                                Log.i(TAG, "onSuccessed: orderEntity.getWorkType"+orderEntity.getWorkType());
                                // 运维专员工单变成进行中直接跳转到指定的操作界面
                                dealStaringWordOrderForMaint(orderEntity.getWorkType(),orderEntity.getWorkOrderId());
                                ((RadioButton) radioGroup.getChildAt(WorkStatusType.STARTING.getValue())).setChecked(true);
                                return;
                            }
                            queryWorkOrderList();
                        }
                    });

                    //巡视增加短信提醒  企业通知先不做
//                    if (orderEntity.getWorkType() == WorkType.PATROL.getValue()) {
//                        dialog.loadDialog("是否发送短信提示", "","发送" ,"不发送",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        updateOrderStatusParams.setIsSend(0);
//                                        HttpClientSend.getInstance().send(updateOrderStatusParams, callback);
//                                    }
//                                },
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        updateOrderStatusParams.setIsSend(1);
//                                        HttpClientSend.getInstance().send(updateOrderStatusParams, callback);
//                                    }
//                                }
//                        );
//                    } else {
//                        HttpClientSend.getInstance().send(updateOrderStatusParams, callback);
//                    }
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
                    safetyDialog.setImages(images);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == EventKind.REQUEST_CODE_PREVIEW) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images == null || images.size() == 0) {
                    safetyDialog.deleteImage();
                }
            }
        }
    }


    private void dealStaringWordOrderForMaint(int workType,int workOrderId) {
        int index = -1;
        if (workType == WorkType.ADORN_AMMETER.getValue()) {     // 跳转到电表装置
            index = R.id.ele_configuration;
        }
        if (workType == WorkType.PATROL.getValue()) {     // 跳转到电房巡视
            index = R.id.ele_tour;
        }
        if (workType == WorkType.REMOVE_FAULT.getValue()) {     // 跳转到消缺
            index = R.id.eliminate;
        }
        if (workType == WorkType.PRETTIFT.getValue()) {       // 跳转到美化安规
            // 跳转到电房巡视
            index = R.id.beautify;
        }
        if (workType == WorkType.REMOVE_DUST.getValue()) {     // 跳转到除尘清理
            index = R.id.dedusting;
        }
        if (index > -1) {
            MainActivity.mMainActivityInstance.shwoFragment(index,workOrderId,workType);
        }
    }

}
