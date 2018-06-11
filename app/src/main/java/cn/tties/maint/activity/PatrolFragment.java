package cn.tties.maint.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.tties.maint.R;
import cn.tties.maint.adapter.PatrolLayoutAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.dao.PatrolItemDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.PatrolItemEntity;
import cn.tties.maint.enums.EquipmentType;
import cn.tties.maint.enums.OtherPatrolInputType;
import cn.tties.maint.enums.OtherPatrolItemType;
import cn.tties.maint.holder.InfoHolder;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.AddPatrolParam;
import cn.tties.maint.httpclient.params.QueryComEquParams;
import cn.tties.maint.httpclient.params.SelectCompanyEquipmentByAccountParams;
import cn.tties.maint.httpclient.params.SelectEleRoomParams;
import cn.tties.maint.httpclient.params.SelectEquipmentByEleRoomParams;
import cn.tties.maint.httpclient.params.SelectPatrolItemParams;
import cn.tties.maint.httpclient.params.SelectPatrolOrderParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.EleRoomResult;
import cn.tties.maint.httpclient.result.OrderResult;
import cn.tties.maint.httpclient.result.PatrolResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.view.ConfirmDialog;
import cn.tties.maint.view.InfoDialog;
import cn.tties.maint.widget.RecyclerManager;

/**
 * 电房巡视
 * Created by Justin on 2018/1/9.
 */

@ContentView(R.layout.fragment_patrol)
public class PatrolFragment extends BaseEleAccountFragment {

    // 巡视设备详细list
    @ViewInject(R.id.list_patrol_equipment_detail)
    private TagFlowLayout patrolCEquipmentListView;

    // 巡视项目类型list
    @ViewInject(R.id.list_patrol_item)
    private ListView patrolItemListView;

    @ViewInject(R.id.layout_nodata)
    private LinearLayout layoutNodata;


    public static PatrolFragment patrolFragmentInstance;

    private MTagAdapter tagAdapter;
    private PatrolLayoutAdapter patrolItemAdapter;

    private Map<String, Integer> otherEquipType;
    private Map<Integer, OtherPatrolInputType> otherInputType;
    private Integer orderId;
    protected InfoHolder curHolder;
    private ConfirmDialog dialog;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initData();
        initView();
        super.onViewCreated(view, savedInstanceState);
        patrolFragmentInstance = this;
        hideLayout(0);
    }

    @Override
    public void changeEleAccountNextStep() {

    }

    /**
     * 初始化数据.
     */
    public void initData() {
        otherEquipType = new HashMap<String, Integer>();
        otherInputType  = new HashMap<Integer, OtherPatrolInputType>();
        List<EquipmentEntity> baseEquipList = EquipmentDao.getInstance().queryByPid(-1);
        for (EquipmentEntity entity : baseEquipList) {
            for (OtherPatrolItemType e : OtherPatrolItemType.values()) {
                if (e.getInfo().equals(entity.getEquipmentName())) {
                    otherEquipType.put(e.getInfo(), entity.getEquipmentId());
                }
            }
        }
        List<EquipmentEntity> allEquipList = EquipmentDao.getInstance().queryAll();
        for (EquipmentEntity entity : allEquipList) {
            for (OtherPatrolInputType e : OtherPatrolInputType.values()) {
                if (entity.getEquipmentId() == 27) {
                    System.out.print(entity.getEquipmentName());
                }
                if (e.getInfo().equals(entity.getEquipmentName())) {
                    otherInputType.put(entity.getEquipmentId(), e);
                }
            }
        }
    }
    /**
     * 初始化界面.
     */
    private void initView() {
        // 初始化企业设备列表
        this.initTagListView();
        // 初始化巡视项
        this.initDetailListView();

        dialog = new ConfirmDialog(PatrolFragment.this.getActivity());
        curHolder = new InfoHolder(infoLayout);
    }

    @Override
    protected void initLv2ListView() {
        RecyclerManager.initParrolConfig(lv2ListView, getActivity());
        lv2ListView.setSwipeMenuItemClickListener(new MenuItemClickListener());
        super.initLv2ListView();
    }

