package cn.tties.maint.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
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
import cn.tties.maint.holder.DistributionHolder;
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
import cn.tties.maint.view.CriProgressDialog;
import cn.tties.maint.view.InfoDialog;
import cn.tties.maint.view.MeterInfoDialog;
import cn.tties.maint.view.TaskDialog;

/**
 * 电表配置.
 */
@ContentView(R.layout.fragment_ammeter)
public class AmmeterFragment extends BaseEleAccountFragment {
    /**
     * 基础信息layout.
     */
    @ViewInject(R.id.layout_baseinfo)
    private LinearLayout layoutBaseinfo;
    /**
     * 账户信息layout.
     */
    @ViewInject(R.id.layout_account)
    private LinearLayout layoutAccount;
    /**
     * 采集点信息layout.
     */
    @ViewInject(R.id.layout_collection_point)
    private LinearLayout layoutCollectionPoint;
    /**
     * 总电量信息layout.
     */
    @ViewInject(R.id.layout_total_electric)
    private LinearLayout layoutTotalElectric;

    @ViewInject(R.id.btn_getMetsureStatus)
    public Button btnGetMetsureStatus;

    @ViewInject(R.id.btn_add_task)
    public Button btnAddTask;

    /**
     * 基础信息holder.
     */
    private BaseInfoHolder baseInfoHolder;
    /**
     * 账户holder.
     */
    private AccountHolder accountHolder;
    /**
     * 采集点holder.
     */
    private CollectionHolder collectionHolder;
    /**
     * 总电量holder.
     */
    private DistributionHolder totalElectricHolder;
    /**
     * 基本holder.
     */
    protected InfoHolder curHolder;
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

