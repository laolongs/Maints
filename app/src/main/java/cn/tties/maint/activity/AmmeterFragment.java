package cn.tties.maint.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonAppendListViewAdapter;
import cn.tties.maint.adapter.CommonSwipListViewAdapter;
import cn.tties.maint.bean.CommonListViewBean;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.dao.RateDao;
import cn.tties.maint.entity.RateEntity;
import cn.tties.maint.enums.AmmeterType;
import cn.tties.maint.enums.CommProtocolType;
import cn.tties.maint.enums.ConnModeType;
import cn.tties.maint.enums.LineInType;
import cn.tties.maint.enums.MeterEquipmentType;
import cn.tties.maint.enums.PowerUtilizationNatureType;
import cn.tties.maint.enums.VoltageLevelType;
import cn.tties.maint.holder.AccountHolder;
import cn.tties.maint.holder.BaseInfoHolder;
import cn.tties.maint.holder.CollectionHolder;
import cn.tties.maint.holder.CollectionMessageHolder;
import cn.tties.maint.holder.DistributionHolder;
import cn.tties.maint.holder.DistributionMessageHolder;
import cn.tties.maint.holder.InfoHolder;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.AddAutoTaskParams;
import cn.tties.maint.httpclient.params.AddEnereyCompanyParams;
import cn.tties.maint.httpclient.params.AddUserEnergyActionParams;
import cn.tties.maint.httpclient.params.CompanyParamsById;
import cn.tties.maint.httpclient.params.GetAutoBatchStatusParams;
import cn.tties.maint.httpclient.params.GetMetsureStatusParams;
import cn.tties.maint.httpclient.params.GetTransformerParams;
import cn.tties.maint.httpclient.params.QueryEnergyUserParams;
import cn.tties.maint.httpclient.params.QueryMeterListParams;
import cn.tties.maint.httpclient.params.QueryTotalElectricityMeterParams;
import cn.tties.maint.httpclient.params.SaveOrUpdateMeterConfigParams;
import cn.tties.maint.httpclient.params.UpdateTotalElectricityMeterParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.FrameResult;
import cn.tties.maint.httpclient.result.MeterResult;
import cn.tties.maint.httpclient.result.QueryEnergyUserResult;
import cn.tties.maint.httpclient.result.TaskResult;
import cn.tties.maint.httpclient.result.TotalElectricityMeterResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.view.CriProgressDialog;
import cn.tties.maint.view.InfoDialog;
import cn.tties.maint.view.MeterInfoDialog;
import cn.tties.maint.view.TaskDialog;

/**
 * 电表配置.
 */
@ContentView(R.layout.fragment_ammeter)
public class AmmeterFragment extends BaseFragment  {
    private static final String TAG = "AmmeterFragment";
    //一级列表
    @ViewInject(R.id.ammeter_lv1)
    private SwipeMenuRecyclerView ammeter_lv1;
    //ip
    @ViewInject(R.id.ammeter_ip)
    private TextView ammeter_ip;
    //端口
    @ViewInject(R.id.ammeter_port)
    private TextView ammeter_port;
    //1级提示信息
    @ViewInject(R.id.ammeter_LL1_hite)
    private LinearLayout ammeter_LL1_hite;
    //二级layout
    @ViewInject(R.id.ammeter_info2)
    private LinearLayout ammeter_info2;
    //新建图片
    @ViewInject(R.id.ammeter_img)
    private ImageView ammeter_img;
    //新建采集点
    @ViewInject(R.id.ammeter_tv)
    private TextView ammeter_tv;
    //二级级列表
    @ViewInject(R.id.ammeter_lv2)
    private SwipeMenuRecyclerView ammeter_lv2;
    //二级提示信息
    @ViewInject(R.id.ammeter_LL2_hite)
    private LinearLayout ammeter_LL2_hite;
    //title
    @ViewInject(R.id.ammeter_title)
    private TextView ammeter_title;