//    @Override
//    public void changeEleAccountNextStep() {
//        hideLayout(0);
//        clearHolder();
//        getWorkOrderId();
//    }

    @Override
    protected void rootListViewClick(CommonListViewInterface bean) {
        if (orderId == null) {
            getWorkOrderId();
        }
        clearHolder();
        lv2Adapter1.clearCheck();
        // 查询设备列表
        EleRoomResult selectEleRoomResult = (EleRoomResult) bean;
        if (selectEleRoomResult.getRoomId() == otherEquipType.get(OtherPatrolItemType.OUTSIDE.getInfo())) {
            getOutSideEquipment();
        } else if (selectEleRoomResult.getRoomId() == otherEquipType.get(OtherPatrolItemType.ENVIRONMENT.getInfo())) {
            // XXX:特殊处理位附属环境设置显示的设备名.
            curHolder.nameText.setText(selectEleRoomResult.getItemName());
            getEnviromentEquipment();
        } else if (selectEleRoomResult.getRoomId() == otherEquipType.get(OtherPatrolItemType.HEALTH.getInfo())) {
            // XXX:特殊处理位清洁卫生设置显示的设备名.
            curHolder.nameText.setText(selectEleRoomResult.getItemName());
            getHealthEquipment();
        } else if (selectEleRoomResult.getRoomId() == otherEquipType.get(OtherPatrolItemType.TOOL.getInfo())) {
            getTool();
        } else {
            getEleRoomEquipment(bean);
        }
    }

    @Override
    protected void lv2UpListViewClick(CommonListViewInterface bean) {
        CompanyEquipmentResult selEntity = (CompanyEquipmentResult) bean;
        clearHolder();
        lv3Adapter1.clearCheck();
        //大于2级设备显示 详细页面
        if (selEntity.getEquipmentEntity().getIsLeafNode()
                || selEntity.getEquipmentEntity().getEquipmentLevel() > 2
                || selEntity.getEquipmentEntity().getType() == EquipmentType.TRANSFORMER.getValue()) {
            hideLayout(1);
            if (selEntity.getName().equals(OtherPatrolItemType.ROOM.getInfo())
                    || selEntity.getEquipmentEntity().getType() == EquipmentType.TRANSFORMER.getValue()) {
                showPatrolItem(selEntity);
            } else {
                PatrolFragment.this.getTagListView(getSameEquip(selEntity.getEquipmentId(), lv2Adapter1));
            }
        } else {
            showLv3ListView(selEntity);
        }
    }

    @Override
    protected void lv3UpListViewClick(CommonListViewInterface bean) {
        clearHolder();
        CompanyEquipmentResult selEntity = (CompanyEquipmentResult) bean;
        Map<Integer, List<CompanyEquipmentResult>> map = lv3Adapter1.getMap();
        List<CompanyEquipmentResult> list = new ArrayList<CompanyEquipmentResult>();
        if (map.containsKey(selEntity.getEquipmentId())) {
            for (CompanyEquipmentResult entity : map.get(selEntity.getEquipmentId())) {
                if (entity.getEquipmentId() == selEntity.getEquipmentId()) {
                    list.add(entity);
                }
            }
        }
        PatrolFragment.this.getTagListView(list);
    }

    /**
     * 初始化企业设备列表.
     */
    private void initTagListView() {
        tagAdapter = new MTagAdapter();
        tagAdapter.setDataList(new ArrayList<CompanyEquipmentResult>());
        patrolCEquipmentListView.setAdapter(tagAdapter);
        patrolCEquipmentListView.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                showPatrolItem(tagAdapter.getItem(position));
                return false;
            }
        });
    }

    /**
     * 初始化巡视项.
     */
    private void initDetailListView() {
        patrolItemAdapter = new PatrolLayoutAdapter(new ArrayList<PatrolItemEntity>());
        patrolItemListView.setAdapter(patrolItemAdapter);
    }

    /**
     * 查询巡视类型列表.
     */
    public void getPatrolTypeList() {
        if (orderId == null) {
            hideLayout(-1);
            layoutNodata.setVisibility(View.VISIBLE);
            return;
        }
        layoutNodata.setVisibility(View.GONE);
        SelectEleRoomParams params = new SelectEleRoomParams();
        params.setAccountId(curEleId);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                // 获取电房列表
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), "获取电房失败", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<EleRoomResult> eleRoomResultList = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<EleRoomResult>>() {
                        }.getType());
                // 加上室外、附属环境、工器具
                for (OtherPatrolItemType e : OtherPatrolItemType.values()) {
                    if (e.getInfo().equals(OtherPatrolItemType.ROOM.getInfo())) {
                        continue;
                    }
                    EleRoomResult result1 = new EleRoomResult();
                    result1.setRoomName(e.getInfo());
                    result1.setRoomId(otherEquipType.get(e.getInfo()));
                    eleRoomResultList.add(result1);
                }
                rootAdapter.setmDataList(eleRoomResultList);
                rootAdapter.notifyDataSetChanged();
                if (rootAdapter.getChecked() != null) {
                    for (int i=0;i < rootAdapter.getMList().size();i++) {
                        EleRoomResult result1 = (EleRoomResult)rootAdapter.getMList().get(i);
                        if (result1.isChecked()) {
                            rootAdapter.clearCheck();
                            setRootClick(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    //室外
    private void getOutSideEquipment() {
        SelectCompanyEquipmentByAccountParams params = new SelectCompanyEquipmentByAccountParams();
        params.setEleAccountId(curEleId);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CompanyEquipmentResult> companyEquipmentList = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<CompanyEquipmentResult>>() {
                        }.getType());
                showLv2ListView(companyEquipmentList, false);
            }
        });
    }

    //附属环境
    private void getEnviromentEquipment() {
        hideLayout(0);
        CompanyEquipmentResult result = new CompanyEquipmentResult();
        result.setName(OtherPatrolItemType.ENVIRONMENT.getInfo());
        result.setEquipmentId(otherEquipType.get(OtherPatrolItemType.ENVIRONMENT.getInfo()));
        showPatrolItem(result);
    }

    //工具器
    private void getTool() {
        QueryComEquParams params = new QueryComEquParams();
        params.setEquipmentId(otherEquipType.get(OtherPatrolItemType.TOOL.getInfo()));
        params.setEleAccountsId(curEleId);
        params.setPid(-1);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CompanyEquipmentResult> companyEquipmentList = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<CompanyEquipmentResult>>() {
                        }.getType());
                //处理企业设备项目
                Iterator<CompanyEquipmentResult> newListIterator = companyEquipmentList.iterator();
                while (newListIterator.hasNext()) {
                    CompanyEquipmentResult equipment = newListIterator.next();
                    EquipmentEntity equipmentEntity = EquipmentDao.getInstance().queryById(equipment.getEquipmentId());
                    equipment.setEquipmentEntity(equipmentEntity);
                }
                showLv2ListView(companyEquipmentList, false);

            }
        });
    }

    /**
     * 清洁卫生.
     */
    private void getHealthEquipment() {
        hideLayout(0);
        CompanyEquipmentResult result = new CompanyEquipmentResult();
        result.setName(OtherPatrolItemType.HEALTH.getInfo());
        result.setEquipmentId(otherEquipType.get(OtherPatrolItemType.HEALTH.getInfo()));
        showPatrolItem(result);
    }

    //电房
    private void getEleRoomEquipment(CommonListViewInterface bean) {
        EleRoomResult selectEleRoomResult = (EleRoomResult) bean;
        SelectEquipmentByEleRoomParams params = new SelectEquipmentByEleRoomParams();
        params.setEleAccountId(curEleId);
        params.setRoomId(selectEleRoomResult.getRoomId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CompanyEquipmentResult> companyEquipmentList = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<CompanyEquipmentResult>>() {
                        }.getType());
                showLv2ListView(companyEquipmentList, true);
            }
        });
    }

    private void showLv2ListView(List<CompanyEquipmentResult> companyEquipmentList, boolean isRoom) {
        hideLayout(1);
        //处理企业设备项目
        List<CompanyEquipmentResult> showCompanyEquipmentList = new ArrayList<>();
        Iterator<CompanyEquipmentResult> newListIterator = companyEquipmentList.iterator();
        while (newListIterator.hasNext()) {
            CompanyEquipmentResult equipment = newListIterator.next();
            EquipmentEntity equipmentEntity = EquipmentDao.getInstance().queryById(equipment.getEquipmentId());
            if (!equipmentEntity.getHasPatrol()) {  // XXX:对查询出来设备不是巡视设备进行特殊处理
                continue;
            }
            equipment.setEquipmentEntity(equipmentEntity);
            showCompanyEquipmentList.add(equipment);
        }

        if (isRoom) {
            CompanyEquipmentResult body = new CompanyEquipmentResult();
            body.setName(OtherPatrolItemType.ROOM.getInfo());
            body.setRoomId(((EleRoomResult) rootAdapter.getChecked()).getRoomId());
            body.setEquipmentId(otherEquipType.get(OtherPatrolItemType.ROOM.getInfo()));
            EquipmentEntity entity = new EquipmentEntity();
            entity.setEquipmentName(OtherPatrolItemType.ROOM.getInfo());
            entity.setIsLeafNode(true);
            entity.setEquipmentLevel(3);
            entity.setEquipmentId(otherEquipType.get(OtherPatrolItemType.ROOM.getInfo()));
            body.setEquipmentEntity(entity);
            showCompanyEquipmentList.add(0, body);
        }
        setMergeDataList(showCompanyEquipmentList, lv2Adapter1);
        lv2Adapter1.notifyDataSetChanged();

        // 默认点击第一行
        if ((null != companyEquipmentList && companyEquipmentList.size() > 0) || showCompanyEquipmentList.size() > 0) {
            setLv2Click(0);
        }
    }

    private void showLv3ListView(CompanyEquipmentResult bean) {
        hideLayout(2);
        QueryComEquParams params = new QueryComEquParams();
        params.setEquipmentId(bean.getEquipmentId());
        params.setEleAccountsId(curEleId);
        params.setPid(bean.getCompanyEquipmentId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CompanyEquipmentResult> companyEquipmentList = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<CompanyEquipmentResult>>() {
                        }.getType());
                //处理企业设备项目
                Iterator<CompanyEquipmentResult> newListIterator = companyEquipmentList.iterator();
                while (newListIterator.hasNext()) {
                    CompanyEquipmentResult equipment = newListIterator.next();
                    PatrolItemDao patrolItemDao = new PatrolItemDao();
                    List<PatrolItemEntity> patrolItemEntityList = patrolItemDao.queryByEquipmentId(equipment.getEquipmentId());
                    if (patrolItemEntityList.size() == 0) {
                        newListIterator.remove();
                    } else {
                        EquipmentEntity equipmentEntity = EquipmentDao.getInstance().queryById(equipment.getEquipmentId());
                        equipment.setEquipmentEntity(equipmentEntity);
                    }
                }
                setMergeDataList(companyEquipmentList, lv3Adapter1);
                lv3Adapter1.notifyDataSetChanged();

                // 默认点击第一行
                if (null != companyEquipmentList && companyEquipmentList.size() > 0) {
                    setLv3Click(0);
                }
            }
        });
    }

    public void getWorkOrderId() {
        SelectPatrolOrderParams params = new SelectPatrolOrderParams();
        params.setStaffId(MyApplication.getUserInfo().getStaffId());
        params.setCompanyId(curCompany.getCompanyId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String resultStr) {
                BaseResult ret = JsonUtils.deserialize(resultStr, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                OrderResult result = JsonUtils.deserialize(ret.getResult(), OrderResult.class);
                if (result.getWorkOrderId() == null) {
                    curHolder.saveBtn.setVisibility(View.INVISIBLE);
                    orderId = null;
                    return;
                }
                curHolder.saveBtn.setVisibility(View.VISIBLE);
                orderId = result.getWorkOrderId();
                getPatrolTypeList();
            }
        });
    }


    private void getTagListView(List<CompanyEquipmentResult> list) {
        tagAdapter.setDataList(list);
        tagAdapter.notifyDataChanged();
        tagAdapter.setSelectedList(0);
        //默认选中
        showPatrolItem(list.get(0));
    }

    /**
     * 查询企业设备列表.
     */
    private void showPatrolItem(final CompanyEquipmentResult result) {
        infoLayout.setVisibility(View.VISIBLE);
        if (result.getEquipmentEntity() == null) {
            curHolder.nameText.setText(result.getName());
        } else {
            curHolder.nameText.setText(result.getEquipmentEntity().getEquipmentName());
        }
        curHolder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                if (patrolItemAdapter.getDataList().size() < 0) {
                    Toast.makeText(getActivity(), "未选泽设备", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rootAdapter.getChecked() == null) {
                    Toast.makeText(getActivity(), "未选泽电房", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (checkPatrol()) {
                    InfoDialog infoDialog = new InfoDialog();
                    infoDialog.loadDialog(false , "有未选择项目", "");
                    return;
                }
                dialog.loadDialog("", "是否提交巡视报告", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        savePatrolInfo(result);
                    }
                });
            }
        });
        if (orderId != null) {
            SelectPatrolItemParams params = new SelectPatrolItemParams();
            params.setEleAccountId(curEleId);
            params.setWorkOrderId(orderId);
            if (!result.getEquipmentId().equals(otherEquipType.get(OtherPatrolItemType.ROOM.getInfo()))) {
                params.setCompanyEquipmentId(result.getCompanyEquipmentId());
            }
            params.setRoomId(result.getRoomId());
            HttpClientSend.getInstance().send(params, new BaseStringCallback() {
                @Override
                public void onSuccess(String resultStr) {
                    BaseResult ret = JsonUtils.deserialize(resultStr, BaseResult.class);
                    if (ret.getErrorCode() != 0) {
                        Toast.makeText(getActivity(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<PatrolResult> recordResult = JsonUtils.deserialize(ret.getResult(),
                            new TypeToken<List<PatrolResult>>() {
                            }.getType());
                    showPatrolItem(recordResult, result);
                }
            });
        } else {
            showPatrolItem(new ArrayList<PatrolResult>(), result);
        }

    }

    private void showPatrolItem(List<PatrolResult> recordResult, final CompanyEquipmentResult result) {
        //已有record记录转成map
        Map<Integer, PatrolResult> recordMap = new HashMap<Integer, PatrolResult>();
        for (PatrolResult result1 : recordResult) {
            recordMap.put(result1.getPatrolItemId(), result1);
        }
        //该设备所属电房
        EleRoomResult roomResult = (EleRoomResult) rootAdapter.getChecked();
        //该设备巡视项目
        List<PatrolItemEntity> itemEntities = PatrolItemDao.getInstance().queryByEquipId(result.getEquipmentId());
        List<PatrolItemEntity> showItemEntities = new ArrayList<>();
        for (PatrolItemEntity entity : itemEntities) {
            //过滤电房配置
            if (entity.getHigh() != null && entity.getHigh().booleanValue()
                    && (roomResult.getIsHigh() == null || !roomResult.getIsHigh().booleanValue())) {
                continue;
            }
            if (entity.getLow() != null && entity.getLow().booleanValue()
                    && (roomResult.getIsLow() == null || !roomResult.getIsLow().booleanValue())) {
                continue;
            }
            if (entity.getTransfomer() != null && entity.getTransfomer().booleanValue()
                    && (roomResult.getIsTransformer() == null || !roomResult.getIsTransformer().booleanValue())) {
                continue;
            }
            //如果已有巡视记录
            if (recordMap.containsKey(entity.getPatrolItemId())) {
                entity.setResult(recordMap.get(entity.getPatrolItemId()));
            } else {
                entity.setResult(new PatrolResult());
            }
            //页面显示项目
            entity.setInputType(PatrolLayoutAdapter.RADIO);
            if (entity.getHasTemperature() != null || entity.getHasLineTemperature() != null || entity.getHasVoltage() != null || entity.getHasCurrent() != null || entity.getActivePower() != null) {
                entity.setInputType(PatrolLayoutAdapter.INPUT);
            } else if (otherInputType.containsKey(entity.getEquipmentId())) {
                //如果巡视项目是计量柜柜体且是柜体数值项目
                if (otherInputType.get(entity.getEquipmentId()) == OtherPatrolInputType.BODY && entity.getBody()) {
                    entity.setInputType(PatrolLayoutAdapter.GAGUE_BODY);
                }
            }
            //ew 附属环境 清洁卫生 特殊处理
            if (otherEquipType.get(OtherPatrolItemType.ENVIRONMENT.getInfo()).equals(entity.getEquipmentId())) {
                entity.setEquipType(OtherPatrolItemType.ENVIRONMENT.getInfo());
            }
            if (otherEquipType.get(OtherPatrolItemType.HEALTH.getInfo()).equals(entity.getEquipmentId())) {
                entity.setEquipType(OtherPatrolItemType.HEALTH.getInfo());
            }
            showItemEntities.add(entity);
        }
        patrolItemAdapter.setDataList(showItemEntities);
        patrolItemAdapter.notifyDataSetChanged();
    }

    private void clearHolder() {
        curHolder.nameText.setText("请选择一个设备");
        tagAdapter.setDataList(new ArrayList());
        tagAdapter.notifyDataChanged();
        patrolItemAdapter.setDataList(new ArrayList());
        patrolItemAdapter.notifyDataSetChanged();
        infoLayout.setVisibility(View.GONE);
    }

    private void savePatrolInfo(final CompanyEquipmentResult result) {
        AddPatrolParam addPatrolParam = new AddPatrolParam();
        List<PatrolResult> list = new ArrayList<PatrolResult>();
        for (PatrolItemEntity entity : patrolItemAdapter.getDataList()) {
            //新记录需要赋值
            if (entity.getResult().getPatrolRecordId() == null) {
                entity.getResult().setEleAccountId(curEleId);
                entity.getResult().setPatrolItemId(entity.getPatrolItemId());
                entity.getResult().setWorkOrderId(orderId);
                //附属环境和清洁卫生 室外没有roomid
                if (!entity.getEquipmentId().equals(otherEquipType.get(OtherPatrolItemType.ENVIRONMENT.getInfo()))
                        && !entity.getEquipmentId().equals(otherEquipType.get(OtherPatrolItemType.HEALTH.getInfo()))
                        && !entity.getEquipmentId().equals(otherEquipType.get(OtherPatrolItemType.OUTSIDE.getInfo()))) {
                    entity.getResult().setRoomId((rootAdapter.getChecked().getItemId()));
                    //电房本体没有设备id
                    if (!entity.getEquipmentId().equals(otherEquipType.get(OtherPatrolItemType.ROOM.getInfo()))
                            && (null != result.getEquipmentEntity() && result.getEquipmentEntity().getType() != EquipmentType.TRANSFORMER.getValue())) {
                        entity.getResult().setCompanyEquipmentId(tagAdapter.getChecked().getCompanyEquipmentId());
                    }
                    // 变压器特殊处理
                    if (null != result.getEquipmentEntity() && result.getEquipmentEntity().getType() == EquipmentType.TRANSFORMER.getValue()) {
                        entity.getResult().setCompanyEquipmentId(result.getCompanyEquipmentId());
                    }
                }
            }
            list.add(entity.getResult());
        }
        addPatrolParam.setPatrolRecordList(list);
        sendSaveInfo(addPatrolParam, result);
    }

    private Boolean checkPatrol() {
        Boolean undoFlag = false;
        PatrolItemEntity errEntity = null;
        int i = -1;
        for (PatrolItemEntity entity : patrolItemAdapter.getDataList()) {
            i++;
            errEntity = entity;
            if (entity.getInputType() == PatrolLayoutAdapter.RADIO) {
                if (entity.getResult().getHasquestion() == null) {
                    undoFlag = true;
                    break;
                }
            } else if (entity.getInputType() == PatrolLayoutAdapter.GAGUE_BODY) {
                if (entity.getResult().getValue1() == null || StringUtil.isEmpty(entity.getResult().getValue1().trim())
                        || entity.getResult().getValue2() == null || StringUtil.isEmpty(entity.getResult().getValue2().trim())
                        || entity.getResult().getValue3() == null || StringUtil.isEmpty(entity.getResult().getValue3().trim())) {
                    undoFlag = true;
                    break;
                }
            } else {
                if (entity.getHasTemperature() != null) {
                    if (entity.getResult().getValue1() == null || StringUtil.isEmpty(entity.getResult().getValue1().trim())) {
                        undoFlag = true;
                        break;
                    }
                }
                if (entity.getHasLineTemperature() != null) {
                    if (entity.getResult().getValue1() == null || StringUtil.isEmpty(entity.getResult().getValue1().trim())
                            || entity.getResult().getValue2() == null || StringUtil.isEmpty(entity.getResult().getValue2().trim())
                            || entity.getResult().getValue3() == null || StringUtil.isEmpty(entity.getResult().getValue3().trim())) {
                        undoFlag = true;
                        break;
                    }
                }
                if (entity.getHasVoltage() != null && entity.getHasCurrent() != null) {
                    if (entity.getResult().getValue1() == null || StringUtil.isEmpty(entity.getResult().getValue1().trim())
                            || entity.getResult().getValue2() == null || StringUtil.isEmpty(entity.getResult().getValue2().trim())
                            || entity.getResult().getValue3() == null || StringUtil.isEmpty(entity.getResult().getValue3().trim())
                            || entity.getResult().getValue4() == null || StringUtil.isEmpty(entity.getResult().getValue4().trim())
                            || entity.getResult().getValue5() == null || StringUtil.isEmpty(entity.getResult().getValue5().trim())
                            || entity.getResult().getValue6() == null || StringUtil.isEmpty(entity.getResult().getValue6().trim())) {
                        undoFlag = true;
                        break;
                    }
                } else if (entity.getHasVoltage() != null) {
                    if (entity.getResult().getValue1() == null || StringUtil.isEmpty(entity.getResult().getValue1().trim())
                            || entity.getResult().getValue2() == null || StringUtil.isEmpty(entity.getResult().getValue2().trim())
                            || entity.getResult().getValue3() == null || StringUtil.isEmpty(entity.getResult().getValue3().trim())) {
                        undoFlag = true;
                        break;
                    }
                } else if (entity.getHasCurrent() != null) {
                    if (entity.getResult().getValue1() == null || StringUtil.isEmpty(entity.getResult().getValue1().trim())
                            || entity.getResult().getValue2() == null || StringUtil.isEmpty(entity.getResult().getValue2().trim())
                            || entity.getResult().getValue3() == null || StringUtil.isEmpty(entity.getResult().getValue3().trim())) {
                        undoFlag = true;
                        break;
                    }
                }
                if (entity.getActivePower() != null) {
                    if (entity.getResult().getValue1() == null || StringUtil.isEmpty(entity.getResult().getValue1().trim())
                            || entity.getResult().getValue2() == null || StringUtil.isEmpty(entity.getResult().getValue2().trim())) {
                        undoFlag = true;
                        break;
                    }
                }
            }
        }
        if (undoFlag) {
            final int position = i;
            patrolItemListView.post(new Runnable() {
                @Override
                public void run() {
                    patrolItemListView.smoothScrollToPosition(position);
                }
            });
            System.out.println(errEntity.getTitle());
        }
        return undoFlag;
    }

    private void sendSaveInfo(AddPatrolParam addPatrolParam, final CompanyEquipmentResult result) {
        HttpClientSend.getInstance().send(addPatrolParam, new BaseAlertCallback("保存成功","保存失败") {
            @Override
            public void onSuccessed(String rest) {
                showPatrolItem(result);
                // 更新问题列表
                QuestionFragment.mQuestionFragmentInstance.getQuestionList();
            }
        });
    }

    class MTagAdapter extends TagAdapter<CompanyEquipmentResult> {

        private List<CompanyEquipmentResult> dataList;

        CompanyEquipmentResult checkResult;

        public MTagAdapter() {
            super(new ArrayList<CompanyEquipmentResult>());
        }

        public void setDataList(List<CompanyEquipmentResult> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public CompanyEquipmentResult getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public View getView(FlowLayout parent, int position, CompanyEquipmentResult s) {
            CompanyEquipmentResult result = getItem(position);
            final LayoutInflater mInflater = LayoutInflater.from(getActivity());
            TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_text, patrolCEquipmentListView, false);
            tv.setText(result.getName());
            return tv;
        }


        @Override
        public void onSelected(int position, View view) {
            checkResult = dataList.get(position);
        }

        public CompanyEquipmentResult getChecked() {
            return checkResult;
        }
    }

    public class MenuItemClickListener implements SwipeMenuItemClickListener {

        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                CompanyEquipmentResult selEntity = (CompanyEquipmentResult)lv2Adapter1.getItem(adapterPosition);
                //大于2级设备显示 详细页面
                if (selEntity.getEquipmentEntity().getIsLeafNode() || selEntity.getEquipmentEntity().getEquipmentLevel() > 2) {
                    if (selEntity.getName().equals(OtherPatrolItemType.ROOM.getInfo())) {
                        lv3Layout.setVisibility(View.GONE);
                        PatrolFragment.this.showPatrolItem(selEntity);
                    } else {
                        PatrolFragment.this.getTagListView(getSameEquip(selEntity.getEquipmentId(), lv2Adapter1));
                    }
                } else {
                    List<PatrolItemEntity> list = PatrolItemDao.getInstance().queryByEquipmentId(selEntity.getEquipmentId());
                    if (list.size() > 0) {
                        lv3Layout.setVisibility(View.GONE);
                        tagAdapter.setDataList(new ArrayList<CompanyEquipmentResult>());
                        tagAdapter.notifyDataChanged();
                        PatrolFragment.this.showPatrolItem(selEntity);
                    } else {
                        Toast.makeText(x.app(), "没有可巡视项目", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
