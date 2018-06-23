package cn.tties.maint.activity;


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonAppendListViewAdapter;
import cn.tties.maint.adapter.CommonSwipListViewAdapter;
import cn.tties.maint.adapter.DetailsLv2ListViewAdapter;
import cn.tties.maint.adapter.EquiRecyclerAdapter;
import cn.tties.maint.adapter.EquipmentAddressListViewAdapter;
import cn.tties.maint.adapter.EquipmentLayoutAdapter;
import cn.tties.maint.adapter.EquipmentRootListViewAdapter;
import cn.tties.maint.adapter.MyEquipmentCheckEleAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.EquipmentcheckBean;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.EventKind;
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
import cn.tties.maint.httpclient.params.CreateEleRoomParams;
import cn.tties.maint.httpclient.params.DeleteComEquParams;
import cn.tties.maint.httpclient.params.DeleteEleRoomParams;
import cn.tties.maint.httpclient.params.QueryComEquParams;
import cn.tties.maint.httpclient.params.SaveCompanyEquipmentByRoomIdParams;
import cn.tties.maint.httpclient.params.SelectCompanyEquipmentByAccountParams;
import cn.tties.maint.httpclient.params.SelectCompanyEquipmentByCompanyParams;
import cn.tties.maint.httpclient.params.SelectEleRoomParams;
import cn.tties.maint.httpclient.params.SelectEquipmentByEleRoomParams;
import cn.tties.maint.httpclient.params.UpdateEleRoomParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.EleRoomResult;
import cn.tties.maint.httpclient.result.EquipmentInfoResult;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.adapter.RecyclerAdapter;
import cn.tties.maint.util.ACache;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.view.AllCancelDialog;
import cn.tties.maint.view.AllEditorDialog;
import cn.tties.maint.view.ConfirmDialog;
import cn.tties.maint.view.CreateEleRoomDialog;
import cn.tties.maint.view.MyPopupWindow;
import cn.tties.maint.view.UpdateEleRoomDialog;
import cn.tties.maint.widget.WrapContentLinearLayoutManager;

import static cn.tties.maint.dao.EquipmentDao.getInstance;