    //召测
    @ViewInject(R.id.btn_getMetsureStatus)
    public Button btnGetMetsureStatus;
    //配置
    @ViewInject(R.id.btn_add_task)
    public Button btnAddTask;
    //编辑
    @ViewInject(R.id.btn_add_editor)
    public Button btn_add_editor;
    //保存
    @ViewInject(R.id.btn_save)
    public Button btn_save;
    //采集点配置layout.
    @ViewInject(R.id.ammeter_collection_configuration)
    private LinearLayout layoutCollectionPoint;
    /**
     * 采集点信息layout.
     */
    @ViewInject(R.id.ammeter_collection_message)
    private LinearLayout layoutCollectionmessage;
    /**
     * 总电量配置layout.
     */
    @ViewInject(R.id.ammeter_allele_configuration)
    private LinearLayout layoutTotalElectric;
    /**
     * 总电量信息layout.
     */
    @ViewInject(R.id.ammeter_allele_message)
    private LinearLayout layoutallelemessage;
    /**
     * 采集点holder.
     */
    private CollectionHolder collectionHolder;

    private CollectionMessageHolder collectionMessageHolder;
    /**
     * 总电量holder.
     */
    private DistributionHolder totalElectricHolder;

    private DistributionMessageHolder distributionMessageHolder;

    //1级
    protected CommonSwipListViewAdapter lv2Adapter;
    protected CommonSwipListViewAdapter rootAdapter;
    protected SwipeItemClickListener rootListener;
    protected SwipeItemClickListener lv2Listener;
    /**
     * 请求类型 1-采集点配置
     */
    private int requestType;
    /**
     * 采集点id
     */
    private CriProgressDialog dialog;

    private InfoDialog infoDialog;

    List<TaskResult> taskList;

    //功率因素
    List<String> powerFactorList;

    //可选择变压器列表
    List<CompanyEquipmentResult> transformerList;

    private TaskDialog taskDialog;

    /*
    *  召测
    */
    private MeterInfoDialog meterInfoDialog;

