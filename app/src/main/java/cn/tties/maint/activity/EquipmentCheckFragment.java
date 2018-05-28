package cn.tties.maint.activity;


import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonAppendListViewAdapter;
import cn.tties.maint.adapter.CommonSwipListViewAdapter;
import cn.tties.maint.adapter.EquipmentCheckDetailsAdapter;
import cn.tties.maint.adapter.EquipmentLayoutAdapter;
import cn.tties.maint.adapter.MyEquipmentCheckEleAdapter;
import cn.tties.maint.adapter.MyEquipmentLayout_RecyAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.CreateBoxBody;
import cn.tties.maint.bean.CreateEquipFourParam;
import cn.tties.maint.bean.CreateEquipItemParam;
import cn.tties.maint.bean.CreateEquipParam;
import cn.tties.maint.bean.CreateEquipTwoParam;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.UpdateEquipParam;
import cn.tties.maint.bean.UpdateItemParam;
import cn.tties.maint.common.Constants;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.dao.EquipmentItemDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.EquipmentItemEntity;
import cn.tties.maint.holder.DistributionHolder;
import cn.tties.maint.holder.EquipmentDetailHolder;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.CopyCompanyEquipmentParams;
import cn.tties.maint.httpclient.params.CreateComEquParams;
import cn.tties.maint.httpclient.params.CreateEleRoomParams;
import cn.tties.maint.httpclient.params.DeleteComEquParams;
import cn.tties.maint.httpclient.params.DeleteEleRoomParams;
import cn.tties.maint.httpclient.params.QueryComEquParams;
import cn.tties.maint.httpclient.params.SaveCompanyEquipmentByRoomIdParams;
import cn.tties.maint.httpclient.params.SelectCEquipAndItemByPidParams;
import cn.tties.maint.httpclient.params.SelectCompanyEquipmentByAccountParams;
import cn.tties.maint.httpclient.params.SelectEleRoomParams;
import cn.tties.maint.httpclient.params.SelectEquItemContentParams;
import cn.tties.maint.httpclient.params.SelectEquipmentByEleRoomParams;
import cn.tties.maint.httpclient.params.UpdateComEquParams;
import cn.tties.maint.httpclient.params.UpdateEleRoomParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CEquipAndItemByPidResult;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.EleRoomResult;
import cn.tties.maint.httpclient.result.EquipmentInfoResult;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.secondLv.RecyclerAdapter;
import cn.tties.maint.util.ACache;
import cn.tties.maint.util.CopyBean;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.view.ConfirmDialog;
import cn.tties.maint.view.CreateEleRoomDialog;
import cn.tties.maint.view.MyPopupWindow;
import cn.tties.maint.view.UpdateEleRoomDialog;
import cn.tties.maint.widget.RecyclerManager;
import cn.tties.maint.widget.WrapContentLinearLayoutManager;

import static cn.tties.maint.dao.EquipmentDao.getInstance;

/**
 * 管理档案
 */
@ContentView(R.layout.fragment_equipment_check)
public class EquipmentCheckFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "EquipmentCheckFragment";
    //详细界面 最大得布局 用于绑定id
    @ViewInject(R.id.layout_info)
    private LinearLayout layoutInfo;
    private List<DataBean> dataBeanList;
    private DataBean dataBean;
    private RecyclerAdapter mAdapter;
    boolean ischeck=false;
    boolean iselecheck=false;
    public EquipmentDetailHolder curHolder;
    //二级top
    protected CommonSwipListViewAdapter rootAdapter;

    protected CommonSwipListViewAdapter lv2Adapter1;

    protected CommonAppendListViewAdapter lv2Adapter2;

    protected CommonSwipListViewAdapter lv3Adapter1;

    protected SwipeItemClickListener rootListener;

    protected SwipeItemClickListener lv2Listener;

    protected CommonAppendListViewAdapter lv3Adapter2;
    private EquipmentLayoutAdapter detailAdapter;
    private MyPopupWindow popupWindow;
    private CompanyEquipmentResult curPidResult;
    public static EquipmentCheckFragment equipmentCheckFragmentInstance;
