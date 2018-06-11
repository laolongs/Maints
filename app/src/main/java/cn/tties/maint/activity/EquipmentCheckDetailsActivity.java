package cn.tties.maint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonSwipListViewAdapter;
import cn.tties.maint.adapter.DetailsSwipListViewAdapter;
import cn.tties.maint.adapter.EquiRecyclerAdapter;
import cn.tties.maint.adapter.EquipmentLayoutAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.CreateBoxBody;
import cn.tties.maint.bean.CreateEquipFiveParam;
import cn.tties.maint.bean.CreateEquipFourParam;
import cn.tties.maint.bean.CreateEquipItemParam;
import cn.tties.maint.bean.CreateEquipParam;
import cn.tties.maint.bean.CreateEquipTwoParam;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.EquipmentcheckBean;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.dao.EquipmentItemDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.EquipmentItemEntity;
import cn.tties.maint.holder.EquipmentDetailsActivityHolder;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.CreateNewComEquParams;
import cn.tties.maint.httpclient.params.SelectCompanyEquipmentByCompanyParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.adapter.RecyclerAdapter;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.view.EquipmentDetailsDialog;
import cn.tties.maint.widget.WrapContentLinearLayoutManager;

import static cn.tties.maint.dao.EquipmentDao.getInstance;

/**
 * 管理档案 新建同类设备
 */
@ContentView(R.layout.activity_equipment_check_details)
public class EquipmentCheckDetailsActivity extends BaseFragmentActivity implements View.OnClickListener {
    private static final String TAG = "EquipmentCheckDetailsAc";
    private CommonListViewInterface eEntity;
    private EquipmentCheckDetailsActivity detailsActivity;
    @ViewInject(R.id.eq_de_layout_info)
    private LinearLayout layout_info;
    EquipmentDetailsActivityHolder curHolder;
    protected DetailsSwipListViewAdapter lv3Adapter2;
    //加载节点布局
    private EquipmentLayoutAdapter detailAdapter;
    private EquipmentEntity entity;
    private CompanyEquipmentResult entitys;
    //二级列表展示
    private List<DataBean> dataBeanList = new ArrayList<>();
    private List<DataBean> recorddataBeanList = new ArrayList<>();
    private RecyclerAdapter mAdapter;
    boolean cabinet=false;
    boolean isTowLeaf=false;
    private int curEleId;
    private int currentItemId;
    private boolean IsForEditor;
    private Integer companyEquipmentId=0;
    private Integer boxBodyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsActivity = this;

        curEleId = (Integer) getIntent().getSerializableExtra("curEleId");
        //编辑时得二级企业设备id
        currentItemId = (Integer) getIntent().getSerializableExtra("currentItemId");
        //判断是否来自编辑页面 true
        IsForEditor = (boolean) getIntent().getSerializableExtra("IsForEditor");
        //false 代表本地设备   true 代表企业设备
        if(!IsForEditor){
            eEntity = (CommonListViewInterface) getIntent().getSerializableExtra("bean");
            entity = (EquipmentEntity) eEntity;
            entity.getEquipmentId();
        }else{
            eEntity = (CommonListViewInterface) getIntent().getSerializableExtra("bean");
            entitys = (CompanyEquipmentResult) eEntity;
            entitys.getEquipmentId();
        }