    private List<Long> taskIdList;
    private int taskIndex;
    private boolean taskStopFlag;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new CriProgressDialog(AmmeterFragment.this.getActivity());
        infoDialog = new InfoDialog();
        taskDialog = new TaskDialog(closeClick);
        taskStopFlag = false;
    }

    @Override
    public void changeEleAccountNextStep() {
        initView();
        //1级数据展示
        initRootListView();
        getAmmeterTypeListView();
        //采集点二级
        initLv2ListView();


    }
    //一级数据库数据展示
    protected void initRootListView() {
        rootAdapter = new CommonSwipListViewAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        ammeter_lv1.setLayoutManager(manager);
        ammeter_lv1.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
        rootListener = new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                CommonListViewInterface selBean = rootAdapter.getItem(position);
                //如果是选中状态,执行取消操作
                if (selBean.isChecked()) {
                    return;
                } else {//切换操作
                    rootAdapter.setChecked(selBean);
                    rootAdapter.notifyDataSetChanged();
                    rootListViewClick(selBean);
                }
            }
        };
        ammeter_lv1.setSwipeItemClickListener(rootListener);
        ammeter_lv1.setAdapter(rootAdapter);
    }
    //二级
    protected void initLv2ListView() {
        lv2Adapter = new CommonSwipListViewAdapter();
        ammeter_lv2.setLayoutManager(new LinearLayoutManager(getActivity()));
        ammeter_lv2.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
        lv2Listener = new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                CommonListViewInterface selBean = lv2Adapter.getItem(position);
                if (selBean.isChecked()) {
                    return;
                } else {//切换操作
                    //设置选中
                    lv2Adapter.setChecked(selBean);
                    lv2Adapter.notifyDataSetChanged();
                    showUpdateDetail(selBean);
                }
            }
        };
        ammeter_lv2.setSwipeItemClickListener(lv2Listener);
        ammeter_lv2.setAdapter(lv2Adapter);

    }
    /**
     * 获取采集点列表.
     */
    private void getCollection() {
        QueryMeterListParams params = new QueryMeterListParams();
        params.setCompanyId(curCompany.getCompanyId());
        params.setEleAccountId(curEleId);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<MeterResult> meterList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<MeterResult>>() {
                }.getType());
                lv2Adapter.setmDataList(meterList);
                lv2Adapter.notifyDataSetChanged();
            }
        });
        //新建采集点
        ammeter_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ammeter_title.setText("采集点配置");
                ToastUtil.showShort(getActivity(),"什么效果不知道");
            }
        });
    }

    /**
     * 初始化界面.
     */
    private void initView() {
        //采集点配置
        collectionHolder = new CollectionHolder(layoutCollectionPoint);
        //采集点信息
       collectionMessageHolder = new CollectionMessageHolder(layoutCollectionmessage);
        //总电量配置
        totalElectricHolder = new DistributionHolder(layoutTotalElectric);
        //总电量信息
        distributionMessageHolder = new DistributionMessageHolder(layoutallelemessage);

    }


    /**
     * 初始化电表配置类型.
     */
    private void getAmmeterTypeListView() {
        List<CommonListViewBean> list = new ArrayList<>();
        if (curCompany.getEnergyCompanyId() == null) {
            CommonListViewBean bean = new CommonListViewBean(AmmeterType.COLLECTION_POINT.getValue(), AmmeterType.COLLECTION_POINT.getInfo());
            list.add(bean);
        } else {
            for (AmmeterType e : AmmeterType.values()) {
                CommonListViewBean bean = new CommonListViewBean(e.getValue(), e.getInfo());
                list.add(bean);
            }
        }
        Log.i(TAG, "getAmmeterTypeListView: "+list.size());
        rootAdapter.setmDataList(list);
        rootAdapter.notifyDataSetChanged();
    }

    /**
     * rootlistView点击事件.
     *
     * @param bean 选中的bean.
     */

    protected void rootListViewClick(CommonListViewInterface bean) {
        if (bean.getItemId() == AmmeterType.COLLECTION_POINT.getValue()) {
            getCollection();
            showHoulder(2);
        }
        if (bean.getItemId() == AmmeterType.TOTAL_ELECTRIC.getValue()) {
            showUpdateDetail(bean);
            showHoulder(3);
        }
    }
    /**
     * 显示明细页面.
     *2级上方listview点击事件.
     * @param bean 选中的bean
     */
    private void showUpdateDetail(CommonListViewInterface bean) {
        if (rootAdapter.getChecked().getItemId() == AmmeterType.COLLECTION_POINT.getValue()) {
            showCollectionDetail(bean);
        }
        if (rootAdapter.getChecked().getItemId() == AmmeterType.TOTAL_ELECTRIC.getValue()) {
            showTotalElectricDetail();
        }
    }
    /**
     * 显示采集点更新界面.
     *
     * @param bean 选中的采集点
     */
    private void showCollectionDetail(final CommonListViewInterface bean) {

        final MeterResult result = (MeterResult) bean;
        //采集点名称
        collectionMessageHolder.ammeter_message_name.setText(setNullEdit(result.getMeterName()));
        //区号
        collectionMessageHolder.ammeter_message_area.setText(setNullEdit(result.getAreaCode()));
        //终端地址
        collectionMessageHolder.ammeter_message_address.setText(setNullEdit(result.getTerminalAddress()));
        //设备类型
        if (result.getEquipmentType() !=  null) {
            int i =0;
            for (MeterEquipmentType e : MeterEquipmentType.values()) {
                if (e.getType() == result.getEquipmentType()) {
                    collectionMessageHolder.ammeter_message_equipment.setText(i+"");
                }
                i++;
            }
        }
        //电表类型
        if (result.getLineInType() !=  null && result.getLineInType() > 0) {
            collectionMessageHolder.ammeter_message_ele.setText(result.getLineInType().intValue() - 1+"");
        }


        //召测
        btnGetMetsureStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                sendGetMetsureStatus(result);
            }
        });
        //编辑
        btn_add_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                showHoulder(1);