// -------------电房-----------
    protected CommonSwipListViewAdapter lv2eleAdapter1;
    protected SwipeItemClickListener lv2eleListener;
    private CreateEleRoomDialog createEleRoomDialog;
    private UpdateEleRoomDialog updateEleRoomDialog;
    private DistributionHolder holder;
    /**
     * 电房下设备数量
     */
    private int companyEquipmentCount = 0;
    private EleRoomResult eleRoomResult;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        equipmentCheckFragmentInstance = this;
        initListView();
    }
    private void initListView() {
        curHolder = new EquipmentDetailHolder(layoutInfo);
        holder = new DistributionHolder(curHolder.layout_lv3_ele_configuration);
        initCheck();
        initRootListView();
        initLv2ListView();
        //电房配置二级
        initLv2EleListView();
//        initDetailListView();
        setData();
    }
    //本页得点击事件
    private void initCheck() {
        curHolder.equipment_companyMessage.setOnClickListener(this);
        curHolder.equipment_eleConfiguration.setOnClickListener(this);
        curHolder.btn_editor.setOnClickListener(this);
        curHolder.btn_del.setOnClickListener(this);
        curHolder.btn_new.setOnClickListener(this);
        curHolder.lv2_ele_img.setOnClickListener(this);
        curHolder.btn_ele_save.setOnClickListener(this);
        curHolder.btn_ele_update.setOnClickListener(this);
        curHolder.btn_ele_del.setOnClickListener(this);
        curHolder.btn_ele_editor.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //公司信息
            case R.id.equipment_companyMessage:
                popupWindow.showTipPopupWindow(curHolder.equipment_companyMessage, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            //电房配置
            case R.id.equipment_eleConfiguration:
                if(iselecheck){
                  showHoulder(3);
                }else{
                    ToastUtil.showShort(getActivity(),"当前无任何设备");
                }
                break;
            //新建电房
            case R.id.lv2_ele_img:
                showHoulder(3);
                createEleRoomDialog = new CreateEleRoomDialog(this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createEleRoomDialog.validator.validate();
                    }
                });
                createEleRoomDialog.show();
                break;
            //电房删除
            case R.id.btn_ele_del:
                deleteEleRoom(eleRoomResult);
                break;
            //电房信息修改
            case R.id.btn_ele_update:
                updateEleRoomDialog = new UpdateEleRoomDialog(EquipmentCheckFragment.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateEleRoomDialog.validator.validate();
                    }
                }, eleRoomResult);
                updateEleRoomDialog.show();
                break;
            //电房保存
            case R.id.btn_ele_save:
                saveEle();
                break;
            //电房编辑
            case R.id.btn_ele_editor:
                showHoulder(2);
                eleEditor();
                break;
            //编辑
            case R.id.btn_editor:
                break;
            //删除
            case R.id.btn_del:
                break;
            //新建
            case R.id.btn_new:
                break;
        }
    }



    //有了公司电表Id后查询本体设备类型  二级数据
    @Override
    public void changeEleAccountNextStep() {
        super.changeEleAccountNextStep();
        List<EquipmentEntity> dataList = EquipmentDao.getInstance().queryByLevel(1);
        rootAdapter.setmDataList(dataList);
        rootAdapter.notifyDataSetChanged();
        popupWindow = new MyPopupWindow(curCompany);
        getEleRoomList();
    }
    //二级数据库数据展示
    protected void initRootListView() {
        rootAdapter = new CommonSwipListViewAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        curHolder.listview_lv1_2.setLayoutManager(manager);
        curHolder.listview_lv1_2.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
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
        curHolder.listview_lv1_2.setSwipeItemClickListener(rootListener);
        curHolder.listview_lv1_2.setAdapter(rootAdapter);
    }
    //二级点击请求三级数据
    public void rootListViewClick(CommonListViewInterface bean) {
        showHoulder(1);
        //重置选中及文本
        ischeck=false;
        curHolder.lv2_tv.setText("新建");

        lv2Adapter2.remove();
//        lv3Adapter1.remove();
        EquipmentEntity selBean = (EquipmentEntity) bean;
        CompanyEquipmentResult ceBean = new CompanyEquipmentResult();
        ceBean.setEquipmentEntity(selBean);
        ceBean.setEquipmentId(selBean.getEquipmentId());
        ceBean.setPid(-1);
        ceBean.setCompanyEquipmentId(-1);
        //三级网络请求
        showMuiltListView(ceBean, lv2Adapter1, lv2Adapter2, true);
        curPidResult = ceBean;
        curHolder.lv2_img.setVisibility(View.VISIBLE);
        curHolder.lv2_tv.setVisibility(View.VISIBLE);
    }
    //三级请求数据展示
    protected void initLv2ListView() {
        lv2Adapter1 = new CommonSwipListViewAdapter();
        curHolder.listview_lv2.setLayoutManager(new LinearLayoutManager(getActivity()));
        curHolder.listview_lv2.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
        lv2Listener = new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                CommonListViewInterface selBean = lv2Adapter1.getItem(position);
                if (selBean.isChecked()) {
                    return;
                } else {//切换操作
                    //设置选中
                    lv2Adapter1.setChecked(selBean);
                    lv2Adapter1.notifyDataSetChanged();
                    lv2UpListViewClick(selBean);
                }
            }
        };
        curHolder.listview_lv2.setSwipeItemClickListener(lv2Listener);

        lv2Adapter2 = new CommonAppendListViewAdapter();
        //三级数据库数据展示
        curHolder.listview_lv2_2.setLayoutManager(new LinearLayoutManager(getActivity()));
        curHolder.listview_lv2_2.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                CommonListViewInterface selBean = lv2Adapter2.getItem(position);
                lv2DownListViewClick(selBean);
            }
        });
