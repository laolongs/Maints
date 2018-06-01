package cn.tties.maint.activity;


import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonAppendListViewAdapter;
import cn.tties.maint.adapter.CommonSwipListViewAdapter;
import cn.tties.maint.adapter.EquiRecyclerAdapter;
import cn.tties.maint.adapter.EquipmentLayoutAdapter;
import cn.tties.maint.adapter.MyEquipmentCheckEleAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.EquipmentcheckBean;
import cn.tties.maint.bean.EventBusBean;
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
import cn.tties.maint.httpclient.params.CreateEleRoomParams;
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
import cn.tties.maint.httpclient.result.EleRoomResult;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.adapter.RecyclerAdapter;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.view.ConfirmDialog;
import cn.tties.maint.view.CreateEleRoomDialog;
import cn.tties.maint.view.MyPopupWindow;
import cn.tties.maint.view.UpdateEleRoomDialog;
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
    private List<DataBean> dataBeanList= new ArrayList<>();
    private DataBean dataBean;
    public EquiRecyclerAdapter mAdapter;
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
    //加载节点布局
    private EquipmentLayoutAdapter detailAdapter;
    private MyPopupWindow popupWindow;
    private CompanyEquipmentResult curPidResult;
    private boolean cabinet=false;
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
    private List<EquipmentEntity> list;
    private CompanyEquipmentResult ceBean;
    private Integer currentItemId;
    private CommonListViewInterface isforeditorbeans;

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
            //编辑   带一个当前二级选中得企业设备id  去EquipmentCheckDetailsActivity在走查询
            case R.id.btn_editor:
                Intent intent=new Intent(getActivity(),EquipmentCheckDetailsActivity.class);
                intent.putExtra("bean",isforeditorbeans);
                intent.putExtra("IsForEditor",true);
                intent.putExtra("curEleId",curEleId);
                startActivity(intent);
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
    //二级节点 或三级节点展示
    protected void initLV3LeafListView() {
        detailAdapter=new EquipmentLayoutAdapter(getActivity(),new ArrayList<EquipmentLayoutBean>());
        curHolder.lv3_leaf_list.setAdapter(detailAdapter);
    }
    //二级点击请求三级数据
    public void rootListViewClick(CommonListViewInterface bean) {
        showHoulder(1);
        showHoulder(5);
        //重置选中及文本
        ischeck=false;
        curHolder.lv2_tv.setText("新建");
        lv2Adapter1.remove();
        lv2Adapter2.remove();
//        lv3Adapter1.remove();
        EquipmentEntity selBean = (EquipmentEntity) bean;
        ceBean = new CompanyEquipmentResult();
        ceBean.setEquipmentEntity(selBean);
        ceBean.setEquipmentId(selBean.getEquipmentId());
        ceBean.setPid(-1);
        ceBean.setCompanyEquipmentId(-1);
        //三级网络请求
        showMuiltListView(ceBean, lv2Adapter1, lv2Adapter2, true);
        curPidResult = ceBean;
        curHolder.lv2_img.setVisibility(View.VISIBLE);
        curHolder.lv2_tv.setVisibility(View.VISIBLE);
        //设备类型
        list = EquipmentDao.getInstance().queryByPid(selBean.getEquipmentId());

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
//        currentItemId = bean.getItemId();
        isforeditorbeans = bean;
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
                EquipmentcheckBean eleRoomResultList = JsonUtils.deserialize(ret.getResult(),EquipmentcheckBean.class);
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
                            Log.i(TAG, "initLv3ListView---------: "+"没有");
                            break;
                    }
                    if(list.get(i).getItemId()==eleRoomResultList.getTwoCompanyEquipment().getEquipmentId()){
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
                        dataBean.setParentLeftTxt(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getName());
                        EquipmentEntity equipmentEntity = EquipmentDao.getInstance().queryById(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getEquipmentId());
                        //是三级节点
                        if(equipmentEntity.getIsLeafNode()){
                            //查找设备零件
                            List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getEquipmentId());

                            for (int j = 0; j < itemList.size(); j++) {
                                EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemList.get(i).getEquipmentItemId());
                                //用于排序
                                dataBean.setItemid(itemList.get(i).getEquipmentItemId());
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
                            for (int j = 0; j < threeCompanyEquipmentItem.size(); j++) {
                                EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, threeCompanyEquipmentItem.get(j).getEquipmentItem().getEquipmentItemId());
                                itemBean.setValue(threeCompanyEquipmentItem.get(j).getEquipmentInfo());
                                Log.i(TAG, "onSuccess: setValue"+itemBean.getValue());
                                beanList.add(itemBean);
                            }

                            for (int j = 0; j <beanLists.size() ; j++) {
                                int num=0;
                                EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemList.get(i).getEquipmentItemId());
                                EquipmentLayoutBean bean1 = beanLists.get(j);
                                if(beanList.size()!=0){
                                    for (int k = 0; k <beanList.size() ; k++) {
                                        if(bean1.getItemId()==beanList.get(k).getItemId()){
                                            num++;
                                            itemBean.setValue(beanList.get(j).getValue());
                                        }
                                    }
                                }
                                if(num==0){
                                    itemBean.setValue(bean1.getValue());
                                }
                                itemBean.setTextName(bean1.getTextName());
                                beanListall.add(itemBean);
                                dataBean.setBean(beanListall);
                            }
                            dataBeanList.add(dataBean);
                        }else{//四级设备
                            //四级设备信息
                            final List<EquipmentLayoutBean> beanListfour = new ArrayList<>();
                            //四级企业设备信息
                            final List<EquipmentLayoutBean> beanListfours = new ArrayList<>();
                            //四级企业设备和设备对比赋值存放信息
                            final List<EquipmentLayoutBean> beanListfourall = new ArrayList<>();
                            int num=0;
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
                                    Log.i(TAG, "onSuccess: 四级默认名字"+itemLists.get(l).getItemName());
                                    Log.i(TAG, "onSuccess: 四级默认属性"+itemLists.get(l).getDefaultValue());
                                    itemBean.setValue(setNullEdit(itemLists.get(l).getDefaultValue()));
                                    leafBean.getChildrenList().add(itemBean);
                                }
                                beanListfour.add(leafBean);
                            }
                            Log.i(TAG, "onSuccess: 本地查询出来得长度"+beanListfour.size());
                            List<EquipmentcheckBean.ThreeCompanyEquipmentListBean.FourCompanyEquipmentListBean> fourCompanyEquipmentList = threeCompanyEquipmentList.get(i).getFourCompanyEquipmentList();
                            EquipmentLayoutBean leafBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getEquipmentId());
                            //四级------------------------
                            for (int j = 0; j <fourCompanyEquipmentList.size(); j++) {
                                List<EquipmentcheckBean.ThreeCompanyEquipmentListBean.FourCompanyEquipmentListBean.FourCompanyEquipmentItemBean> fourCompanyEquipmentItem = fourCompanyEquipmentList.get(j).getFourCompanyEquipmentItem();
                                for (int k = 0; k < fourCompanyEquipmentItem.size(); k++) {
                                    EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, fourCompanyEquipmentItem.get(k).getEquipmentItem().getEquipmentItemId());
                                    itemBean.setValue(setNullEdit(fourCompanyEquipmentItem.get(k).getEquipmentInfo()));
                                    leafBean.getChildrenList().add(itemBean);
                                }
                                beanListfours.add(leafBean);
                            }
                            for (int k = 0; k < beanListfour.size(); k++) {
                                    EquipmentLayoutBean leafBeans = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getEquipmentId());
                                    leafBeans.setTextName(beanListfour.get(k).getTextName());
                                    Log.i(TAG, "onSuccess:四级子长度 "+beanListfour.get(k).getChildrenList().size());
                                    for (int l = 0; l < beanListfour.get(k).getChildrenList().size(); l++) {//5
                                        EquipmentLayoutBean leafBeanchild = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, beanListfour.get(k).getChildrenList().get(l).getItemId());
                                        Log.i(TAG, "onSuccess:四级本地子长度id "+beanListfour.get(k).getChildrenList().get(l).getItemId());
                                        if(beanListfours.size()!=0){
                                            for (int m = 0; m <beanListfours.get(l).getChildrenList().size() ; m++) {//5
                                                Log.i(TAG, "onSuccess:四级查询  子长度id "+beanListfours.get(l).getChildrenList().get(m).getItemId());
                                                if(beanListfour.get(k).getChildrenList().get(l).getItemId()==beanListfours.get(l).getChildrenList().get(m).getItemId()){
                                                    num++;
                                                    leafBeanchild.setValue(beanListfours.get(l).getChildrenList().get(m).getValue());
                                                }
                                            }
                                        }
                                        if(num==0){
                                            leafBeanchild.setValue(beanListfour.get(k).getChildrenList().get(l).getValue());
                                        }
                                        leafBeanchild.setTextName(beanListfour.get(k).getChildrenList().get(l).getTextName());
                                        leafBeans.getChildrenList().add(leafBeanchild);
                                    }
                                    beanListfourall.add(leafBeans);
                                }
                            dataBean.setBean(beanListfourall);
                            dataBeanList.add(dataBean);
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
                List<EquipmentcheckBean.TwoCompanyEquipmentItemBean> twoCompanyEquipmentItem = eleRoomResultList.getTwoCompanyEquipmentItem();
                if(twoCompanyEquipmentItem.size()!=0){
                    //二级节点 三级设备信息
                    final List<EquipmentLayoutBean> beanList = new ArrayList<>();
                    curHolder.lv3_leaf_list.setVisibility(View.VISIBLE);
                    curHolder.recy_detail.setVisibility(View.GONE);
                    Log.i(TAG, "onSuccess444: "+twoCompanyEquipmentItem.size());
                    for (int i = 0; i < twoCompanyEquipmentItem.size(); i++) {
                        EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, twoCompanyEquipmentItem.get(i).getEquipmentItem().getEquipmentItemId());
                        itemBean.setTextName(twoCompanyEquipmentItem.get(i).getEquipmentItem().getItemName());
                        itemBean.setValue(twoCompanyEquipmentItem.get(i).getEquipmentInfo());
                        beanList.add(itemBean);
                    }
                    //叶子节点适配器
                    detailAdapter.setDataList(beanList);

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
            //后台是否有数据
            case 5:
                curHolder.layout_lv3_ele_all.setVisibility(View.VISIBLE);
                curHolder.layout_lv3_ele_hite.setVisibility(View.GONE);
                break;
            //后台是否有数据
            case 6:
                curHolder.layout_lv3_ele_all.setVisibility(View.GONE);
                curHolder.layout_lv3_ele_hite.setVisibility(View.VISIBLE);
                break;
        }
    }
}