//                addAutoTask(result.getDcTerminalId(), result.getEquipmentType());
            }
        });
    }

    /**
     * 显示总电量界面.
     */
    private void showTotalElectricDetail() {
//        totalElectricHolder.textLeft.setText(curEleNo + "总电量");
        totalElectricHolder.textLeft.setText("当前总电量");
        getTotalElectricity();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                sendUpdateTotalElectric();
            }
        });
    }

    /**
     * 获取总电量数据.
     */
    private void getTotalElectricity() {
        QueryTotalElectricityMeterParams params = new QueryTotalElectricityMeterParams();
        params.setCompanyId(curCompany.getCompanyId());
        params.setEleAccountId(curEleId);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                TotalElectricityMeterResult totalElectricityMeterResult = JsonUtils.deserialize(ret.getResult(), TotalElectricityMeterResult.class);
                List<CommonListViewBean> list = new ArrayList<>();
                totalElectricHolder.leftAdapter.setmDataList(totalElectricityMeterResult.getHaveMeterList());
                totalElectricHolder.leftAdapter.notifyDataSetChanged();

                totalElectricHolder.rightAdapter.setmDataList(totalElectricityMeterResult.getNoHaveMeterList());
                totalElectricHolder.rightAdapter.notifyDataSetChanged();
            }
        });
    }



    @Override
    public void onValidationSucceeded() {
        switch (requestType) {
            case 1:
//                this.meterConfigRequest();
                break;
        }

    }

    private void sendGetMetsureStatus(MeterResult bean) {
        dialog.loadDialog("召测中...");
        reMetsureSend(bean.getDcPointId(), null);
    }

    private void reMetsureSend(final Long pointId, Long frameId) {
        GetMetsureStatusParams params = new GetMetsureStatusParams();
        params.setPointId(pointId);
        params.setFrameId(frameId);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    dialog.removeDialog();
                    infoDialog.loadDialog(false, "召测失败", ret.getErrorMessage());
                    return;
                }
                FrameResult measureResult = JsonUtils.deserialize(ret.getResult(), FrameResult.class);
                // XXX:没有返回isLast，有返回ret=2
                if (!measureResult.getLast()) {
                    try {
                        Thread.sleep(5000);
                        reMetsureSend(pointId, measureResult.getFrameId());
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                dialog.removeDialog();
                if (measureResult.getTimeOut()) {
                    infoDialog.loadDialog(false, "召测失败", measureResult.getMsg());
                    return;
                }
                if (measureResult.getRet() > 0) {
                    List<JsonElement> list2 = measureResult.getDataList();
                    meterInfoDialog = new MeterInfoDialog(AmmeterFragment.this,list2 , new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            meterInfoDialog.validator.validate();
                        }
                    });
                    meterInfoDialog.show();
//                    infoDialog.loadDialog(true, "召测成功", null);
                } else {
                    infoDialog.loadDialog(false, "召测失败", measureResult.getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog.removeDialog();
                infoDialog.loadDialog(false, "数据连接失败", null);
                super.onError(ex, isOnCallback);
            }
        });
    }
    /**
     * 请求采集点配置
     */