//        setLv2ListViewMenu();
        curHolder.listview_lv2.setAdapter(lv2Adapter1);
        curHolder.listview_lv2_2.setAdapter(lv2Adapter2);
    }

//    //用于四级得详情条目展示
//    public void initDetailListView() {
//        //模拟数据
//        dataBeanList = new ArrayList<>();
//        for (int i = 1; i <= 3; i++) {
//            dataBean = new DataBean();
//            dataBean.setID(i+"");
//            dataBean.setType(0);
//            dataBean.setParentLeftTxt("开关装置");
//            dataBean.setParentRightTxt("详情");
////            dataBean.setBean();
////            dataBean.setChildLeftTxt("子--"+i);
////            dataBean.setChildRightTxt("子内容--"+i);
//            dataBean.setChildBean(dataBean);
//            dataBeanList.add(dataBean);
//        }
//        setData();
//    }
    //二级详情展示
    private void setData(){
        curHolder.recy_detail.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
        mAdapter = new RecyclerAdapter(getActivity());
//        mAdapter.setDataBeanList(dataBeanList);
        curHolder.recy_detail.setAdapter(mAdapter);
        curHolder.recy_detail.getItemAnimator().setChangeDuration(300);
        curHolder.recy_detail.getItemAnimator().setMoveDuration(300);
    }