        initFindeViewById();
    }

    private void initFindeViewById() {
        curHolder = new EquipmentDetailsActivityHolder(layout_info);
        if(!IsForEditor){
            curHolder.id=eEntity.getItemId();
            Log.i(TAG, "onCreate  本地: " + entity.getEquipmentId());
        }else{
            curHolder.id=entitys.getEquipmentId();
            companyEquipmentId=entitys.getCompanyEquipmentId();
            Log.i(TAG, "onCreate: 设备0100 " + entitys.getEquipmentId());
            Log.i(TAG, "onCreate: 设备 222" + entitys.getCompanyEquipmentId());
        }
        initView();
        initLv3ListView();
        initisCabinet();
        setData();
        initForEditor();
        curHolder.eq_de_confirm.setOnClickListener(this);
        curHolder.eq_de_cancel.setOnClickListener(this);
        curHolder.eq_de_start.setOnClickListener(this);
        curHolder.eq_de_old.setOnClickListener(this);
    }



    //是否展示柜体可选
    private void initisCabinet() {
        if(!cabinet){
            curHolder.isCabinet=false;
           curHolder.eq_de_radiogroup.setVisibility(View.GONE);
           curHolder.eq_de_CabinetTV.setVisibility(View.GONE);
        }else{
            curHolder.isCabinet=true;
            curHolder.eq_de_radiogroup.setVisibility(View.VISIBLE);
            curHolder.eq_de_CabinetTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //保存
            case R.id.eq_de_confirm:
                if(dataBeanList.size()==0){
                    ToastUtil.showShort(this,"尚未添加子设备，请添加");
                    return;
                }
                addCompanyEquip();
                break;
            //取消
            case R.id.eq_de_cancel:
                finish();
                break;
            //恢复初始
            case R.id.eq_de_start:
                    if(!IsForEditor){
                        if(dataBeanList!=null&&dataBeanList.size()>0){
                            dataBeanList.clear();
                            recorddataBeanList.clear();
                            mAdapter.notifyDataSetChanged();
                        }else{
                            ToastUtil.showShort(this,"尚未添加子设备，请添加");
                        }
                    }else{
                        initForEditor();
                    }
                break;
            //导入先前配置
            case R.id.eq_de_old:
                if(isTowLeaf){
                    ToastUtil.showShort(this,"当前为子设备，无导入");
                    return;
                }

                Intent intent=new Intent(this,ImportConfigurationActivity.class);
                //肯定要带点参数过去当限制条件得
                if(!IsForEditor){
                    intent.putExtra("equipmentId",entity.getEquipmentId());
                }else{
                    intent.putExtra("equipmentId",entitys.getEquipmentId());
                }
                startActivity(intent);
                break;
        }
    }
    //编辑企业设备
    private void initForEditor() {
        if(IsForEditor){
            dataBeanList.clear();
            recorddataBeanList.clear();
            mAdapter.notifyDataSetChanged();
            SelectCompanyEquipmentByCompanyParams params=new SelectCompanyEquipmentByCompanyParams();
            params.setCompanyEquipmentId(currentItemId);
            Log.i(TAG, "lv2UpListViewClick+bean.getItemId: "+currentItemId);
            HttpClientSend.getInstance().send(params,new BaseStringCallback(){
                @Override
                public void onSuccess(String result) {
                    BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                    if(ret.getErrorCode()!=0){
                        Toast.makeText(EquipmentCheckDetailsActivity.this, ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    EquipmentcheckBean eleRoomResultList = JsonUtils.deserialize(ret.getResult(),EquipmentcheckBean.class);
                    //设备名称
                    curHolder.eq_de_name.setText(eleRoomResultList.getTwoCompanyEquipment().getName());
                    //设备重命名
                    curHolder.edit_value.setText(eleRoomResultList.getTwoCompanyEquipment().getName());

                    List<EquipmentcheckBean.ThreeCompanyEquipmentListBean> threeCompanyEquipmentList = eleRoomResultList.getThreeCompanyEquipmentList();
                    //设备数量
                    curHolder.eq_de_num.setText(threeCompanyEquipmentList.size()+"");
                    if(threeCompanyEquipmentList.size()!=0){

                        for (int i = 0; i < threeCompanyEquipmentList.size(); i++) {
                            //三级设备信息
                            final List<EquipmentLayoutBean> beanList = new ArrayList<>();
                            //三级企业设备信息
                            final List<EquipmentLayoutBean> beanLists = new ArrayList<>();
                            //三级企业设备和设备对比赋值存放信息
                            final List<EquipmentLayoutBean> beanListall = new ArrayList<>();
                           DataBean dataBean = new DataBean();
                            Log.i(TAG, "onSuccess: 三级企业设备iD"+threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getCompanyEquipmentId());
                            dataBean.setCompanyEquipmentId(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getCompanyEquipmentId());
                            dataBean.setRecord(1);
                            dataBean.setParentLeftTxt(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getName());
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
                                recorddataBeanList.add(dataBean);
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
                                //对应item 的Map
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
                                recorddataBeanList.add(dataBean);
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
                        mAdapter.setRecordDataBeanList(recorddataBeanList);
                        mAdapter.notifyDataSetChanged();
                    }
                    List<EquipmentcheckBean.TwoCompanyEquipmentItemBean> twoCompanyEquipmentItem = eleRoomResultList.getTwoCompanyEquipmentItem();
                    if(twoCompanyEquipmentItem.size()!=0){
                        //如果是二级节点得话，只展示list
                        IsVisibility(false);
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
    }
    private void addCompanyEquip() {
        //最大级的list集合包含二级（三级（四级））
        List<CreateEquipParam> twoList = new ArrayList<>();
        //企业设备下级设备
        List<CreateEquipItemParam> threeList = new ArrayList<>();
        //二级子节点
        List<CreateEquipTwoParam> arrTwoLists = new ArrayList<>();
        //是否又柜子
        //二级企业设备
        CreateEquipParam cEquipParam = new CreateEquipParam();
        //设备id 如果是二级子节点得话，curHolder.id就是刚跳转过来bean得ID
        cEquipParam.setEquipmentId(curHolder.id);
        //公司表号
        cEquipParam.setEleAccountId(curEleId);
        //设备名称
        cEquipParam.setComEquName(curHolder.edit_value.getText().toString());
        //顶级id 都是-1;
        cEquipParam.setPid(-1);
        //设备id
        cEquipParam.setCompanyEquipmentId(companyEquipmentId);
        Log.i(TAG, "addCompanyEquip:companyEquipmentId "+companyEquipmentId);

        //是否有柜体
        if (curHolder.isCabinet) {
            CreateBoxBody boxBody=new CreateBoxBody();
//          传递过来得二级设备id
//            List<CreateEquipFiveParam> arrayLists = new ArrayList<>();
//            CreateEquipFiveParam createEquipFiveParam=new CreateEquipFiveParam();
//            createEquipFiveParam.setEquItemId(eEntity.getItemId());
//            arrayLists.add(createEquipFiveParam);
//            boxBody.setBoxBodyEquItemArr(arrayLists);
            //柜体Id
            boxBody.setBoxBodyId(boxBodyId);
            cEquipParam.setBoxBody(boxBody);
        }
        //是否是二级节点
        if(isTowLeaf){
            List<EquipmentLayoutBean> dataList = detailAdapter.getDataList();
            for (int i = 0; i < dataList.size(); i++) {
                EquipmentLayoutBean bean = dataList.get(i);
                //二级
                CreateEquipTwoParam createEquipTwoParam = new CreateEquipTwoParam();
                //二级id
                createEquipTwoParam.setEquItemId(bean.getItemId());
                //二级输入框值
                createEquipTwoParam.setEquItemContent(bean.getValue());
                //只是节点， 所以只有一种类型
                arrTwoLists.add(createEquipTwoParam);
            }
            cEquipParam.setComItemTwo(arrTwoLists);
        }else{
//            //添加三级得集合到二级
//            cEquipParam.setEquNameArrThree(threeList);
            for (int i = 0; i < recorddataBeanList.size(); i++) {
                DataBean dataBean = recorddataBeanList.get(i);
                //三级
                CreateEquipItemParam threeParam = new CreateEquipItemParam();
                //三级id
                threeParam.setEquId(dataBean.getItemid());
                //三级输入框值
                threeParam.setEquName(dataBean.getParentLeftTxt());
                //是否删除过
                threeParam.setFlag(dataBean.isFlag());
                //企业设备id
                threeParam.setCompanyEquipmentId(dataBean.getCompanyEquipmentId());
                Log.i(TAG, "addCompanyEquipa: "+dataBean.getCompanyEquipmentId());
                List<CreateEquipFourParam> arrFourList = new ArrayList<>();
                for (int j = 0; j <recorddataBeanList.get(i).getBean().size() ; j++) {
                    EquipmentLayoutBean bean = recorddataBeanList.get(i).getBean().get(j);
                    switch (bean.getType()){
                        //三级节点
                        case EquipmentLayoutAdapter.EDITTEXT:
                            List<CreateEquipFiveParam> arrTwoList = new ArrayList<>();

                            Log.i(TAG, "addCompanyEquip:三级 "+bean.getChildrenList().size());
                            for (EquipmentLayoutBean itemBean : bean.getChildrenList()) {
                                //CreateEquipFiveParam  指得是节点
                                CreateEquipFiveParam createEquipFiveParam=new CreateEquipFiveParam();

                                //创建过得企业设备下得属性
                                createEquipFiveParam.setEquipmentInfoId(itemBean.getEquipmentInfoId());
                                createEquipFiveParam.setEquItemId(itemBean.getItemId());
                                createEquipFiveParam.setEquItemContent(itemBean.getValue());
                                arrTwoList.add(createEquipFiveParam);
                                threeParam.setComItemThree(arrTwoList);
                            }
                            break;
                        //三级含四级
                        case EquipmentLayoutAdapter.LEAF:

                            //四级
                            CreateEquipFourParam fourParam = new CreateEquipFourParam();
                            fourParam.setCompanyEquipmentId(bean.getCompanyEquipmentId());
                            fourParam.setEquIdFour(bean.getItemId());
                            List<CreateEquipFiveParam> fourItemList = new ArrayList<>();
                            Log.i(TAG, "addCompanyEquip:四级 "+bean.getChildrenList().size());
                            for (EquipmentLayoutBean itemBean : bean.getChildrenList()) {
                                //CreateEquipFiveParam  指得是节点
                                CreateEquipFiveParam createEquipFiveParam=new CreateEquipFiveParam();
                                //创建过得企业设备下得属性
                                createEquipFiveParam.setEquipmentInfoId(itemBean.getEquipmentInfoId());
                                createEquipFiveParam.setEquItemId(itemBean.getItemId());
                                createEquipFiveParam.setEquItemContent(itemBean.getValue());
                                fourItemList.add(createEquipFiveParam);
                            }
                            fourParam.setEquItemFourArr(fourItemList);
                            arrFourList.add(fourParam);
                            //添加四级得集合
                            threeParam.setEquArrFour(arrFourList);
                            break;
                    }

                }
                //三级集合
                threeList.add(threeParam);
            }
            //添加三级得集合到二级
            cEquipParam.setEquNameArrThree(threeList);
        }

        twoList.add(cEquipParam);
        sendAddInfo(twoList);
    }

    private void sendAddInfo(List<CreateEquipParam> cEquipParam) {
        //234一次提交接口
        CreateNewComEquParams params = new CreateNewComEquParams();
        params.setParamJson(JsonUtils.getJsonObjectStr(cEquipParam));
        Log.i(TAG, "sendAddInfo: "+JsonUtils.getJsonObjectStr(cEquipParam));
        HttpClientSend.getInstance().send(params, new BaseAlertCallback("新增设备成功","新增设备失败") {
            @Override
            public void onSuccessed(String result) {
                try {
//                    showMuiltListView(curPidResult, adapter, adapter2, false);
                    // 更新电房管理中的未分配设备
//                    EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleAccountComEqu();
                    //重置新增界面
//                    showAddCEquipLayout(EquipmentDao.getInstance().queryById(curHolder.id), adapter, adapter2);
//                    EquipmentCheckFragment.equipmentCheckFragmentInstance.getEleAccountComEqu();
                    EquipmentCheckFragment.equipmentCheckFragmentInstance.showlv2refresh();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: "+ex.toString());
            }
        });
    }

    private void initView() {
        if(!IsForEditor){
            //设备名称
            curHolder.eq_de_name.setText(entity.getEquipmentName());
            //设备重命名
            curHolder.edit_value.setText(entity.getEquipmentName());
        }

    }
    //四级请求数据展示
    protected void initLv3ListView() {
        //加载节点布局
        detailAdapter = new EquipmentLayoutAdapter(this, new ArrayList<EquipmentLayoutBean>());
        curHolder.eq_de_list_feal_list.setAdapter(detailAdapter);
        lv3Adapter2 = new DetailsSwipListViewAdapter();
        //四级本地数据
        GridLayoutManager layoutManage = new GridLayoutManager(this, 4);
        curHolder.eq_de_list_lv4.setLayoutManager(layoutManage);
        curHolder.eq_de_list_lv4.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
//                EquipmentLayoutBean item = lv3Adapter2.getItem(position);
                CommonListViewInterface item = lv3Adapter2.getItem(position);
                EquipmentEntity bean = (EquipmentEntity) item;
                lv3DownListViewClick(bean);
            }
        });
        curHolder.eq_de_list_lv4.setAdapter(lv3Adapter2);
        //页面元素list
        List<EquipmentLayoutBean> beanList = new ArrayList<>();
        if(!IsForEditor){
            //是否是叶子节点
            if (!entity.getIsLeafNode()) {
                IsVisibility(true);
                //查询下级设备
                List<EquipmentEntity> childist = getInstance().queryByPid(entity.getEquipmentId());
                for (EquipmentEntity entity : childist) {
                    switch (entity.getEquipmentType()) {
                        //有柜体
                        case CABINET:
                            //柜体id
                            boxBodyId=entity.getEquipmentId();
                            cabinet=true;
                            break;
                        default:
                            break;
                    }
                }
                lv3Adapter2.setmDataList(childist);
                lv3Adapter2.notifyDataSetChanged();
            } else {
                //如果是二级节点得话，只展示list
                IsVisibility(false);
                //查找设备零件
                List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(entity.getEquipmentId());

                //生成零件页面
                for (EquipmentItemEntity itemEntity : itemList) {
                    EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemEntity.getEquipmentItemId());
                    itemBean.setInputType(itemEntity.getInputType());
                    if (itemEntity.getUnitName() == null) {
                        itemBean.setTextName(itemEntity.getItemName());
                    } else {
                        itemBean.setTextName(itemEntity.getItemName() + "(" + itemEntity.getUnitName() + ")");
                    }
                    itemBean.setValue(setNullEdit(itemEntity.getDefaultValue()));
                    beanList.add(itemBean);
                }
                //叶子节点适配器
                detailAdapter.setDataList(beanList);
                detailAdapter.notifyDataSetChanged();
            }
        }else{//用于查询编辑过来得数据是否包含柜体
            IsVisibility(true);
            //查询下级设备
            List<EquipmentEntity> childist = getInstance().queryByPid(entitys.getEquipmentId());
            for (EquipmentEntity entity : childist) {
                switch (entity.getEquipmentType()) {
                    //有柜体
                    case CABINET:
                        cabinet=true;
                        boxBodyId=entity.getEquipmentId();
                        break;
                    //没有柜体
                    default:
                        break;
                }
            }
            lv3Adapter2.setmDataList(childist);
            lv3Adapter2.notifyDataSetChanged();
        }

    }

    protected void lv3DownListViewClick(final EquipmentEntity bean) {
        boolean flag=false;
        List<EquipmentLayoutBean> beanList = new ArrayList<>();
//      //如果不是叶子节点
        if (!bean.getIsLeafNode()) {
            if (bean.getEquipmentLevel() > 2) {
                //查询4级设备
                List<EquipmentEntity> ccEntityList = getInstance().queryByPidAndCequipId(bean.getEquipmentId());
                flag=false;
                for (EquipmentEntity ccEntity : ccEntityList) {
                    EquipmentLayoutBean leafBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, ccEntity.getEquipmentId());
                    leafBean.setTextName(ccEntity.getEquipmentName());
                    List<EquipmentItemEntity> itemList = ccEntity.getItemList();
                    for (EquipmentItemEntity itemEntity : itemList) {
                        EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemEntity.getEquipmentItemId());
                        itemBean.setInputType(itemEntity.getInputType());
                        if (itemEntity.getUnitName() == null) {
                            itemBean.setTextName(itemEntity.getItemName());
                        } else {
                            itemBean.setTextName(itemEntity.getItemName() + "(" + itemEntity.getUnitName() + ")");
                        }

                        itemBean.setValue(setNullEdit(itemEntity.getDefaultValue()));
                        leafBean.getChildrenList().add(itemBean);
                    }
                    beanList.add(leafBean);
                }
            }
        }else {//如果是叶子节点
            //查找设备零件
            List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(bean.getEquipmentId());
            //为了便于展示  id不准确
            EquipmentLayoutBean itemBeans = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, 0);
            //生成零件页面
            flag=true;
            for (EquipmentItemEntity itemEntity : itemList) {
                EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemEntity.getEquipmentItemId());
                itemBean.setInputType(itemEntity.getInputType());
                if (itemEntity.getUnitName() == null) {
                    itemBean.setTextName(itemEntity.getItemName());
                } else {
                    itemBean.setTextName(itemEntity.getItemName() + "(" + itemEntity.getUnitName() + ")");
                }
                itemBean.setValue(setNullEdit(itemEntity.getDefaultValue()));
//                beanList.add(itemBean);
                itemBeans.getChildrenList().add(itemBean);
            }
            beanList.add(itemBeans);
        }
        EquipmentDetailsDialog dialog = new EquipmentDetailsDialog(false,this, bean.getEquipmentName(), beanList, bean.getEquipmentId(),flag,new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialog.show();
    }

    //判断回来的数据是否为叶结点，展示不一样的视图
    public void IsVisibility(boolean flag){
        if(flag){
            isTowLeaf=false;
            curHolder.eq_de_list_feal_list.setVisibility(View.GONE);
        }else{
            isTowLeaf=true;
            curHolder.eq_de_list_feal_list.setVisibility(View.VISIBLE);
            curHolder.eq_de_list_lv4.setVisibility(View.GONE);
            curHolder.eq_de_list_lv4_details.setVisibility(View.GONE);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean bean) {
        if(bean.getKind().equals(EventKind.EVENT_COMPANY_ADD)){
            String message = bean.getMessage();
            boolean success = bean.getSuccess();
            boolean aNew = bean.isNew();
            List<EquipmentLayoutBean> beans = bean.getObj();
            String name = bean.getName();
            int equipmentId = Integer.parseInt(message);
            ClickDialogDetails(success,aNew,name,equipmentId,beans);
        }
        if(bean.getKind().equals(EventKind.EVENT_COMPANY_IMPORTLIST)){
            List<DataBean> dataBeanList1= bean.getDataBeanList();
            for (int i = 0; i < dataBeanList1.size(); i++) {
                dataBeanList.add(dataBeanList1.get(i));
                recorddataBeanList.add(dataBeanList1.get(i));
            }
            mAdapter.setDataBeanList(dataBeanList);
            mAdapter.notifyDataSetChanged();
            //包含设备数
            curHolder.eq_de_num.setText(dataBeanList.size()+"");
        }

    }

    protected void ClickDialogDetails(final boolean flag,boolean isNew,String name,int equipmentId,List<EquipmentLayoutBean> beans) {
        List<EquipmentLayoutBean> beanList = new ArrayList<>();
        DataBean dataBean = new DataBean();
        dataBean.setParentLeftTxt(name);
        dataBean.setRecord(0);
//      //如果不是叶子节点
        if (!flag) {
            //查询4级设备
            List<EquipmentEntity> ccEntityList = getInstance().queryByPidAndCequipId(equipmentId);
            for (int i = 0; i <ccEntityList.size() ; i++) {
                EquipmentLayoutBean leafBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, ccEntityList.get(i).getEquipmentId());
                if(!isNew){
                    dataBean.setParentLeftTxt(name);
                }else{
                    if(name.contains("复制")){
                        dataBean.setParentLeftTxt(name);
                    }else{
                        dataBean.setParentLeftTxt(name+"复制");
                    }
                }

                //遍历添加二级数据
                leafBean.setTextName(ccEntityList.get(i).getEquipmentName());
                leafBean.setCompanyEquipmentId(0);
                List<EquipmentItemEntity> itemList = ccEntityList.get(i).getItemList();
                for (int j = 0; j < itemList.size(); j++) {
                    EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemList.get(j).getEquipmentItemId());
                    //遍历添加二级详情数据
                    itemBean.setInputType(itemList.get(j).getInputType());
                    if (itemList.get(j).getUnitName() == null) {
                        itemBean.setTextName(itemList.get(j).getItemName());
                    } else {
                        itemBean.setTextName(itemList.get(j).getItemName() + "(" + itemList.get(j).getUnitName() + ")");
                    }
                    itemBean.setCompanyEquipmentId(0);
                    itemBean.setEquipmentInfoId(0);
                    itemBean.setValue(beans.get(i).getChildrenList().get(j).getValue());
                    leafBean.getChildrenList().add(itemBean);
                }
                //记录是否叶结点
                dataBean.setLeaf(flag);
                beanList.add(leafBean);
                dataBean.setBean(beanList);
                //记录最大的id.用于新建相同设备； 还有排序比较
                dataBean.setItemid(equipmentId);
            }

        }else {//如果是叶子节点
            //记录是否叶结点
            dataBean.setLeaf(flag);
            if(!isNew){
                dataBean.setParentLeftTxt(name);
            }else{
                if(name.contains("复制")){
                    dataBean.setParentLeftTxt(name);
                }else{
                    dataBean.setParentLeftTxt(name+"复制");
                }
            }
            //查找设备零件
            List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(equipmentId);
            //为了便于展示  id不准确
            EquipmentLayoutBean itemBeans = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, 0);
            //生成零件页面
            for (int i = 0; i < itemList.size(); i++) {
                EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemList.get(i).getEquipmentItemId());
                itemBean.setInputType(itemList.get(i).getInputType());
                if (itemList.get(i).getUnitName() == null) {
                    itemBean.setTextName(itemList.get(i).getItemName());
                } else {
                    itemBean.setTextName(itemList.get(i).getItemName() + "(" + itemList.get(i).getUnitName() + ")");
                }
//              同级展示不好实现，所以添加到子条目里
                itemBean.setCompanyEquipmentId(0);
                itemBean.setEquipmentInfoId(0);
                itemBean.setValue(beans.get(0).getChildrenList().get(i).getValue());
                itemBeans.getChildrenList().add(itemBean);
            }
            beanList.add(itemBeans);
            dataBean.setBean(beanList);
            //排序比较
            dataBean.setItemid(equipmentId);
        }
        dataBean.setChildBean(dataBean);
        dataBeanList.add(dataBean);
        recorddataBeanList.add(dataBean);
        Collections.sort(dataBeanList, new Comparator<DataBean>() {
            @Override
            public int compare(DataBean dataBean, DataBean t1) {
                return  Integer.valueOf(dataBean.getItemid()).compareTo(Integer.valueOf(t1.getItemid()));
            }
        });
        //包含设备数
        curHolder.eq_de_num.setText(dataBeanList.size()+"");
        mAdapter.setDataBeanList(dataBeanList);
        mAdapter.setRecordDataBeanList(recorddataBeanList);

        mAdapter.notifyDataSetChanged();
    }
    //四级设备   二级详情展示
    private void setData(){
        curHolder.eq_de_list_lv4_details.setLayoutManager(new WrapContentLinearLayoutManager(this));
        mAdapter = new RecyclerAdapter(this);
        curHolder.eq_de_list_lv4_details.setAdapter(mAdapter);
        curHolder.eq_de_list_lv4_details.getItemAnimator().setChangeDuration(300);
        curHolder.eq_de_list_lv4_details.getItemAnimator().setMoveDuration(300);
        mAdapter.setOnClickTextViewData(new RecyclerAdapter.OnClickTextViewData() {
            @Override
            public void OnClickTextViewDataListener(int text) {
                //设备数量
                curHolder.eq_de_num.setText(text+"");
            }
        });
    }

}