    //变压器列表
    List<CompanyEquipmentResult> totalTransformerList;
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
        initView();
        dialog = new CriProgressDialog(AmmeterFragment.this.getActivity());
        infoDialog = new InfoDialog();
        taskDialog = new TaskDialog(closeClick);
        taskStopFlag = false;
        hideLayout(-1);
    }

    @Override
    public void changeEleAccountNextStep() {
        hideLayout(0);
        rootAdapter.clearCheck();
        getAmmeterTypeListView();
        //变压器列表
        getTransformer();
    }

    /**
     * 初始化界面.
     */
    private void initView() {
        //基础页面
        curHolder = new InfoHolder(infoLayout);
        //基础信息页面
        baseInfoHolder = new BaseInfoHolder(layoutBaseinfo);

        //功率因素
        powerFactorList = new ArrayList<>();
        powerFactorList.add("0.9");
        powerFactorList.add("0.8");
        powerFactorList.add("0.85");
        ArrayAdapter<String> powerFactorAdapter = new ArrayAdapter<>(x.app(), R.layout.spinner_text, powerFactorList);
        baseInfoHolder.spinnerPowerFactor.setAdapter(powerFactorAdapter);
        //账户页面
        accountHolder = new AccountHolder(layoutAccount);
        //采集点页面
        collectionHolder = new CollectionHolder(layoutCollectionPoint);
        //总电量页面
        totalElectricHolder = new DistributionHolder(layoutTotalElectric);
    }

    private void getTransformer() {
        GetTransformerParams params = new GetTransformerParams();
        params.setEleAccountId(curEleId);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), "获取运维专员失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                totalTransformerList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<CompanyEquipmentResult>>() {
                }.getType());
            }
        });
    }
    /**
     * 初始化电表配置类型.
     */
    private void getAmmeterTypeListView() {
        List<CommonListViewBean> list = new ArrayList<>();
        if (curCompany.getEnergyCompanyId() == null) {
            CommonListViewBean bean = new CommonListViewBean(AmmeterType.BASE_INFO.getValue(), AmmeterType.BASE_INFO.getInfo());
            list.add(bean);
        } else {
            for (AmmeterType e : AmmeterType.values()) {
                CommonListViewBean bean = new CommonListViewBean(e.getValue(), e.getInfo());
                list.add(bean);
            }
        }
        rootAdapter.setmDataList(list);
        rootAdapter.notifyDataSetChanged();
    }

    /**
     * rootlistView点击事件.
     *
     * @param bean 选中的bean.
     */
    @Override
    protected void rootListViewClick(CommonListViewInterface bean) {
        hideLayout(1);
        lv2Adapter1.clearCheck();
        if (bean.getItemId() == AmmeterType.BASE_INFO.getValue()) {
            showUpdateDetail(bean);
        }
        if (bean.getItemId() == AmmeterType.ACCOUNT.getValue()) {
            getAccount();
        }
        if (bean.getItemId() == AmmeterType.COLLECTION_POINT.getValue()) {
            getCollection();
        }
        if (bean.getItemId() == AmmeterType.TOTAL_ELECTRIC.getValue()) {
            showUpdateDetail(bean);
        }
    }

    /**
     * 2级上方listview点击事件.
     *
     * @param bean 选中的bean.
     */
    @Override
    protected void lv2UpListViewClick(CommonListViewInterface bean) {
        showUpdateDetail(bean);
    }

    /**
     * 2级上方listview点击事件.
     *
     * @param bean 选中的bean.
     */
    @Override
    protected void lv2DownListViewClick(CommonListViewInterface bean) {
        lv2Adapter1.clearCheck();
        showAddDetail(bean, lv2Adapter1);
    }

    /**
     * 获取账户.
     */
    private void getAccount() {
        QueryEnergyUserParams queryEnergyUserParams = new QueryEnergyUserParams();
        queryEnergyUserParams.setCompanyId(curCompany.getCompanyId());
        HttpClientSend.getInstance().send(queryEnergyUserParams, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<QueryEnergyUserResult> queryEnergyUserResult = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<QueryEnergyUserResult>>() {
                }.getType());
                showLv2ListView(queryEnergyUserResult);
            }
        });
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
                showLv2ListView(meterList);
            }
        });
    }

    /**
     * 显示2级listview.
     *
     * @param list 上方列表
     */
    private void showLv2ListView(List list) {
        //上方列表
        lv2Layout.setVisibility(View.VISIBLE);
        lv2Adapter1.setmDataList(list);
        lv2Adapter1.notifyDataSetChanged();

        //下方列表
        List<CommonListViewBean> addList = new ArrayList<>();
        if (rootAdapter.getChecked().getItemId() == AmmeterType.ACCOUNT.getValue()) {
            CommonListViewBean bean = new CommonListViewBean(AmmeterType.ACCOUNT.getValue(), "增加账户");
            addList.add(bean);
        }
        if (rootAdapter.getChecked().getItemId() == AmmeterType.COLLECTION_POINT.getValue()) {
            CommonListViewBean bean = new CommonListViewBean(AmmeterType.COLLECTION_POINT.getValue(), "增加采集点");
            addList.add(bean);
        }
        lv2Adapter2.setmDataList(addList);
        lv2Adapter2.notifyDataSetChanged();
        //默认点击
        if (list.size() > 0 && lv2Adapter1.getChecked() == null) {
            setLv2Click(0);
        } else {
            List<CommonListViewInterface> beanlist = lv2Adapter1.getMList();
            for (int i =0;i< beanlist.size();i++) {
                if (beanlist.get(i).isChecked()) {
                    lv2Adapter1.clearCheck();
                    setLv2Click(i);
                }
            }
        }
    }

    /**
     * 显示新增页面.
     *
     * @param bean    rootlistview选中的bean
     * @param adapter 上方listview的adapter
     */
    private void showAddDetail(CommonListViewInterface bean, CommonSwipListViewAdapter adapter) {
        if (bean.getItemId() == AmmeterType.ACCOUNT.getValue()) {
            showHoulder(layoutAccount);
            accountHolder.clear();
            accountHolder.dbId = null;
            curHolder.nameText.setText(AmmeterType.ACCOUNT.getInfo());
            curHolder.saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NoFastClickUtils.isFastClick()) {
                        return;
                    }
                    requestType = 2;
                    accountHolder.validator(AmmeterFragment.this);

                }
            });
        }
        if (bean.getItemId() == AmmeterType.COLLECTION_POINT.getValue()) {
            showHoulder(layoutCollectionPoint);
            collectionHolder.clear();

            List<String> strList = getTranformerList(null);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(x.app(), R.layout.spinner_text, strList);
            collectionHolder.spinnerTransformer.setAdapter(adapter1);

            accountHolder.dbId = null;
            curHolder.nameText.setText(AmmeterType.COLLECTION_POINT.getInfo());
            curHolder.saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NoFastClickUtils.isFastClick()) {
                        return;
                    }
                    requestType = 1;
                    collectionHolder.validator(AmmeterFragment.this);
                }
            });
        }
    }

    /**
     * 显示明细页面.
     *
     * @param bean 选中的bean
     */
    private void showUpdateDetail(CommonListViewInterface bean) {
        if (rootAdapter.getChecked().getItemId() == AmmeterType.BASE_INFO.getValue()) {
            showBaseInfoDetail();
        }
        if (rootAdapter.getChecked().getItemId() == AmmeterType.ACCOUNT.getValue()) {
            showAccountDetail(bean);
        }
        if (rootAdapter.getChecked().getItemId() == AmmeterType.COLLECTION_POINT.getValue()) {
            showCollectionDetail(bean);
        }
        if (rootAdapter.getChecked().getItemId() == AmmeterType.TOTAL_ELECTRIC.getValue()) {
            showTotalElectricDetail();
        }
    }

    /**
     * 显示基础信息更新界面.
     */
    private void showBaseInfoDetail() {
        lv2Layout.setVisibility(View.GONE);
        showHoulder(layoutBaseinfo);
        curHolder.nameText.setText(AmmeterType.BASE_INFO.getInfo());

        //用电性质列表
        final List<String> natureList = PowerUtilizationNatureType.getInfoList();
        ArrayAdapter<String> electricAdapter = new ArrayAdapter<>(x.app(), R.layout.spinner_text, natureList);
        baseInfoHolder.spinnerPowerUtilizationNature.setAdapter(electricAdapter);
        //电压等级列表
        final List<String> streList = new ArrayList<>();
        List<VoltageLevelType> voltageList = VoltageLevelType.getVoltagerLevelListByNature(PowerUtilizationNatureType.getTpye(PowerUtilizationNatureType.getInfoList().get(0)));
        for (VoltageLevelType e : voltageList) {
            streList.add(e.getInfo());
        }
        ArrayAdapter<String> voltageAdapter = new ArrayAdapter<>(x.app(), R.layout.spinner_text, streList);
        baseInfoHolder.spinnerVoltageLevel.setAdapter(voltageAdapter);
        final RateEntity entity = RateDao.getInstance().queryByRateId(curCompany.getRateId());
        //费率不为空，设定数据库数据
        if (curCompany.getRateId() != null) {
            int i = 0;
            for (PowerUtilizationNatureType e : PowerUtilizationNatureType.values()) {
                if (e.getValue() == entity.getNature()) {
                    baseInfoHolder.spinnerPowerUtilizationNature.setSelection(i);
                    break;
                }
                i++;
            }
            i = 0;
            for (String str : powerFactorList) {
                if (Double.valueOf(str).equals(curCompany.getPowerFactor())) {
                    baseInfoHolder.spinnerPowerFactor.setSelection(i);
                    break;
                }
                i++;
            }
        }
        baseInfoHolder.spinnerPowerUtilizationNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long var) {
                //用电性质联动电压等级
                List<String> voltageLevelList = new ArrayList<>();

                for (VoltageLevelType e : VoltageLevelType.getVoltagerLevelListByNature(PowerUtilizationNatureType.getTpye(PowerUtilizationNatureType.getInfoList().get(postion)))) {
                    voltageLevelList.add(e.getInfo());
                }
                ArrayAdapter<String> voltageLevelAdapter = new ArrayAdapter<>(x.app(), R.layout.spinner_text, voltageLevelList);
                baseInfoHolder.spinnerVoltageLevel.setAdapter(voltageLevelAdapter);

                if (entity == null) {
                    return;
                }
                int i = 0;
                for (VoltageLevelType e : VoltageLevelType.getVoltagerLevelListByNature(PowerUtilizationNatureType.getTpye(PowerUtilizationNatureType.getInfoList().get(postion)))) {
                    if (e.getType() == entity.getVoltageLevel()) {
                        baseInfoHolder.spinnerVoltageLevel.setSelection(i);
                        break;
                    }
                    i++;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        curHolder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                updateBaseInfo();
            }
        });
    }

    /**
     * 显示账户信息更新界面.
     *
     * @param bean 选中的账户
     */
    private void showAccountDetail(CommonListViewInterface bean) {
        QueryEnergyUserResult result = (QueryEnergyUserResult) bean;
        showHoulder(layoutAccount);
        accountHolder.dbId = bean.getItemId();
        accountHolder.editUid.setText(result.getUserName());
        accountHolder.editUsername.setText(result.getRealUserName());
        curHolder.nameText.setText(bean.getItemName());
        curHolder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                requestType = 2;
                accountHolder.validator(AmmeterFragment.this);
            }
        });
    }

    /**
     * 显示采集点更新界面.
     *
     * @param bean 选中的采集点
     */
    private void showCollectionDetail(final CommonListViewInterface bean) {
        showHoulder(layoutCollectionPoint);
        final MeterResult result = (MeterResult) bean;
        collectionHolder.clear();
        collectionHolder.editAmmeterNumber.setText(setNullEdit(result.getAmmeterNumber()));
        collectionHolder.editCollectonPointName.setText(setNullEdit(result.getMeterName()));
        collectionHolder.editTerminalAddress.setText(setNullEdit(result.getTerminalAddress()));
        collectionHolder.editGPRS.setText(setNullEdit(result.getGprsCardNo()));
        collectionHolder.editVoltageTransformerRatio.setText(setNullEdit(result.getTv()));
        collectionHolder.editCurrentTransformerRatio.setText(setNullEdit(result.getTa()));
        collectionHolder.editRatedCurrent.setText(setNullEdit(result.getRatedCurrent()));
        collectionHolder.editRatedVoltage.setText(setNullEdit(result.getRatedVoltage()));
        collectionHolder.editRatedPower.setText(setNullEdit(result.getRatedPower()));
        collectionHolder.editAreaCode.setText(setNullEdit(result.getAreaCode()));
        collectionHolder.point.setText(setNullEdit(result.getPoint()));
        if (result.getConnMode() !=  null) {
            int i =0;
            for (ConnModeType e : ConnModeType.values()) {
                if (e.getType() == result.getConnMode()) {
                    collectionHolder.spinnerConnMode.setSelection(i);
                }
                i++;
            }
        }
        if (result.getEquipmentType() !=  null) {
            int i =0;
            for (MeterEquipmentType e : MeterEquipmentType.values()) {
                if (e.getType() == result.getEquipmentType()) {
                    collectionHolder.equipmentType.setSelection(i);
                }
                i++;
            }
        }
        if (result.getCommProtocolType() !=  null) {
            int i =0;
            for (CommProtocolType e : CommProtocolType.values()) {
                if (e.getType() == result.getCommProtocolType()) {
                    collectionHolder.commProtocolType.setSelection(i);
                }
                i++;
            }
        }
        if (result.getLineInType() !=  null && result.getLineInType() > 0) {
            collectionHolder.lineInType.setSelection(result.getLineInType().intValue() - 1);
        }
        //循环该电号下全部变压器
        List<String> strList = getTranformerList(result);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(x.app(), R.layout.spinner_text, strList);
        collectionHolder.spinnerTransformer.setAdapter(adapter);
        int i =1;
        for (CompanyEquipmentResult result1 : transformerList) {
            if (result1.getCompanyEquipmentId().equals(result.getTransformerId())) {
                collectionHolder.spinnerTransformer.setSelection(i);
            }
            i++;
        }
        curHolder.nameText.setText(setNullEdit(setNullEdit(result.getItemName())));
        curHolder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                collectionHolder.dbId = result.getMeterId();
                requestType = 1;
                collectionHolder.validator(AmmeterFragment.this);
            }
        });

        btnGetMetsureStatus.setVisibility(View.VISIBLE);
        btnGetMetsureStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                sendGetMetsureStatus(result);
            }
        });

        btnAddTask.setVisibility(View.VISIBLE);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                addAutoTask(result.getDcTerminalId(), result.getEquipmentType());
            }
        });
    }

    private List<String> getTranformerList(MeterResult meter) {
        transformerList = (ArrayList)((ArrayList)totalTransformerList).clone();
        List<MeterResult> list = lv2Adapter1.getMList();
        for (MeterResult result : list) {
            if (result.getTransformerId() == null) {
                continue;
            }
            for (CompanyEquipmentResult result1 : transformerList) {
                if (meter != null) {
                    if (result1.getCompanyEquipmentId().equals(result.getTransformerId()) && result.getTransformerId() != meter.getTransformerId()) {
                        transformerList.remove(result1);
                        break;
                    }
                } else {
                    if (result1.getCompanyEquipmentId().equals(result.getTransformerId())) {
                        transformerList.remove(result1);
                        break;
                    }
                }
            }
        }
        List<String> spinnerList = new ArrayList<>();
        spinnerList.add("不关联");
        for (CompanyEquipmentResult result1 : transformerList) {
            spinnerList.add(result1.getName());
        }
        return spinnerList;
    }

    /**
     * 显示总电量界面.
     */
    private void showTotalElectricDetail() {
        lv2Layout.setVisibility(View.GONE);
        showHoulder(layoutTotalElectric);
        totalElectricHolder.textLeft.setText(curEleNo + "总电量");
        curHolder.nameText.setText(AmmeterType.TOTAL_ELECTRIC.getInfo());
        getTotalElectricity();
        curHolder.saveBtn.setOnClickListener(new View.OnClickListener() {
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

    /**
     * 显示界面.
     *
     * @param curView 当前界面.
     */
    private void showHoulder(View curView) {
        infoLayout.setVisibility(View.VISIBLE);
        btnGetMetsureStatus.setVisibility(View.GONE);
        btnAddTask.setVisibility(View.GONE);
        layoutCollectionPoint.setVisibility(View.GONE);
        layoutAccount.setVisibility(View.GONE);
        layoutBaseinfo.setVisibility(View.GONE);
        layoutTotalElectric.setVisibility(View.GONE);
        curView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onValidationSucceeded() {
        switch (requestType) {
            case 1:
                this.meterConfigRequest();
                break;
            case 2:
                this.sendAddAccount();
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
    private void meterConfigRequest() {
        dialog.loadDialog("采集点信息配置中...");
        final SaveOrUpdateMeterConfigParams meterParams = new SaveOrUpdateMeterConfigParams();
        meterParams.setMeterId(collectionHolder.dbId);
        meterParams.setEleAccountId(curEleId);
        meterParams.setCompanyId(curCompany.getCompanyId());
        meterParams.setMeterName(collectionHolder.editCollectonPointName.getText().toString());
//        meterParams.setAmmeterNumber(Integer.parseInt(collectionHolder.editAmmeterNumber.getText().toString()));
        meterParams.setTerminalAddress(collectionHolder.editTerminalAddress.getText().toString());
        meterParams.setGprsCardNo(collectionHolder.editGPRS.getText().toString());
        meterParams.setTv(Integer.parseInt(collectionHolder.editVoltageTransformerRatio.getText().toString()));
        meterParams.setTa(Integer.parseInt(collectionHolder.editCurrentTransformerRatio.getText().toString()));
        meterParams.setRatedCurrent(Integer.parseInt(collectionHolder.editRatedCurrent.getText().toString()));
        meterParams.setRatedPower(Integer.parseInt(collectionHolder.editRatedPower.getText().toString()));
        meterParams.setRatedVoltage(Integer.parseInt(collectionHolder.editRatedVoltage.getText().toString()));
        meterParams.setConnMode(ConnModeType.getValue(String.valueOf(collectionHolder.spinnerConnMode.getSelectedItem())));
        meterParams.setAreaCode(collectionHolder.editAreaCode.getText().toString());
        meterParams.setPoint(Long.valueOf(collectionHolder.point.getText().toString()));
        meterParams.setEquipmentType(MeterEquipmentType.getValue(String.valueOf(collectionHolder.equipmentType.getSelectedItem())));
        meterParams.setCommProtocolType(CommProtocolType.getValue(String.valueOf(collectionHolder.commProtocolType.getSelectedItem())));
        meterParams.setLineInType(LineInType.getValue(String.valueOf(collectionHolder.lineInType.getSelectedItem())));
        if (collectionHolder.spinnerTransformer.getSelectedItemPosition() != 0) {
            meterParams.setTransformerId(transformerList.get(collectionHolder.spinnerTransformer.getSelectedItemPosition() - 1).getCompanyEquipmentId());
        }
        HttpClientSend.getInstance().send(meterParams, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    infoDialog.loadDialog(false, "采集点配置失败", ret.getErrorMessage());
                    dialog.removeDialog();
                    return;
                }
                JsonObject element = ret.getResult().getAsJsonObject();
                boolean flag = false;
                if (element.has("isNewTerm")) {
                    if (element.get("isNewTerm").getAsBoolean()) {
                        flag = true;
                    }
                }
                //新建场合 自动执行任务配置
                if  (collectionHolder.dbId == null && flag) {
                    //配置任务
                    AmmeterFragment.this.sleep(5000);
                    addAutoTask(element.get("terminalId").getAsLong(), meterParams.getEquipmentType());
                } else {
                    dialog.removeDialog();
                    infoDialog.loadDialog(true, "采集点配置更新成功", null);
                }
                //刷新列表
                getCollection();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog.removeDialog();
                infoDialog.loadDialog(false, "采集点配置失败",null);
                ex.printStackTrace();
            }
        });
    }

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

    private void sendAddAccount() {
        final AddUserEnergyActionParams addUserEnergyActionParams = new AddUserEnergyActionParams();
        addUserEnergyActionParams.setCompanyId(curCompany.getCompanyId());
        addUserEnergyActionParams.setUserId(accountHolder.dbId);
        addUserEnergyActionParams.setRealUserName(accountHolder.editUsername.getText().toString());
        addUserEnergyActionParams.setUserName(accountHolder.editUid.getText().toString());
        addUserEnergyActionParams.setUserPwd(accountHolder.editPassword.getText().toString());
        String successStr = "账户添加成功";
        String errStr = "账户添加失败";
        if (accountHolder.dbId != null ) {
            successStr = "账户更新成功";
            errStr = "账户更新失败";
        }
        HttpClientSend.getInstance().send(addUserEnergyActionParams, new BaseAlertCallback(successStr, errStr) {
            @Override
            public void onSuccessed(String result) {
                if (accountHolder.dbId == null) {
                    accountHolder.clear();
                } else {
                    setClear(accountHolder.editPassword);
                    setClear(accountHolder.editPasswordConfirm);
                }
                getAccount();
            }
        });
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

    private void updateBaseInfo() {
        AddEnereyCompanyParams params = new AddEnereyCompanyParams();
        params.setCompanyId(curCompany.getCompanyId());
        String powerFactor = (String) baseInfoHolder.spinnerPowerFactor.getSelectedItem();
        params.setNature(PowerUtilizationNatureType.getValue(baseInfoHolder.spinnerPowerUtilizationNature.getSelectedItem().toString()));
        params.setVoltageLevel(VoltageLevelType.getValue(baseInfoHolder.spinnerVoltageLevel.getSelectedItem().toString(), PowerUtilizationNatureType.getTpye(params.getNature())));
        params.setPowerFactor(Double.parseDouble(powerFactor));
        HttpClientSend.getInstance().send(params, new BaseAlertCallback("基础信息更新成功", "基础信息更新失败") {
            @Override
            public void onSuccessed(String result) {
                refreshCompany();
            }
        });
    }

    private void refreshCompany() {
        CompanyParamsById paramsById = new CompanyParamsById();
        paramsById.setCompanyId(curCompany.getCompanyId());
        HttpClientSend.getInstance().send(paramsById , new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    //错误
                } else {
                    CompanyResult companyResult = JsonUtils.deserialize(ret.getResult(), CompanyResult.class);
                    curCompany.setRateId(companyResult.getRateId());
                    curCompany.setPowerFactor(companyResult.getPowerFactor());
                    curCompany.setEnergyCompanyId(companyResult.getEnergyCompanyId());
                }
                showBaseInfoDetail();
                getAmmeterTypeListView();
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
}