//  点击二级数据请求三  四级数据 请求后台
    protected void lv2UpListViewClick(CommonListViewInterface bean) {
        ToastUtil.showShort(getActivity(),"暂无接口");
        CompanyEquipmentResult selBean = (CompanyEquipmentResult) bean;
//        lv3Adapter1.clearCheck();
        selBean.setEquipmentEntity((EquipmentEntity)rootAdapter.getChecked());
        selBean.setEquipmentId(curPidResult.getEquipmentEntity().getEquipmentId());
        selBean.setPid(-1);
        selBean.setCompanyEquipmentId(-1);
        //详细页面 四级详情展示---------------------------------需要接口-据二级id， 查处三四级得数据------------------
//            showMuiltListView(selBean, lv3Adapter1, lv3Adapter2, false);
//        mAdapter.setDataBeanList();
        mAdapter.notifyDataSetChanged();
    }

    //点击新建三级同类型设备
    protected void lv2DownListViewClick(CommonListViewInterface bean) {
        lv2Adapter1.clearCheck();
        EquipmentEntity selBean = (EquipmentEntity) bean;
        curPidResult.setEquipmentEntity((EquipmentEntity)rootAdapter.getChecked());
        curPidResult.setEquipmentId(curPidResult.getEquipmentEntity().getEquipmentId());
        curPidResult.setPid(-1);
        curPidResult.setCompanyEquipmentId(-1);
        Intent intent=new Intent(getActivity(),EquipmentCheckDetailsActivity.class);
        intent.putExtra("bean",bean);
        intent.putExtra("curEleId",curEleId);
        startActivity(intent);
    }
    //请求三级数据及三级本地同设备类型
    private void showMuiltListView(final CompanyEquipmentResult selBean, final CommonSwipListViewAdapter adapter, final CommonAppendListViewAdapter adapter2, final boolean flag) {
        final List<CompanyEquipmentResult> mDataList = new ArrayList<>();
        QueryComEquParams params = new QueryComEquParams();
        params.setEquipmentId(selBean.getEquipmentId());
        params.setEleAccountsId(curEleId);
        params.setPid(selBean.getCompanyEquipmentId());
        //图片旋转效果
        curHolder.lv2_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ischeck){
                    curHolder.layout_lv2_all.setVisibility(View.VISIBLE);
                    ObjectAnimator ra = ObjectAnimator.ofFloat(curHolder.lv2_img,"rotation", 0f, 90f);
                    ra.setDuration(500);
                    ra.start();
                    curHolder.lv2_tv.setText("取消");
                    ischeck=true;
                    List<EquipmentEntity> list = EquipmentDao.getInstance().queryByPid(selBean.getEquipmentId());
                    adapter2.setmDataList(list);
                    adapter2.notifyDataSetChanged();
                }else{
                    ObjectAnimator ra = ObjectAnimator.ofFloat(curHolder.lv2_img,"rotation", 0f, -90f);
                    ra.setDuration(500);
                    ra.start();
                    ischeck=false;
                    curHolder.lv2_tv.setText("新建");
                }
            }

        });
        //二级后台返回数据
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CompanyEquipmentResult> orgList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<CompanyEquipmentResult>>() {
                }.getType());
                List<CompanyEquipmentResult> oldList = adapter.getMList();
                for (CompanyEquipmentResult entity : orgList) {
                    EquipmentEntity equipmentEntity = EquipmentDao.getInstance().queryById(entity.getEquipmentId());
                    entity.setEquipmentEntity(equipmentEntity);
                    mDataList.add(entity);
                }
                if(flag){
                    if(orgList.size()==0){
                        curHolder.layout_lv2_all.setVisibility(View.GONE);
                    }else{
                        curHolder.layout_lv2_all.setVisibility(View.VISIBLE);
                    }
                }

                adapter.setmDataList(mDataList);
//                这是一个公用的请求三四级 数据的一个是否合并的展示
//                setMergeDataList(orgList, adapter);
                adapter.notifyDataSetChanged();
                //电房是否允许点击
                if(mDataList.size()==0){
                    iselecheck=false;
                }else{
                    iselecheck=true;
                }
            }
        });
    }