//    private void meterConfigRequest() {
//        dialog.loadDialog("采集点信息配置中...");
//        final SaveOrUpdateMeterConfigParams meterParams = new SaveOrUpdateMeterConfigParams();
//        meterParams.setMeterId(collectionHolder.dbId);
//        meterParams.setEleAccountId(curEleId);
//        meterParams.setCompanyId(curCompany.getCompanyId());
//        meterParams.setMeterName(collectionHolder.editCollectonPointName.getText().toString());
////        meterParams.setAmmeterNumber(Integer.parseInt(collectionHolder.editAmmeterNumber.getText().toString()));
//        meterParams.setTerminalAddress(collectionHolder.editTerminalAddress.getText().toString());
//        meterParams.setGprsCardNo(collectionHolder.editGPRS.getText().toString());
//        meterParams.setTv(Integer.parseInt(collectionHolder.editVoltageTransformerRatio.getText().toString()));
//        meterParams.setTa(Integer.parseInt(collectionHolder.editCurrentTransformerRatio.getText().toString()));
//        meterParams.setRatedCurrent(Integer.parseInt(collectionHolder.editRatedCurrent.getText().toString()));
//        meterParams.setRatedPower(Integer.parseInt(collectionHolder.editRatedPower.getText().toString()));
//        meterParams.setRatedVoltage(Integer.parseInt(collectionHolder.editRatedVoltage.getText().toString()));
//        meterParams.setConnMode(ConnModeType.getValue(String.valueOf(collectionHolder.spinnerConnMode.getSelectedItem())));
//        meterParams.setAreaCode(collectionHolder.editAreaCode.getText().toString());
//        meterParams.setPoint(Long.valueOf(collectionHolder.point.getText().toString()));
//        meterParams.setEquipmentType(MeterEquipmentType.getValue(String.valueOf(collectionHolder.equipmentType.getSelectedItem())));
//        meterParams.setCommProtocolType(CommProtocolType.getValue(String.valueOf(collectionHolder.commProtocolType.getSelectedItem())));
//        meterParams.setLineInType(LineInType.getValue(String.valueOf(collectionHolder.lineInType.getSelectedItem())));
//        if (collectionHolder.spinnerTransformer.getSelectedItemPosition() != 0) {
//            meterParams.setTransformerId(transformerList.get(collectionHolder.spinnerTransformer.getSelectedItemPosition() - 1).getCompanyEquipmentId());
//        }
//        HttpClientSend.getInstance().send(meterParams, new BaseStringCallback() {
//            @Override
//            public void onSuccess(String result) {
//                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
//                if (ret.getErrorCode() != 0) {
//                    infoDialog.loadDialog(false, "采集点配置失败", ret.getErrorMessage());
//                    dialog.removeDialog();
//                    return;
//                }
//                JsonObject element = ret.getResult().getAsJsonObject();
//                boolean flag = false;
//                if (element.has("isNewTerm")) {
//                    if (element.get("isNewTerm").getAsBoolean()) {
//                        flag = true;
//                    }
//                }
//                //新建场合 自动执行任务配置
//                if  (collectionHolder.dbId == null && flag) {
//                    //配置任务
//                    AmmeterFragment.this.sleep(5000);
//                    addAutoTask(element.get("terminalId").getAsLong(), meterParams.getEquipmentType());
//                } else {
//                    dialog.removeDialog();
//                    infoDialog.loadDialog(true, "采集点配置更新成功", null);
//                }
//                //刷新列表
//                getCollection();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                dialog.removeDialog();
//                infoDialog.loadDialog(false, "采集点配置失败",null);
//                ex.printStackTrace();
//            }
//        });
//    }

    /**
     * 配置任务
     * @param terminalId
     * @param equipmentType
     */
    private void addAutoTask(final long terminalId, final int equipmentType) {
        taskStopFlag  = false;
        taskIdList = MeterEquipmentType.getTaskIdList(MeterEquipmentType.get(equipmentType));
        if (null == taskIdList) {
            return;
        }
        dialog.loadDialog("采集点任务配置中...");
        taskIndex = 0;
        taskList = null;
        this.addAutoTask(terminalId);
    }

    private void addAutoTask(final long terminalId) {
        AddAutoTaskParams params = new AddAutoTaskParams();
        params.setTerminalId(terminalId);
        params.setTaskId(taskIdList.get(taskIndex));
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    dialog.removeDialog();
                    infoDialog.loadDialog(false, "采集点任务配置失败", ret.getErrorMessage());
                    return;
                }
                AmmeterFragment.this.sleep(5000);
                List<TaskResult> tasks = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<TaskResult>>() {
                }.getType());
                dialog.removeDialog();
                taskDialog.show();
                taskDialog.setExecuting();
                if (null == taskList) {
                    taskList = tasks;
                } else {
                    taskList.addAll(tasks);
                }
                taskDialog.refreshMDataList(taskList);
                for (TaskResult result1 : tasks) {
                    addAutoBatch(terminalId, result1.getTaskId());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog.removeDialog();
                infoDialog.loadDialog(false, "采集点配置失败",null);
                ex.printStackTrace();
            }
        });
    }

    private void addAutoBatch(final Long terminalId, final Long taskId) {
        reAutoBatchStatusSend(terminalId, null, taskId);
    }

    private void reAutoBatchStatusSend(final Long terminalId,final Long frameId, final Long taskId) {
        if (taskStopFlag) {
            return;
        }
        GetAutoBatchStatusParams params = new GetAutoBatchStatusParams();
        params.setTerminalId(terminalId);
        params.setFrameId(frameId);
        params.setTaskId(taskId);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    taskDialog.changeMsg(taskId, ret.getErrorMessage());
                    taskDialog.setFinish(taskId);
                    isFinish();
                    return;
                }
                FrameResult frameResult = JsonUtils.deserialize(ret.getResult(), FrameResult.class);
                //切换任务下发状态
                taskDialog.changeMsg(taskId, frameResult.getMsg());
                // XXX:没有返回isLast，有返回ret=2
                if (!frameResult.getLast()) {
                    AmmeterFragment.this.sleep(5000);
                    reAutoBatchStatusSend(terminalId, frameResult.getFrameId(),taskId);
                    return;
                }
                taskDialog.setFinish(taskId);
                isFinish();
                AmmeterFragment.this.taskIndex++;
                if (taskIndex < taskIdList.size()) {
                    AmmeterFragment.this.addAutoTask(terminalId);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }
        });
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void sendUpdateTotalElectric() {
        final UpdateTotalElectricityMeterParams params = new UpdateTotalElectricityMeterParams();
        params.setCompanyId(curCompany.getCompanyId());
        params.setEleAccountId(curEleId);
        String meters = totalElectricHolder.leftAdapter.getItemIds();
        params.setMeterIds(meters);
        HttpClientSend.getInstance().send(params, new BaseAlertCallback("总电量配置成功", "总电量配置失败") {
            @Override
            public void onSuccessed(String result) {
            }
        });
    }



    private void isFinish() {
        if (taskIdList.size() == taskDialog.getTaskList().size()) {
            for (TaskResult result : taskDialog.getTaskList()) {
                if (!result.getLast()) {
                    return;
                }
            }
            taskDialog.setFinish();
        }
    }
    private View.OnClickListener closeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            taskStopFlag = true;
            taskDialog.dismiss();
        }
    };
    private void showHoulder(int type) {
        switch (type){
            case 1:
                ammeter_title.setText("采集点配置");
                //召测
                btnGetMetsureStatus.setVisibility(View.GONE);
                ////配置
                btnAddTask.setVisibility(View.VISIBLE);
                //编辑
                btn_add_editor.setVisibility(View.GONE);
                //保存
                btn_save.setVisibility(View.GONE);
                //采集点配置layout.
                layoutCollectionPoint.setVisibility(View.VISIBLE);
                //采集点信息layout.
                layoutCollectionmessage.setVisibility(View.GONE);
                //总电量配置
                layoutTotalElectric.setVisibility(View.GONE);
                //总电量信息
                layoutallelemessage.setVisibility(View.GONE);
                //二级视图
                ammeter_info2.setVisibility(View.VISIBLE);
                //二级提示视图
                ammeter_LL2_hite.setVisibility(View.VISIBLE);
                break;
            case 2:
                ammeter_title.setText("采集点信息");
                //召测
                btnGetMetsureStatus.setVisibility(View.VISIBLE);
                ////配置
                btnAddTask.setVisibility(View.GONE);
                //编辑
                btn_add_editor.setVisibility(View.VISIBLE);
                //保存
                btn_save.setVisibility(View.GONE);
                //采集点配置layout.
                layoutCollectionPoint.setVisibility(View.GONE);
                //采集点信息layout.
                layoutCollectionmessage.setVisibility(View.VISIBLE);
                //总电量配置
                layoutTotalElectric.setVisibility(View.GONE);
                //总电量信息
                layoutallelemessage.setVisibility(View.GONE);
                //二级视图
                ammeter_info2.setVisibility(View.VISIBLE);
                //二级提示视图
                ammeter_LL2_hite.setVisibility(View.VISIBLE);
                break;
            case 3:
                ammeter_title.setText("总电量配置");
                //召测
                btnGetMetsureStatus.setVisibility(View.GONE);
                ////配置
                btnAddTask.setVisibility(View.GONE);
                //编辑
                btn_add_editor.setVisibility(View.GONE);
                //保存
                btn_save.setVisibility(View.VISIBLE);
                //采集点配置layout.
                layoutCollectionPoint.setVisibility(View.GONE);
                //采集点信息layout.
                layoutCollectionmessage.setVisibility(View.GONE);
                //总电量配置
                layoutTotalElectric.setVisibility(View.VISIBLE);
                //总电量信息
                layoutallelemessage.setVisibility(View.GONE);
                //二级视图
                ammeter_info2.setVisibility(View.GONE);
                //二级提示视图
                ammeter_LL2_hite.setVisibility(View.GONE);
                break;
            case 4:
                ammeter_title.setText("总电量信息");
                //召测
                btnGetMetsureStatus.setVisibility(View.GONE);
                ////配置
                btnAddTask.setVisibility(View.GONE);
                //编辑
                btn_add_editor.setVisibility(View.VISIBLE);
                //保存
                btn_save.setVisibility(View.GONE);
                //采集点配置layout.
                layoutCollectionPoint.setVisibility(View.GONE);
                //采集点信息layout.
                layoutCollectionmessage.setVisibility(View.GONE);
                //总电量配置
                layoutTotalElectric.setVisibility(View.GONE);
                //总电量信息
                layoutallelemessage.setVisibility(View.VISIBLE);
                //二级视图
                ammeter_info2.setVisibility(View.GONE);
                //二级提示视图
                ammeter_LL2_hite.setVisibility(View.GONE);
                break;

        }
    }

}