/**
 * 管理档案
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_equipment_check)
public class EquipmentCheckFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "EquipmentCheckFragment";
    //详细界面 最大得布局 用于绑定id
    @ViewInject(R.id.layout_info)
    private LinearLayout layoutInfo;
    private List<DataBean> dataBeanList= new ArrayList<>();
    private DataBean dataBean;
    public EquiRecyclerAdapter mAdapter;
    boolean ischeck=false;
    boolean iselecheck=false;
    public EquipmentDetailHolder curHolder;
    //二级top
    protected EquipmentRootListViewAdapter rootAdapter;

    protected DetailsLv2ListViewAdapter lv2Adapter1;

    protected EquipmentAddressListViewAdapter lv2Adapter2;

    protected SwipeItemClickListener rootListener;

    protected SwipeItemClickListener lv2Listener;

    //加载节点布局
    private EquipmentLayoutAdapter detailAdapter;
    private MyPopupWindow popupWindow;
    private CompanyEquipmentResult curPidResult;
    private boolean cabinet=false;
    public static EquipmentCheckFragment equipmentCheckFragmentInstance;
    //后台请求得二三四级数据
    private EquipmentcheckBean eleRoomResultList;
    private CommonListViewInterface selBean;
// -------------电房-----------
    int unselect = Color.parseColor("#BDC8D8");
    int select = Color.parseColor("#ffffff");
    int textselect = Color.parseColor("#1B92EE");
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
    private List<EquipmentEntity> list;
    private CompanyEquipmentResult ceBean;
    private Integer currentItemId;
    private CommonListViewInterface beans;

//
    private CompanyResult curCompany;
    private Integer curEleId;
    String curEleNo;
    private final List<EquipmentEntity> dataList;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        equipmentCheckFragmentInstance = this;
        initListView();
    }

    @SuppressLint("ValidFragment")
    public EquipmentCheckFragment(Integer curEleId,String curEleNo, CompanyResult curCompany){
        popupWindow = new MyPopupWindow();
        this.curEleId=curEleId;
        this.curEleNo=curEleNo;
        this.curCompany=curCompany;
        dataList = EquipmentDao.getInstance().queryByLevel(1);
        Log.i(TAG, "changeEleAccountNextStep: 电号-"+ curEleId);
        Log.i(TAG, "changeEleAccountNextStep: 公司号-"+ curCompany.getCompanyId());
        popupWindow.setPopupWindowData(curCompany);
    }
    private void initListView() {
        curHolder = new EquipmentDetailHolder(layoutInfo);
        holder = new DistributionHolder(curHolder.layout_lv3_ele_configuration);
        initCheck();
        initRootListView();
//        changeEleAccount();
        initLv2ListView();
        //电房配置二级
        initLv2EleListView();
        //二级节点设备，属于 三级
        initLV3LeafListView();
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
                curHolder.equipment_eleLL.setBackgroundColor(select);
                curHolder.equipment_eleRoom.setTextColor(textselect);
                    if(iselecheck){
                        showHoulder(3);
                        getEleRoomList();
                    }else{
                        ToastUtil.showShort(getActivity(),"当前无任何设备");
                    }
                break;
            //新建电房
            case R.id.lv2_ele_img:
                if(!holder.isEditor){
                    showHoulder(3);
                    createEleRoomDialog = new CreateEleRoomDialog(this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            createEleRoomDialog.validator.validate();
                        }
                    });
                    createEleRoomDialog.show();
                }else{
                    dialog();
                }
                break;
            //电房删除
            case R.id.btn_ele_del:
                deleteEleRoom(eleRoomResult);
                break;
            //电房信息修改
            case R.id.btn_ele_update:
                if(!holder.isEditor){
                    updateEleRoomDialog = new UpdateEleRoomDialog(EquipmentCheckFragment.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateEleRoomDialog.validator.validate();
                        }
                    }, eleRoomResult);
                    updateEleRoomDialog.show();
                }else{
                    dialog();
                }

                break;
            //电房保存
            case R.id.btn_ele_save:
                holder.isEditor=false;
                saveEle();
                break;
            //电房编辑
            case R.id.btn_ele_editor:
                showHoulder(2);
                eleEditor();
                break;
            //编辑   带一个当前二级选中得企业设备id  去EquipmentCheckDetailsActivity在走查询
            case R.id.btn_editor:
                Intent intent=new Intent(getActivity(),EquipmentCheckDetailsActivity.class);
                intent.putExtra("bean",beans);
                intent.putExtra("IsForEditor",true);
                intent.putExtra("currentItemId",currentItemId);
                intent.putExtra("curEleId",curEleId);
                startActivity(intent);
                break;
            //删除
            case R.id.btn_del:
                AllCancelDialog dialog1=new AllCancelDialog();
                dialog1.loadDialog("您确定要删除该设备？", new AllCancelDialog.OnClickIsConfirm() {
                    @Override
                    public void OnClickIsConfirmListener() {
                        showHoulder(1);
                        CompanyEquipmentResult beans1 = (CompanyEquipmentResult) beans;
                       //发起请求
                        sendDelInfo(beans1.getCompanyEquipmentId()+"",lv2Adapter1,lv2Adapter2);
                    }
                }, new AllCancelDialog.OnClickIsCancel() {
                    @Override
                    public void OnClickIsCancelListener() {

                    }
                });
                break;
            //新建
            case R.id.btn_new:
                sendCopyAddInfo();
                break;
        }
    }
    //新建相同设备
    private void sendCopyAddInfo() {
        CopyCompanyEquipmentParams params = new CopyCompanyEquipmentParams();
        //判断是否复制过。是得话就不在添加复制了
        if(eleRoomResultList.getTwoCompanyEquipment().getName().contains("复制")){
            params.setCompanyEquipmentName(eleRoomResultList.getTwoCompanyEquipment().getName());
        }else{
            params.setCompanyEquipmentName(eleRoomResultList.getTwoCompanyEquipment().getName()+"复制");
        }
        params.setCompanyEquipmentId(eleRoomResultList.getTwoCompanyEquipment().getCompanyEquipmentId());
        HttpClientSend.getInstance().send(params, new BaseAlertCallback("新增设备成功","新增设备失败") {
            @Override
            public void onSuccessed(String result) {
                showMuiltListView(curPidResult, lv2Adapter1, lv2Adapter2, true);
                // 更新电房管理中的未分配设备
//                EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleAccountComEqu();
                //重置新增界面
//                showAddCEquipLayout(EquipmentDao.getInstance().queryById(curHolder.id), adapter, adapter2);
            }
        });
    }
    //删除企业设备
    private void sendDelInfo(final String cEqupIds, final DetailsLv2ListViewAdapter adapter, final EquipmentAddressListViewAdapter adapter2) {
        DeleteComEquParams params = new DeleteComEquParams();
        params.setComEquIds(cEqupIds);
        HttpClientSend.getInstance().send(params, new BaseAlertCallback("删除设备成功","删除设备失败") {
            @Override
            public void onSuccessed(String result) {
                try {
                    showMuiltListView(curPidResult, adapter, adapter2, true);
//                    EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleAccountComEqu();
//                    EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleRoomComEqu();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }

//    有了公司电表Id后查询本体设备类型  二级数据
    @Override
    public void changeEleAccountNextSteps(Integer curEleId, String curEleNo, CompanyResult curCompany) {
        this.curEleId=curEleId;
        this.curEleNo=curEleNo;
        this.curCompany=curCompany;
        popupWindow.setPopupWindowData(curCompany);
        rootAdapter.notifyDataSetChanged();
    }

    //二级数据库数据展示
    protected void initRootListView() {
        rootAdapter = new EquipmentRootListViewAdapter();
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
                    if(!holder.isEditor){
                        rootAdapter.setChecked(selBean);
                        rootAdapter.notifyDataSetChanged();
                        curHolder.equipment_eleLL.setBackgroundColor(unselect);
                        curHolder.equipment_eleRoom.setTextColor(select);
                        rootListViewClick(selBean);

                    }else{
                        dialog();
                    }

                }
            }
        };
        curHolder.listview_lv1_2.setSwipeItemClickListener(rootListener);
        curHolder.listview_lv1_2.setAdapter(rootAdapter);
        rootAdapter.setmDataList(dataList);
        rootAdapter.notifyDataSetChanged();
    }
    //二级节点 或三级节点展示
    protected void initLV3LeafListView() {
        detailAdapter=new EquipmentLayoutAdapter(getActivity(),new ArrayList<EquipmentLayoutBean>());
        curHolder.lv3_leaf_list.setAdapter(detailAdapter);
    }
    //二级点击请求三级数据
    public void rootListViewClick(CommonListViewInterface bean) {
        showHoulder(1);
        //重置选中及文本
        ischeck=false;
        curHolder.lv2_tv.setText("新建");
        lv2Adapter1.remove();
        lv2Adapter2.remove();
        EquipmentEntity selBean = (EquipmentEntity) bean;
        ceBean = new CompanyEquipmentResult();
        ceBean.setEquipmentEntity(selBean);
        ceBean.setEquipmentId(selBean.getEquipmentId());
        ceBean.setPid(-1);
        ceBean.setCompanyEquipmentId(-1);
        //三级网络请求
        showMuiltListView(ceBean, lv2Adapter1, lv2Adapter2, true);
        curPidResult = ceBean;
        Log.i(TAG, "rootListViewClick: 心灵"+selBean.getEquipmentId());
        //设备类型
        list = EquipmentDao.getInstance().queryByPid(selBean.getEquipmentId());

    }
    //三级请求数据展示
    protected void initLv2ListView() {
        lv2Adapter1 = new DetailsLv2ListViewAdapter();
        curHolder.listview_lv2.setLayoutManager(new LinearLayoutManager(getActivity()));
        curHolder.listview_lv2.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
        lv2Listener = new SwipeItemClickListener() {

            @Override
            public void onItemClick(View itemView, int position) {
                selBean = lv2Adapter1.getItem(position);
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

        lv2Adapter2 = new EquipmentAddressListViewAdapter();
        //三级数据库数据展示
        curHolder.listview_lv2_2.setLayoutManager(new LinearLayoutManager(getActivity()));
        curHolder.listview_lv2_2.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                CommonListViewInterface selBean = lv2Adapter2.getItem(position);
                lv2DownListViewClick(selBean);
            }
        });
        curHolder.listview_lv2.setAdapter(lv2Adapter1);
        curHolder.listview_lv2_2.setAdapter(lv2Adapter2);
    }

    //二级详情展示
    private void setData(){
        curHolder.recy_detail.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
        mAdapter = new EquiRecyclerAdapter(getActivity());
        curHolder.recy_detail.setAdapter(mAdapter);
        curHolder.recy_detail.getItemAnimator().setChangeDuration(300);
        curHolder.recy_detail.getItemAnimator().setMoveDuration(300);
        mAdapter.setOnClickTextViewData(new EquiRecyclerAdapter.OnClickTextViewData() {
            @Override
            public void OnClickTextViewDataListener(int text) {
                //设备数量
                curHolder.lv2_details_num.setText(text+"");
            }
        });
    }
//  点击二级数据请求三  四级数据 请求后台
    protected void lv2UpListViewClick(final CommonListViewInterface bean) {
        beans = bean;
        currentItemId = bean.getItemId();
        dataBeanList.clear();
        mAdapter.notifyDataSetChanged();
        SelectCompanyEquipmentByCompanyParams params=new SelectCompanyEquipmentByCompanyParams();
        params.setCompanyEquipmentId(bean.getItemId());
        Log.i(TAG, "lv2UpListViewClick+bean.getItemId: "+bean.getItemId());
        HttpClientSend.getInstance().send(params,new BaseStringCallback(){



            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if(ret.getErrorCode()!=0){
                    Toast.makeText(getActivity(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                eleRoomResultList = JsonUtils.deserialize(ret.getResult(),EquipmentcheckBean.class);
                showHoulder(6);
                curHolder.text_name.setText(eleRoomResultList.getTwoCompanyEquipment().getName());

               //设备类型赋值
                for (int i = 0; i < list.size(); i++) {
                    Log.i(TAG, "onSuccess:list "+list.get(i).getItemName());
                    EquipmentEntity equipmentEntity = list.get(i);
                    switch (equipmentEntity.getEquipmentType()) {
                        //有柜体
                        case CABINET:
                            cabinet=true;
                            Log.i(TAG, "initLv3ListView---------: "+"有");
                            break;
                        //没有柜体
                        default:
                            cabinet=false;
                            Log.i(TAG, "initLv3ListView---------: "+"没有");
                            break;
                    }
                    if(list.get(i).getItemId()== eleRoomResultList.getTwoCompanyEquipment().getEquipmentId()){
                        //类型名称
                        curHolder.lv2_details_name.setText(list.get(i).getItemName());
                    }
                }
                //是否有柜体
                if(!cabinet){
                    curHolder.lv2_details_iscabinet.setText("否");
                }else{
                    curHolder.lv2_details_iscabinet.setText("是");
                }

                List<EquipmentcheckBean.ThreeCompanyEquipmentListBean> threeCompanyEquipmentList = eleRoomResultList.getThreeCompanyEquipmentList();
                //设备数量
                curHolder.lv2_details_num.setText(threeCompanyEquipmentList.size()+"");
                if(threeCompanyEquipmentList.size()!=0){
                    curHolder.lv3_leaf_list.setVisibility(View.GONE);
                    curHolder.recy_detail.setVisibility(View.VISIBLE);

                    for (int i = 0; i < threeCompanyEquipmentList.size(); i++) {
                        //三级设备信息
                        final List<EquipmentLayoutBean> beanList = new ArrayList<>();
                        //三级企业设备信息
                        final List<EquipmentLayoutBean> beanLists = new ArrayList<>();
                        //三级企业设备和设备对比赋值存放信息
                        final List<EquipmentLayoutBean> beanListall = new ArrayList<>();
                        dataBean = new DataBean();
                        Log.i(TAG, "onSuccess: 三级企业设备iD"+threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getCompanyEquipmentId());
                        dataBean.setCompanyEquipmentId(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getCompanyEquipmentId());
                        dataBean.setRecord(1);
                        dataBean.setPid(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getPid());
                        dataBean.setParentLeftTxt(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getName());
                        //用于判断当前条目是否是节点
                        dataBean.setEquipmentId(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getEquipmentId());
                        EquipmentEntity equipmentEntity = EquipmentDao.getInstance().queryById(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getEquipmentId());
                        //是三级节点
                        if(equipmentEntity.getIsLeafNode()){
                            //查找设备零件
                            List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getEquipmentId());

                            for (int j = 0; j < itemList.size(); j++) {
//
                                EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemList.get(i).getEquipmentItemId());
                                //用于排序
                                dataBean.setItemid(itemList.get(i).getEquipmentId());
                                itemBean.setInputType(itemList.get(j).getInputType());
                                if (itemList.get(j).getUnitName() == null) {
                                    itemBean.setTextName(itemList.get(j).getItemName());
                                } else {
                                    itemBean.setTextName(itemList.get(j).getItemName() + "(" + itemList.get(j).getUnitName() + ")");
                                }
                                itemBean.setValue(setNullEdit(itemList.get(j).getDefaultValue()));
                                beanLists.add(itemBean);
                            }
                            //三級节点
                            List<EquipmentcheckBean.ThreeCompanyEquipmentListBean.ThreeCompanyEquipmentItemBean> threeCompanyEquipmentItem = threeCompanyEquipmentList.get(i).getThreeCompanyEquipmentItem();
                            //可能缺少数据，但是怎么补呢
                            Map<Integer, String> itemMap = new HashMap<>();
                            Map<Integer, Integer> itemMapinfoid = new HashMap<>();
                            for (int j = 0; j < threeCompanyEquipmentItem.size(); j++) {
                                EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, threeCompanyEquipmentItem.get(j).getEquipmentItem().getEquipmentItemId());
                                itemBean.setValue(threeCompanyEquipmentItem.get(j).getEquipmentInfo());
                                itemMap.put(threeCompanyEquipmentItem.get(j).getEquipmentItem().getEquipmentItemId(),threeCompanyEquipmentItem.get(j).getEquipmentInfo());
                                if(threeCompanyEquipmentItem.get(j).getEquipmentInfoId()==null){
                                    itemMapinfoid.put(threeCompanyEquipmentItem.get(j).getEquipmentItem().getEquipmentItemId(),0);
                                }else{
                                    itemMapinfoid.put(threeCompanyEquipmentItem.get(j).getEquipmentItem().getEquipmentItemId(),threeCompanyEquipmentItem.get(j).getEquipmentInfoId());
                                }
                                //企业设备属性iD
                                Log.i(TAG, "onSuccess: 三级企业设备属性iD"+threeCompanyEquipmentItem.get(j).getEquipmentInfoId());
                                itemBean.setEquipmentInfoId(threeCompanyEquipmentItem.get(j).getEquipmentInfoId());
                                Log.i(TAG, "onSuccess: setValue"+itemBean.getValue());
                                beanList.add(itemBean);
                            }

                            EquipmentLayoutBean itemBeans = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, 0);
                            for (int j = 0; j <beanLists.size() ; j++) {
                                EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemList.get(j).getEquipmentItemId());
                                EquipmentLayoutBean bean1 = beanLists.get(j);
                                String info = itemMap.get(itemList.get(j).getEquipmentItemId());
                                Integer infoid = itemMapinfoid.get(itemList.get(j).getEquipmentItemId());
                                itemBean.setValue(info==null?bean1.getValue():info);
                                //企业设备属性iD
                                itemBean.setEquipmentInfoId(infoid);
                                itemBean.setTextName(bean1.getTextName());
                                itemBeans.getChildrenList().add(itemBean);
                            }
                            beanListall.add(itemBeans);
                            dataBean.setBean(beanListall);
                            dataBeanList.add(dataBean);
                        }else{//四级设备
                            //四级设备信息
                            final List<EquipmentLayoutBean> beanListfour = new ArrayList<>();
                            //四级企业设备信息
                            final List<EquipmentLayoutBean> beanListfours = new ArrayList<>();
                            //四级企业设备和设备对比赋值存放信息
                            final List<EquipmentLayoutBean> beanListfourall = new ArrayList<>();
                            //查询4级设备
                            List<EquipmentEntity> ccEntityList = getInstance().queryByPidAndCequipId(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getEquipmentId());
                            for (int k = 0; k <ccEntityList.size() ; k++) {
                                EquipmentLayoutBean leafBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, ccEntityList.get(k).getEquipmentId());
                                //用于排序
                                dataBean.setItemid(ccEntityList.get(k).getEquipmentId());
                                Log.i(TAG, "onSuccess: 四级名字"+ccEntityList.get(k).getEquipmentName());
                                //遍历添加二级数据
                                leafBean.setTextName(ccEntityList.get(k).getEquipmentName());
                                List<EquipmentItemEntity> itemLists = ccEntityList.get(k).getItemList();
                                for (int l = 0; l <itemLists.size() ; l++) {
                                    EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemLists.get(l).getEquipmentItemId());
                                    //遍历添加二级详情数据
                                    itemBean.setInputType(itemLists.get(l).getInputType());
                                    if (itemLists.get(l).getUnitName() == null) {
                                        itemBean.setTextName(itemLists.get(l).getItemName());
                                    } else {
                                        itemBean.setTextName(itemLists.get(l).getItemName() + "(" + itemLists.get(l).getUnitName() + ")");
                                    }
//                                        Log.i(TAG, "onSuccess: 四级默认名字"+itemLists.get(l).getItemName());
//                                        Log.i(TAG, "onSuccess: 四级默认属性"+itemLists.get(l).getDefaultValue());
                                    itemBean.setValue(setNullEdit(itemLists.get(l).getDefaultValue()));
                                    leafBean.getChildrenList().add(itemBean);
                                }
                                beanListfour.add(leafBean);
                            }
                            Log.i(TAG, "onSuccess: 本地查询出来得长度"+beanListfour.size());
                            List<EquipmentcheckBean.ThreeCompanyEquipmentListBean.FourCompanyEquipmentListBean> fourCompanyEquipmentList = threeCompanyEquipmentList.get(i).getFourCompanyEquipmentList();
                            //四级------------------------
                            //对应item 的Map  写得真烂
                            Map<Integer, String> itemMap = new HashMap<>();
                            Map<Integer, Integer> itemMapcomId = new HashMap<>();
                            Map<Integer, Integer> itemMapinfoid = new HashMap<>();
                            for (int j = 0; j <fourCompanyEquipmentList.size(); j++) {
                                EquipmentLayoutBean leafBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, fourCompanyEquipmentList.get(j).getFourCompanyEquipment().getEquipmentId());
                                List<EquipmentcheckBean.ThreeCompanyEquipmentListBean.FourCompanyEquipmentListBean.FourCompanyEquipmentItemBean> fourCompanyEquipmentItem = fourCompanyEquipmentList.get(j).getFourCompanyEquipmentItem();
                                if(fourCompanyEquipmentList.get(j).getFourCompanyEquipment().getCompanyEquipmentId()==0){
                                    itemMapcomId.put(fourCompanyEquipmentList.get(j).getFourCompanyEquipment().getEquipmentId(),0);
                                }else{
                                    itemMapcomId.put(fourCompanyEquipmentList.get(j).getFourCompanyEquipment().getEquipmentId(),fourCompanyEquipmentList.get(j).getFourCompanyEquipment().getCompanyEquipmentId());

                                }
//                                    leafBean.setCompanyEquipmentId(fourCompanyEquipmentList.get(j).getFourCompanyEquipment().getCompanyEquipmentId());
                                Log.i(TAG, "onSuccess: 四级rrrr企业设备iD"+leafBean.getCompanyEquipmentId());
                                for (int k = 0; k < fourCompanyEquipmentItem.size(); k++) {
                                    EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, fourCompanyEquipmentItem.get(k).getEquipmentItem().getEquipmentItemId());
                                    //企业设备属性iD
                                    itemBean.setEquipmentInfoId(fourCompanyEquipmentItem.get(k).getEquipmentInfoId());
                                    Log.i(TAG, "onSuccess: 四级企业设备属性iD"+fourCompanyEquipmentItem.get(k).getEquipmentInfoId());
                                    itemBean.setValue(setNullEdit(fourCompanyEquipmentItem.get(k).getEquipmentInfo()));
                                    itemMap.put(fourCompanyEquipmentItem.get(k).getEquipmentItem().getEquipmentItemId(), fourCompanyEquipmentItem.get(k).getEquipmentInfo());
                                    if(fourCompanyEquipmentItem.get(k).getEquipmentInfoId()==null){
                                        itemMapinfoid.put(fourCompanyEquipmentItem.get(k).getEquipmentItem().getEquipmentItemId(), 0);
                                    }else{
                                        itemMapinfoid.put(fourCompanyEquipmentItem.get(k).getEquipmentItem().getEquipmentItemId(), fourCompanyEquipmentItem.get(k).getEquipmentInfoId());
                                    }
                                    leafBean.getChildrenList().add(itemBean);
                                }
                                beanListfours.add(leafBean);
                            }
                            for (int k = 0; k < beanListfour.size(); k++) {
                                EquipmentLayoutBean leafBeans = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, beanListfour.get(k).getItemId());
                                leafBeans.setTextName(beanListfour.get(k).getTextName());
                                Integer companyId = itemMapcomId.get(beanListfour.get(k).getItemId());
                                leafBeans.setCompanyEquipmentId(companyId);
                                Log.i(TAG, "onSuccess: 四级rrqqqqqqqqqqqqrr企业设备iD"+companyId);
                                Log.i(TAG, "onSuccess:四级子长度 "+beanListfour.get(k).getChildrenList().size());
                                for (int l = 0; l < beanListfour.get(k).getChildrenList().size(); l++) {//5
                                    String bean1 = itemMap.get(beanListfour.get(k).getChildrenList().get(l).getItemId());
                                    Integer infoid = itemMapinfoid.get(beanListfour.get(k).getChildrenList().get(l).getItemId());
                                    EquipmentLayoutBean leafBeanchild = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, beanListfour.get(k).getChildrenList().get(l).getItemId());
                                    Log.i(TAG, "onSuccess:四级本地子长度id "+beanListfour.get(k).getChildrenList().get(l).getItemId());
                                    leafBeanchild.setEquipmentInfoId(infoid);
                                    leafBeanchild.setValue(bean1 == null ? beanListfour.get(k).getChildrenList().get(l).getValue() : bean1);
                                    leafBeanchild.setTextName(beanListfour.get(k).getChildrenList().get(l).getTextName());
                                    leafBeans.getChildrenList().add(leafBeanchild);
                                }
                                beanListfourall.add(leafBeans);
                            }
                            dataBean.setBean(beanListfourall);
                            dataBeanList.add(dataBean);
//                            recorddataBeanList.add(dataBean);
                        }

                    }
                    //排序
                    Collections.sort(dataBeanList, new Comparator<DataBean>() {
                        @Override
                        public int compare(DataBean dataBean, DataBean t1) {
                            return  Integer.valueOf(dataBean.getItemid()).compareTo(Integer.valueOf(t1.getItemid()));
                        }
                    });

                    mAdapter.setDataBeanList(dataBeanList);
                    mAdapter.notifyDataSetChanged();
                }
                //----------------二级节点
                List<EquipmentcheckBean.TwoCompanyEquipmentItemBean> twoCompanyEquipmentItem = eleRoomResultList.getTwoCompanyEquipmentItem();
                if(twoCompanyEquipmentItem.size()!=0){
                    curHolder.lv3_leaf_list.setVisibility(View.VISIBLE);
                    curHolder.recy_detail.setVisibility(View.GONE);
                    //二级节点 三级设备信息
                    //查找设备零件
                    List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(eleRoomResultList.getTwoCompanyEquipment().getEquipmentId());
                    Log.i(TAG, "onSuccess  二级  : "+itemList.size());
                    Log.i(TAG, "onSuccess: 二级 name"+itemList.get(0).getItemName());
                    List<EquipmentLayoutBean> beanLists = new ArrayList<>();
                    List<EquipmentLayoutBean> beanListall = new ArrayList<>();
                    for (int i = 0; i < itemList.size(); i++) {
                        EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemList.get(i).getEquipmentItemId());
                        itemBean.setTextName(itemList.get(i).getItemName());
                        itemBean.setValue(itemList.get(i).getDefaultValue());
                        beanLists.add(itemBean);
                    }
                    final List<EquipmentLayoutBean> beanList = new ArrayList<>();
                    Log.i(TAG, "onSuccess444: "+twoCompanyEquipmentItem.size());
                    Map<Integer, String> itemMap = new HashMap<>();
                    Map<Integer, Integer> itemMapinfoid = new HashMap<>();
                    for (int i = 0; i < twoCompanyEquipmentItem.size(); i++) {
                        EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, twoCompanyEquipmentItem.get(i).getEquipmentItem().getEquipmentItemId());
                        itemMap.put(twoCompanyEquipmentItem.get(i).getEquipmentItem().getEquipmentItemId(),twoCompanyEquipmentItem.get(i).getEquipmentInfo());
                        if(twoCompanyEquipmentItem.get(i).getEquipmentInfoId()==null){
                            itemMapinfoid.put(twoCompanyEquipmentItem.get(i).getEquipmentItem().getEquipmentItemId(), 0);
                        }else {
                            itemMapinfoid.put(twoCompanyEquipmentItem.get(i).getEquipmentItem().getEquipmentItemId(), twoCompanyEquipmentItem.get(i).getEquipmentInfoId());
                        }
                        beanList.add(itemBean);
                    }
                    for (int i = 0; i < beanLists.size(); i++) {
                        EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, beanLists.get(i).getItemId());
                        String info = itemMap.get(beanLists.get(i).getItemId());
                        Integer infoid = itemMapinfoid.get(beanLists.get(i).getItemId());
                        itemBean.setTextName(itemList.get(i).getItemName());
                        itemBean.setValue(info==null?itemList.get(i).getDefaultValue():info);
                        itemBean.setEquipmentInfoId(infoid==0?0:infoid);
                        beanListall.add(itemBean);
                    }
                    //叶子节点适配器
                    detailAdapter.setDataList(beanListall);
                    detailAdapter.notifyDataSetChanged();
                }
            }

        });
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
        intent.putExtra("IsForEditor",false);
        intent.putExtra("currentItemId",0);
        intent.putExtra("curEleId",curEleId);
        startActivity(intent);
    }
    //请求三级数据及三级本地同设备类型
    private void showMuiltListView(final CompanyEquipmentResult selBean, final DetailsLv2ListViewAdapter adapter, final EquipmentAddressListViewAdapter adapter2, final boolean flag) {
        curHolder.listview_lv2.setVisibility(View.VISIBLE);
        final List<CompanyEquipmentResult> mDataList = new ArrayList<>();
        QueryComEquParams params = new QueryComEquParams();
        params.setEquipmentId(selBean.getEquipmentId());
        params.setEleAccountsId(curEleId);
        params.setPid(selBean.getCompanyEquipmentId());
        Log.i(TAG, "showMuiltListView111111: "+selBean.getEquipmentId());
        Log.i(TAG, "showMuiltListView22222: "+curEleId);
        Log.i(TAG, "showMuiltListView333333: "+selBean.getCompanyEquipmentId());
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
                        curHolder.layout_lv2_hite.setVisibility(View.VISIBLE);
                    }else{
                        curHolder.layout_lv2_all.setVisibility(View.VISIBLE);
                        curHolder.layout_lv2_hite.setVisibility(View.GONE);
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
        //图片旋转效果
        curHolder.lv2_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ischeck){
                    int white = Color.parseColor("#ffffff");
                    curHolder.layout_lv2_all.setVisibility(View.VISIBLE);
                    curHolder.layout_lv2.setBackgroundColor(white);
                    ObjectAnimator ra = ObjectAnimator.ofFloat(curHolder.lv2_img,"rotation", 0f, 90f);
                    ra.setDuration(500);
                    ra.start();
                    curHolder.lv2_tv.setText("取消");
                    ischeck=true;
                    curHolder.listview_lv2.setVisibility(View.GONE);
                    curHolder.listview_lv2_2.setVisibility(View.VISIBLE);
                    List<EquipmentEntity> list = EquipmentDao.getInstance().queryByPid(selBean.getEquipmentId());
                    adapter2.setmDataList(list);
                    adapter2.notifyDataSetChanged();
                }else{
                    int gray = Color.parseColor("#E9ECF2");
                    curHolder.layout_lv2.setBackgroundColor(gray);
                    curHolder.listview_lv2.setVisibility(View.VISIBLE);
                    curHolder.listview_lv2_2.setVisibility(View.GONE);
                    ObjectAnimator ra = ObjectAnimator.ofFloat(curHolder.lv2_img,"rotation", 0f, -90f);
                    ra.setDuration(500);
                    ra.start();
                    ischeck=false;
                    curHolder.lv2_tv.setText("新建");
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
                    if(!holder.isEditor){
                        //设置选中
                        lv2eleAdapter1.setChecked(selBean);
                        lv2eleAdapter1.notifyDataSetChanged();
                        lv2UpEleListViewClick(selBean);
                    }else{
                        dialog();
                    }

                }
            }
        };
        curHolder.listview_ele_lv2.setSwipeItemClickListener(lv2eleListener);
        curHolder.listview_ele_lv2.setAdapter(lv2eleAdapter1);
    }
    private void getEleRoomList() {
        SelectEleRoomParams params = new SelectEleRoomParams();
        params.setAccountId(curEleId);
        Log.i(TAG, "getEleRoomList: "+"电号"+curEleId);
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
        if(iselecheck){
            showHoulder(3);
        }
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
        Log.i(TAG, "getEleRoomList: "+"sssss"+lv2eleAdapter1.getChecked().getItemId());
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
                showHoulder(2);
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
                curHolder.layout_lv2.setVisibility(View.VISIBLE);
                curHolder.layout_ele_info.setVisibility(View.GONE);
                curHolder.layout_lv3_all.setVisibility(View.GONE);
                curHolder.layout_lv3_hite.setVisibility(View.VISIBLE);
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
                curHolder.layout_lv2_ele_all.setVisibility(View.VISIBLE);
                curHolder.layout_lv2_ele_hite.setVisibility(View.GONE);
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
            //后台是否有数据
            case 5:
                curHolder.layout_lv3_all.setVisibility(View.GONE);
                curHolder.layout_lv3_hite.setVisibility(View.VISIBLE);
                break;
            //后台是否有数据
            case 6:
                curHolder.layout_lv3_all.setVisibility(View.VISIBLE);
                curHolder.layout_lv3_hite.setVisibility(View.GONE);
                break;
        }
    }
    public void dialog(){
        AllEditorDialog dialog1=new AllEditorDialog();
        dialog1.loadDialog("尚未保存当前页面，确认放弃？", new AllEditorDialog.OnClickIsConfirm() {
            @Override
            public void OnClickIsConfirmListener() {
                return;
            }
        }, new AllEditorDialog.OnClickIsCancel() {
            @Override
            public void OnClickIsCancelListener() {
                holder.isEditor=false;
                EventBusBean eventBusBean=new EventBusBean();
                eventBusBean.setKind(EventKind.EVENT_COMPANY_ELEISEDITOR);
                eventBusBean.setSuccess(holder.isEditor);
                EventBus.getDefault().post(eventBusBean);

            }
        });
    }
    public void showlv2refresh(){
        showMuiltListView(ceBean,lv2Adapter1,lv2Adapter2,true);
    }
    public void showlv3refresh(){
        lv2UpListViewClick(selBean);
    }
}