//                  ----------------电房配置          ---------------
    public void initLv2EleListView(){
        lv2eleAdapter1 = new CommonSwipListViewAdapter();
        curHolder.listview_ele_lv2.setLayoutManager(new LinearLayoutManager(getActivity()));
        curHolder.listview_ele_lv2.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
        lv2eleListener = new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                CommonListViewInterface selBean = lv2eleAdapter1.getItem(position);
                if (selBean.isChecked()) {
                    return;
                } else {//切换操作
                    //设置选中
                    lv2eleAdapter1.setChecked(selBean);
                    lv2eleAdapter1.notifyDataSetChanged();
                    lv2UpEleListViewClick(selBean);
                }
            }
        };
        curHolder.listview_ele_lv2.setSwipeItemClickListener(lv2eleListener);
        curHolder.listview_ele_lv2.setAdapter(lv2eleAdapter1);
    }
    private void getEleRoomList() {
        SelectEleRoomParams params = new SelectEleRoomParams();
        params.setAccountId(curEleId);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), "获取电房失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<EleRoomResult> eleRoomResultList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<EleRoomResult>>() {
                }.getType());
                if(eleRoomResultList.size()==0){
                    showHoulder(4);
                }else{
                    lv2eleAdapter1.setmDataList(eleRoomResultList);
                    lv2eleAdapter1.notifyDataSetChanged();
                }
                // 默认点击第一行
                if (null != eleRoomResultList && eleRoomResultList.size() > 0) {
                    lv2eleListener.onItemClick(curHolder.listview_ele_lv2, 0);
                }
            }
        });
    }
    private void lv2UpEleListViewClick(CommonListViewInterface selBean) {
        String name="";
        ArrayList<Boolean> arrayList=new ArrayList<Boolean>();
        eleRoomResult = (EleRoomResult) selBean;
        curHolder.ele_message_name.setText(eleRoomResult.getRoomName());
        Boolean isHigh = eleRoomResult.getIsHigh();
        Boolean isLow = eleRoomResult.getIsLow();
        Boolean isTransformer = eleRoomResult.getIsTransformer();
        arrayList.add(isHigh);
        arrayList.add(isLow);
        arrayList.add(isTransformer);
        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i)){
                if(i==0){
                    name="  高压电房";
                }
                if(i==1){
                  name=name+"  低压电房";
                }
                if(i==2){
                    name=name+"  变压器室";
                }
            }
        }
        curHolder.ele_message_type.setText(name);
        curHolder.lv3_ele_message_smrecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        final MyEquipmentCheckEleAdapter adapter=new MyEquipmentCheckEleAdapter();
        curHolder.lv3_ele_message_smrecy.setAdapter(adapter);
        SelectEquipmentByEleRoomParams selectEquipmentByEleRoomParams = new SelectEquipmentByEleRoomParams();
        selectEquipmentByEleRoomParams.setRoomId(lv2eleAdapter1.getChecked().getItemId());
        HttpClientSend.getInstance().send(selectEquipmentByEleRoomParams, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), "获取企业设备失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CompanyEquipmentResult> companyEquipmentResults = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<CompanyEquipmentResult>>() {
                        }.getType());
                adapter.setmDataList(companyEquipmentResults);
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void eleEditor(){
//        eleRoomResult = (EleRoomResult) selBean;
       // 查询并显示分配设备
        holder.textRight.setText("未分配");
        holder.textLeft.setText(eleRoomResult.getRoomName());
        this.getEleAccountComEqu();
        this.getEleRoomComEqu();
    }
    /**
     * 保存修改后电房.
     */
    public void saveEle() {
        if (NoFastClickUtils.isFastClick()) {
            return;
        }
        String companyEquipmentId = holder.leftAdapter.getItemIds();
        SaveCompanyEquipmentByRoomIdParams scebrip = new SaveCompanyEquipmentByRoomIdParams();
        scebrip.setCompanyEquipmentId(companyEquipmentId);
        scebrip.setRoomId(lv2eleAdapter1.getChecked().getItemId());
        HttpClientSend.getInstance().send(scebrip, new BaseAlertCallback("电房设备配置成功","电房设备配失败") {
            @Override
            public void onSuccessed(String result) {
                // 更新巡视中的电房
//                PatrolFragment.patrolFragmentInstance.getPatrolTypeList();
                getEleAccountComEqu();
                getEleRoomComEqu();
            }
        });
    }
    /**
     * 创建电房.
     */
    public void createEleRoom() {
        CreateEleRoomParams createEleRoomParams = new CreateEleRoomParams();
        createEleRoomParams.setEleAccountId(curEleId);
        createEleRoomParams.setHigh(createEleRoomDialog.highRoom.isChecked());
        createEleRoomParams.setLow(createEleRoomDialog.lowRoom.isChecked());
        createEleRoomParams.setRoomName(createEleRoomDialog.eleRoomName.getText().toString().trim());
        createEleRoomParams.setTransformer(createEleRoomDialog.transformer.isChecked());
        createEleRoomParams.setFlag(0);
        HttpClientSend.getInstance().send(createEleRoomParams, new BaseAlertCallback("创建电房成功","创建电房失败") {
            @Override
            public void onSuccessed(String result) {
                try {
                    getEleRoomList();
                    createEleRoomDialog.dismiss();
                    // 更新巡视中的电房
//                    PatrolFragment.patrolFragmentInstance.getPatrolTypeList();
                    // 更新检修中的电房
//                    OverhaulFragment.overhaulFragmentInstance.getCompanyEquipmentList();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }
    /**
     * 修改电房.
     */
    public void updateEleRoom(final EleRoomResult eleRoomResult) {
        UpdateEleRoomParams updateEleRoomParams = new UpdateEleRoomParams();
        updateEleRoomParams.setEleAccountId(curEleId);
        updateEleRoomParams.setRoomId(eleRoomResult.getRoomId());
        updateEleRoomParams.setHigh(updateEleRoomDialog.highRoom.isChecked());
        updateEleRoomParams.setLow(updateEleRoomDialog.lowRoom.isChecked());
        updateEleRoomParams.setRoomName(updateEleRoomDialog.eleRoomName.getText().toString().trim());
        updateEleRoomParams.setTransformer(updateEleRoomDialog.transformer.isChecked());
        updateEleRoomParams.setFlag(0);
        HttpClientSend.getInstance().send(updateEleRoomParams, new BaseAlertCallback("更新电房成功","更新电房失败") {
            @Override
            public void onSuccessed(String result) {
                try {
                    getEleRoomList();
                    EleRoomResult eleRoomResult2 = (EleRoomResult) lv2eleAdapter1.getChecked();
                    if (eleRoomResult.getRoomId() == eleRoomResult2.getRoomId()){
                        holder.textLeft.setText(updateEleRoomDialog.eleRoomName.getText().toString().trim());
                    }
//                    PatrolFragment.patrolFragmentInstance.getPatrolTypeList();
                    updateEleRoomDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }
    private void deleteEleRoom(final EleRoomResult eleRoomResult) {
        if (companyEquipmentCount!=0){
            Toast.makeText(getActivity(), "请先移除该电房下设备！", Toast.LENGTH_SHORT).show();
            return;
        }
        ConfirmDialog dialog = new ConfirmDialog(EquipmentCheckFragment.this.getActivity());
        dialog.loadDialog("是否要删除此电房", null, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DeleteEleRoomParams DeleteEleRoomParams = new DeleteEleRoomParams();
                DeleteEleRoomParams.setRoomId(eleRoomResult.getRoomId());
                HttpClientSend.getInstance().send(DeleteEleRoomParams, new BaseAlertCallback("删除电房成功","删除电房失败") {
                    @Override
                    public void onSuccessed(String result) {
                        try {
                            getEleRoomList();
                            // 更新巡视中的电房
                            PatrolFragment.patrolFragmentInstance.getPatrolTypeList();
                            // 更新检修中的电房
                            OverhaulFragment.overhaulFragmentInstance.getCompanyEquipmentList();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                        }
                    }
                });
            }
        });
    }
    /**
     * 查询电房下企业设备
     *
     * @author lizhen
     */
    public void getEleRoomComEqu() {
        SelectEquipmentByEleRoomParams selectEquipmentByEleRoomParams = new SelectEquipmentByEleRoomParams();
        selectEquipmentByEleRoomParams.setRoomId(lv2eleAdapter1.getChecked().getItemId());
        HttpClientSend.getInstance().send(selectEquipmentByEleRoomParams, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), "获取企业设备失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CompanyEquipmentResult> companyEquipmentResults = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<CompanyEquipmentResult>>() {
                        }.getType());
                companyEquipmentCount=companyEquipmentResults.size();
                holder.leftAdapter.setmDataList(companyEquipmentResults);
                holder.leftAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 查询该用电户号下的未在此电房下的设备
     *
     * @author lizhen
     */
    public void getEleAccountComEqu() {
        if (null == lv2eleAdapter1.getChecked()) {
            return;
        }
        SelectCompanyEquipmentByAccountParams selectCompanyEquipmentByAccountParams = new SelectCompanyEquipmentByAccountParams();
        selectCompanyEquipmentByAccountParams.setEleAccountId(curEleId);
        HttpClientSend.getInstance().send(selectCompanyEquipmentByAccountParams, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), "获取企业设备失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CompanyEquipmentResult> companyEquipmentResults = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<CompanyEquipmentResult>>() {
                        }.getType());
                holder.rightAdapter.setmDataList(companyEquipmentResults);
                holder.rightAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showHoulder(int type) {
        switch (type){
            case 1:
                curHolder.layout_equipment_info.setVisibility(View.VISIBLE);
                curHolder.layout_ele_info.setVisibility(View.GONE);
                break;
                //电房配置
            case 2:
                curHolder.text_ele_name.setText("电房配置");
                curHolder.layout_equipment_info.setVisibility(View.GONE);
                curHolder.layout_ele_info.setVisibility(View.VISIBLE);
                curHolder.layout_ele_lv2.setVisibility(View.VISIBLE);
                curHolder.btn_ele_editor.setVisibility(View.GONE);
                curHolder.btn_ele_del.setVisibility(View.VISIBLE);
                curHolder.btn_ele_save.setVisibility(View.VISIBLE);
                curHolder.btn_ele_update.setVisibility(View.VISIBLE);
                curHolder.layout_lv3_ele_message.setVisibility(View.GONE);
                curHolder.layout_lv3_ele_configuration.setVisibility(View.VISIBLE);
                break;
            //配置信息
            case 3:
                curHolder.text_ele_name.setText("配置信息");
                curHolder.layout_equipment_info.setVisibility(View.GONE);
                curHolder.layout_ele_info.setVisibility(View.VISIBLE);
                curHolder.layout_ele_lv2.setVisibility(View.VISIBLE);
                curHolder.btn_ele_editor.setVisibility(View.VISIBLE);
                curHolder.btn_ele_del.setVisibility(View.GONE);
                curHolder.btn_ele_save.setVisibility(View.GONE);
                curHolder.btn_ele_update.setVisibility(View.GONE);
                curHolder.layout_lv3_ele_message.setVisibility(View.VISIBLE);
                curHolder.layout_lv3_ele_configuration.setVisibility(View.GONE);
                break;
            //当前无电房
            case 4:
                curHolder.text_ele_name.setText("电房配置");
                curHolder.layout_equipment_info.setVisibility(View.GONE);
                curHolder.layout_ele_info.setVisibility(View.VISIBLE);
                curHolder.layout_lv2_ele_all.setVisibility(View.GONE);
                curHolder.layout_lv2_ele_hite.setVisibility(View.VISIBLE);
                curHolder.layout_lv3_ele_all.setVisibility(View.GONE);
                curHolder.layout_lv3_ele_hite.setVisibility(View.VISIBLE);
                curHolder.layout_ele_lv2.setVisibility(View.GONE);
                curHolder.btn_ele_editor.setVisibility(View.GONE);
                curHolder.btn_ele_del.setVisibility(View.GONE);
                curHolder.btn_ele_save.setVisibility(View.GONE);
                curHolder.btn_ele_update.setVisibility(View.GONE);
                curHolder.layout_lv3_ele_message.setVisibility(View.GONE);
                curHolder.layout_lv3_ele_configuration.setVisibility(View.GONE);
                break;
        }
    }
}
