package cn.tties.maint.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.TableOrderAdapter;
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
import cn.tties.maint.view.MyPopupWindow;
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

//    @ViewInject(R.id.searchView)
//    private SearchView searchView;

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

    private List<OrderResult> serachList;

    private List<String> pinYinList;

    private List<String> pinYinAllList;

    private PtrListViewOnScrollListener mPtrListViewOnScrollListener;

    private ConfirmDialog dialog ;

    private CompanyResult curCompany;
    private Integer curEleId;
    String curEleNo;
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
        Integer roleType = MyApplication.getUserInfo().getRoleType().getValue();
        if(roleType == RoleType.MAINT.getValue()){
            orderKinds.add(WorkType.ADORN_AMMETER.getInfo());
            orderKinds.add(WorkType.PATROL.getInfo());
            orderKinds.add(WorkType.REMOVE_FAULT.getInfo());
            orderKinds.add(WorkType.PRETTIFT.getInfo());
            orderKinds.add(WorkType.REMOVE_DUST.getInfo());
        }
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
        searchEntity.setStaffId(MyApplication.getUserInfo().getStaffId());
        searchEntity.setStatus(WorkStatusType.UNSTART.getValue());
    }

    /**
     * 根据查询对象searchEntity查询工单列表.
     */
    private void queryWorkOrderList() {
        SelectOrderParams selectOrderParams = new SelectOrderParams();
        selectOrderParams.setStaffId(MyApplication.getUserInfo().getStaffId().intValue());
        if (null != searchEntity.getStatus()) {
            selectOrderParams.setStatus(searchEntity.getStatus());
        }
        if (null != searchEntity.getWorkType()) {
            selectOrderParams.setWorkType(searchEntity.getWorkType());
        }
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

                // 更新搜索数据
//                pinYinList = new ArrayList<String>();
//                pinYinAllList = new ArrayList<String>();
//                for (OrderResult entity : orderList) {
//                    pinYinList.add(PinYinUtils.getPinYinHeadChar(entity.getCompany().getCompanyShortName()));
//                    pinYinAllList.add(PinYinUtils.getPinYin(entity.getCompany().getCompanyShortName()));
//                }
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
            convertView = getConvertView(convertView);
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.textWorkType.setText(orderEntity.getWorkTypeType().getInfo());
            viewHolder.textCompanyName.setText(orderEntity.getCompany().getCompanyShortName());
            viewHolder.textCompanyAddr.setText(orderEntity.getCompany().getCompanyAddr());
            viewHolder.textStartDate.setText(orderEntity.getCreateTime());
            viewHolder.textTechName.setText(orderEntity.getCompany().getTechName());
            viewHolder.textTechTel.setText(orderEntity.getCompany().getTechTel());
            viewHolder.textQuestionCount.setText(orderEntity.getInTime().toString());
            viewHolder.textTourCount.setText(orderEntity.getXunShiCount().toString());
            if (orderEntity.getStatusType() == WorkStatusType.UNSTART) {
                // XXX：特殊处理
                viewHolder.btnSave.setText(WorkType.getTpye(orderEntity.getWorkType()).getOpr());
                viewHolder.btnSave.setVisibility(View.VISIBLE);
            } else if (orderEntity.getStatusType() == WorkStatusType.STARTING) {
                viewHolder.btnSave.setText("已完成");
                viewHolder.btnSave.setVisibility(View.VISIBLE);
            } else {
                viewHolder.btnSave.setVisibility(View.INVISIBLE);
            }
            viewHolder.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NoFastClickUtils.isFastClick()) {
                        return;
                    }
                    final UpdateOrderStatusParams updateOrderStatusParams = new UpdateOrderStatusParams();
                    if (orderEntity.getStatusType() == WorkStatusType.UNSTART) {
                        updateOrderStatusParams.setStatus(WorkStatusType.STARTING.getValue());
                    } else if (orderEntity.getStatusType() == WorkStatusType.STARTING) {
                        updateOrderStatusParams.setStatus(WorkStatusType.END.getValue());
                    }
                    updateOrderStatusParams.setWorkOrderId(orderEntity.getWorkOrderId());

                    final BaseAlertCallback callback = new BaseAlertCallback("更新工单状态成功","更新工单状态失败") {
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
                                // 运维专员工单变成进行中直接跳转到指定的操作界面
                                dealStaringWordOrderForMaint(orderEntity.getWorkType(), orderEntity.getCompanyId());
                                ((RadioButton) radioGroup.getChildAt(WorkStatusType.STARTING.getValue())).setChecked(true);
                                return;
                            }
                            queryWorkOrderList();
                        }
                    };
                    //巡视增加短信提醒
                    if (orderEntity.getWorkType() == WorkType.PATROL.getValue()) {
                        dialog.loadDialog("是否发送短信提示", "","发送" ,"不发送",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        updateOrderStatusParams.setIsSend(0);
                                        HttpClientSend.getInstance().send(updateOrderStatusParams, callback);
                                    }
                                },
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        updateOrderStatusParams.setIsSend(1);
                                        HttpClientSend.getInstance().send(updateOrderStatusParams, callback);
                                    }
                                }
                        );
                    } else {
                        HttpClientSend.getInstance().send(updateOrderStatusParams, callback);
                    }
                }
            });
            return convertView;
        }
    }

    private void dealStaringWordOrderForMaint(int workType, int companyId) {
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
            MainActivity.mMainActivityInstance.shwoFragment(index);
        }
    }

}
