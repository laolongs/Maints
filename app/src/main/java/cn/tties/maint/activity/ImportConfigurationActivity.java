package cn.tties.maint.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
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
import cn.tties.maint.adapter.EquiRecyclerAdapter;
import cn.tties.maint.adapter.EquipmentLayoutAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.EquipmentcheckBean;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.bean.ImportConfigurationBean;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.dao.EquipmentItemDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.EquipmentItemEntity;
import cn.tties.maint.enums.LineInType;
import cn.tties.maint.enums.MeterEquipmentType;
import cn.tties.maint.holder.ImportConfigurationActivityHolder;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.CompanyParams;
import cn.tties.maint.httpclient.params.SelectCompanyEquipmentByCompanyParams;
import cn.tties.maint.httpclient.params.SelectImportConfigurationtListParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.EleAccountResult;
import cn.tties.maint.httpclient.result.QuertionResult;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.widget.WrapContentLinearLayoutManager;

import static cn.tties.maint.dao.EquipmentDao.getInstance;

@ContentView(R.layout.activity_import_configuration)
public class ImportConfigurationActivity extends BaseFragmentActivity implements View.OnClickListener {
    private static final String TAG = "ImportConfigurationActi";
    @ViewInject(R.id.import_con_layout_info)
    private LinearLayout layout_info;
    private ImportConfigurationActivityHolder curHolder;
    public EquiRecyclerAdapter mAdapter;
    private List<DataBean> dataBeanList= new ArrayList<>();
    private List<CompanyResult> companyList;
    private ArrayAdapter<String> companyAdapter;
    private QuertionResult searchEntity;
    private List<String> eleList = new ArrayList<String>();;
    private int equipmentId;
    private boolean cabinet;
    private DataBean dataBean;
    //加载节点布局
    private EquipmentLayoutAdapter detailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFindeViewById();
        equipmentId = getIntent().getIntExtra("equipmentId", 0);
        Log.i(TAG, "onCreate:equipmentId "+ equipmentId);
    }

    private void initFindeViewById() {
        searchEntity = new QuertionResult();
        curHolder = new ImportConfigurationActivityHolder(layout_info);
        curHolder.import_con_btn_import.setOnClickListener(this);
        curHolder.import_con_cancel.setOnClickListener(this);
        showHoulder(1);
        setData();
        getCompanyList();
    }
    //得到运维管理人员下得全部企业
    private void getCompanyList() {
        companyList = new ArrayList<>();
        CompanyParams params = new CompanyParams();
        params.setMaintStaffId(MyApplication.getUserInfo().getMaintStaffId());
        Log.i(TAG, "getCompanyList: "+MyApplication.getUserInfo().getMaintStaffId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                companyList.clear();
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), "获取企业信息失败", Toast.LENGTH_SHORT).show();
                } else {
                    companyList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<CompanyResult>>() {
                    }.getType());
                }
                List<String> strList = new ArrayList<String>();
                strList.add("全部企业");
                for (CompanyResult companyResult : companyList) {
                    strList.add(companyResult.getCompanyShortName());
                }
                companyAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, strList);
                curHolder.import_con_company.setAdapter(companyAdapter);
                curHolder.import_con_company.setOnItemSelectedListener(
                        new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i == 0) {
                                    eleList.clear();
                                    searchEntity.setCompanyId(null);
                                } else {
                                    searchEntity.setCompanyId(companyList.get(i - 1).getCompanyId());
                                    getEleList(companyList,(int)companyList.get(i - 1).getCompanyId());
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        }
                );
            }
        });
    }
    //查询户号==表号
    private void getEleList(List<CompanyResult> companyList,int curCompanyId) {
        final List<Integer> eleAccountIdList = new ArrayList<Integer>();
        for (int i = 0; i < companyList.size(); i++) {
            if(companyList.get(i).getCompanyId()==curCompanyId){
                List<EleAccountResult> eleAccountList = companyList.get(i).getEleAccountList();
                for (int j = 0; j < eleAccountList.size(); j++) {
                    eleList.add(eleAccountList.get(j).getEleNo());
                    eleAccountIdList.add(eleAccountList.get(j).getEleAccountId());
                }

            }
        }
        ArrayAdapter<String> eleAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, eleList);
        curHolder.import_con_ele.setAdapter(eleAdapter);
        curHolder.import_con_ele.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 0; j < eleAccountIdList.size(); j++) {
                    if(i==j){
                        getEquipmentList(eleAccountIdList.get(i));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    //查询设备==企业设备
    private void getEquipmentList(Integer eleaccountid) {
        SelectImportConfigurationtListParams params=new SelectImportConfigurationtListParams();
        params.setEleAccountId(eleaccountid);
        params.setEquipmentId(equipmentId);
        Log.i(TAG, "getEquipmentList: "+eleaccountid);
        Log.i(TAG, "getEquipmentListequipmentId: "+equipmentId);
        HttpClientSend.getInstance().send(params,new BaseStringCallback(){
            @Override
            public void onSuccess(String result) {
                final ImportConfigurationBean ret = JsonUtils.deserialize(result, ImportConfigurationBean.class);
                if(ret.getErrorCode()!=0){
                    Toast.makeText(ImportConfigurationActivity.this, ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                final List<String> stringlist = new ArrayList<String>();
                for (int i = 0; i < ret.getResult().size(); i++) {
                    stringlist.add(ret.getResult().get(i).getName());
                }
                ArrayAdapter<String> equipmentAdapter = new ArrayAdapter<String>(ImportConfigurationActivity.this, R.layout.spinner_text, stringlist);
                curHolder.import_con_equipment.setAdapter(equipmentAdapter);
                //设备
                curHolder.import_con_equipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        showHoulder(2);
                        equipmentSend(ret.getResult().get(i).getCompanyEquipmentId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
                //导入
            case R.id.import_con_btn_import:
                EventBusBean busBean=new EventBusBean();
                busBean.setKind(EventKind.EVENT_COMPANY_IMPORTLIST);
                busBean.setDataBeanList(dataBeanList);
                EventBus.getDefault().post(busBean);
                finish();
                break;
                //返回
            case R.id.import_con_cancel:
                    finish();
                break;
        }
    }
    protected void equipmentSend(int CompanyEquipmentId) {
        dataBeanList.clear();
        mAdapter.notifyDataSetChanged();
        SelectCompanyEquipmentByCompanyParams params=new SelectCompanyEquipmentByCompanyParams();
        params.setCompanyEquipmentId(CompanyEquipmentId);
        Log.i(TAG, "lv2UpListViewClick+bean.getItemId: "+CompanyEquipmentId);
        HttpClientSend.getInstance().send(params,new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                {
                    BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                    if (ret.getErrorCode() != 0) {
                        Toast.makeText(ImportConfigurationActivity.this, ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    EquipmentcheckBean eleRoomResultList = JsonUtils.deserialize(ret.getResult(), EquipmentcheckBean.class);
                    //设备类型
                    List<EquipmentEntity> list = EquipmentDao.getInstance().queryByPid(eleRoomResultList.getParentEquipmentId());
                    //设备类型赋值
                    for (int i = 0; i < list.size(); i++) {
                        Log.i(TAG, "onSuccess:list " + list.get(i).getItemName());
                        EquipmentEntity equipmentEntity = list.get(i);
                        switch (equipmentEntity.getEquipmentType()) {
                            //有柜体
                            case CABINET:
                                cabinet = true;
                                Log.i(TAG, "initLv3ListView---------: " + "有");
                                break;
                            //没有柜体
                            default:
                                Log.i(TAG, "initLv3ListView---------: " + "没有");
                                break;
                        }
                        if (list.get(i).getItemId() == eleRoomResultList.getTwoCompanyEquipment().getEquipmentId()) {
                            //类型名称
                            curHolder.import_con_type.setText(list.get(i).getItemName());
                        }
                    }
                    //是否有柜体
                    if (!cabinet) {
                        curHolder.import_con_iscabinet.setText("否");
                    } else {
                        curHolder.import_con_iscabinet.setText("是");
                    }

                    //设备名称
                    curHolder.import_con_name.setText(eleRoomResultList.getTwoCompanyEquipment().getName());
                    List<EquipmentcheckBean.ThreeCompanyEquipmentListBean> threeCompanyEquipmentList = eleRoomResultList.getThreeCompanyEquipmentList();
                    //设备数量
                    curHolder.import_con_num.setText(threeCompanyEquipmentList.size() + "");
                    if (threeCompanyEquipmentList.size() != 0) {
                        showHoulder(2);

                        for (int i = 0; i < threeCompanyEquipmentList.size(); i++) {
                            //三级设备信息
                            final List<EquipmentLayoutBean> beanList = new ArrayList<>();
                            //三级企业设备信息
                            final List<EquipmentLayoutBean> beanLists = new ArrayList<>();
                            //三级企业设备和设备对比赋值存放信息
                            final List<EquipmentLayoutBean> beanListall = new ArrayList<>();
                            dataBean = new DataBean();
//                            Log.i(TAG, "onSuccess: 三级企业设备iD" + threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getCompanyEquipmentId());
                            //企业设备属性iD  因为是导入得，所以不需要EquipmentInfoId 和公司id
                            dataBean.setCompanyEquipmentId(0);
//                            dataBean.setCompanyequipmentId(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getCompanyEquipmentId());
                            dataBean.setRecord(1);
                            dataBean.setParentLeftTxt(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getName());
                            EquipmentEntity equipmentEntity = EquipmentDao.getInstance().queryById(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getEquipmentId());
                            //是三级节点
                            if (equipmentEntity.getIsLeafNode()) {
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
                                    itemMap.put(threeCompanyEquipmentItem.get(j).getEquipmentItem().getEquipmentItemId(), threeCompanyEquipmentItem.get(j).getEquipmentInfo());
                                    if (threeCompanyEquipmentItem.get(j).getEquipmentInfoId() == null) {
                                        itemMapinfoid.put(threeCompanyEquipmentItem.get(j).getEquipmentItem().getEquipmentItemId(), 0);
                                    } else {
                                        itemMapinfoid.put(threeCompanyEquipmentItem.get(j).getEquipmentItem().getEquipmentItemId(), threeCompanyEquipmentItem.get(j).getEquipmentInfoId());
                                    }
                                    //企业设备属性iD
                                    Log.i(TAG, "onSuccess: 三级企业设备属性iD" + threeCompanyEquipmentItem.get(j).getEquipmentInfoId());
                                    itemBean.setEquipmentInfoId(threeCompanyEquipmentItem.get(j).getEquipmentInfoId());
                                    Log.i(TAG, "onSuccess: setValue" + itemBean.getValue());
                                    beanList.add(itemBean);
                                }

                                EquipmentLayoutBean itemBeans = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, 0);
                                for (int j = 0; j < beanLists.size(); j++) {
                                    EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemList.get(j).getEquipmentItemId());
                                    EquipmentLayoutBean bean1 = beanLists.get(j);
                                    String info = itemMap.get(itemList.get(j).getEquipmentItemId());
                                    Integer infoid = itemMapinfoid.get(itemList.get(j).getEquipmentItemId());
//
                                    itemBean.setValue(info == null ? bean1.getValue() : info);
                                    //企业设备属性iD  因为是导入得，所以不需要EquipmentInfoId 和公司id
//                                    itemBean.setEquipmentInfoId(infoid == 0 ? 0 : infoid);
                                    itemBean.setEquipmentInfoId(0);
                                    itemBean.setTextName(bean1.getTextName());
                                    itemBeans.getChildrenList().add(itemBean);
                                }
                                beanListall.add(itemBeans);
                                dataBean.setBean(beanListall);
                                dataBeanList.add(dataBean);
                            } else {//四级设备
                                //四级设备信息
                                final List<EquipmentLayoutBean> beanListfour = new ArrayList<>();
                                //四级企业设备信息
                                final List<EquipmentLayoutBean> beanListfours = new ArrayList<>();
                                //四级企业设备和设备对比赋值存放信息
                                final List<EquipmentLayoutBean> beanListfourall = new ArrayList<>();
                                int num = 0;
                                //查询4级设备
                                List<EquipmentEntity> ccEntityList = getInstance().queryByPidAndCequipId(threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getEquipmentId());
                                for (int k = 0; k < ccEntityList.size(); k++) {
                                    EquipmentLayoutBean leafBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, ccEntityList.get(k).getEquipmentId());
                                    //用于排序
                                    dataBean.setItemid(ccEntityList.get(k).getEquipmentId());
                                    Log.i(TAG, "onSuccess: 四级名字" + ccEntityList.get(k).getEquipmentName());
                                    //遍历添加二级数据
                                    leafBean.setTextName(ccEntityList.get(k).getEquipmentName());
                                    List<EquipmentItemEntity> itemLists = ccEntityList.get(k).getItemList();
                                    for (int l = 0; l < itemLists.size(); l++) {
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
                                Log.i(TAG, "onSuccess: 本地查询出来得长度" + beanListfour.size());
                                List<EquipmentcheckBean.ThreeCompanyEquipmentListBean.FourCompanyEquipmentListBean> fourCompanyEquipmentList = threeCompanyEquipmentList.get(i).getFourCompanyEquipmentList();
                                //四级------------------------
                                //对应item 的Map
                                Map<Integer, String> itemMap = new HashMap<>();
                                Map<Integer, Integer> itemMapinfoid = new HashMap<>();
                                for (int j = 0; j < fourCompanyEquipmentList.size(); j++) {
                                    EquipmentLayoutBean leafBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, fourCompanyEquipmentList.get(j).getFourCompanyEquipment().getEquipmentId());
                                    List<EquipmentcheckBean.ThreeCompanyEquipmentListBean.FourCompanyEquipmentListBean.FourCompanyEquipmentItemBean> fourCompanyEquipmentItem = fourCompanyEquipmentList.get(j).getFourCompanyEquipmentItem();
                                    leafBean.setCompanyEquipmentId(fourCompanyEquipmentList.get(j).getFourCompanyEquipment().getCompanyEquipmentId());
                                    Log.i(TAG, "onSuccess: 四级rrrr企业设备iD" + leafBean.getCompanyEquipmentId());
                                    for (int k = 0; k < fourCompanyEquipmentItem.size(); k++) {
                                        EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, fourCompanyEquipmentItem.get(k).getEquipmentItem().getEquipmentItemId());
                                        //企业设备属性iD
                                        itemBean.setEquipmentInfoId(fourCompanyEquipmentItem.get(k).getEquipmentInfoId());
                                        Log.i(TAG, "onSuccess: 四级企业设备属性iD" + fourCompanyEquipmentItem.get(k).getEquipmentInfoId());
                                        itemBean.setValue(setNullEdit(fourCompanyEquipmentItem.get(k).getEquipmentInfo()));
                                        itemMap.put(fourCompanyEquipmentItem.get(k).getEquipmentItem().getEquipmentItemId(), fourCompanyEquipmentItem.get(k).getEquipmentInfo());
                                        if (fourCompanyEquipmentItem.get(k).getEquipmentInfoId() == null) {
                                            itemMapinfoid.put(fourCompanyEquipmentItem.get(k).getEquipmentItem().getEquipmentItemId(), 0);
                                        } else {
                                            itemMapinfoid.put(fourCompanyEquipmentItem.get(k).getEquipmentItem().getEquipmentItemId(), fourCompanyEquipmentItem.get(k).getEquipmentInfoId());
                                        }
                                        leafBean.getChildrenList().add(itemBean);
                                    }
                                    beanListfours.add(leafBean);
                                }
                                for (int k = 0; k < beanListfour.size(); k++) {
                                    EquipmentLayoutBean leafBeans = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, threeCompanyEquipmentList.get(i).getThreeCompanyEquipment().getEquipmentId());
                                    leafBeans.setTextName(beanListfour.get(k).getTextName());
                                    //企业设备属性iD  因为是导入得，所以不需要EquipmentInfoId 和公司id
                                    leafBeans.setCompanyEquipmentId(0);
//                                    leafBeans.setCompanyEquipmentId(beanListfours.get(k).getCompanyEquipmentId());
                                    Log.i(TAG, "onSuccess: 四级rrqqqqqqqqqqqqrr企业设备iD" + beanListfours.get(k).getCompanyEquipmentId());
                                    Log.i(TAG, "onSuccess:四级子长度 " + beanListfour.get(k).getChildrenList().size());
                                    for (int l = 0; l < beanListfour.get(k).getChildrenList().size(); l++) {//5
                                        String bean1 = itemMap.get(beanListfour.get(k).getChildrenList().get(l).getItemId());
                                        Integer infoid = itemMapinfoid.get(beanListfour.get(k).getChildrenList().get(l).getItemId());
                                        EquipmentLayoutBean leafBeanchild = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, beanListfour.get(k).getChildrenList().get(l).getItemId());
                                        Log.i(TAG, "onSuccess:四级本地子长度id " + beanListfour.get(k).getChildrenList().get(l).getItemId());
//                                        leafBeanchild.setEquipmentInfoId(infoid == 0 ? 0 : infoid);
                                        //企业设备属性iD  因为是导入得，所以不需要EquipmentInfoId 和公司id
                                        leafBeanchild.setEquipmentInfoId(0);
                                        leafBeanchild.setValue(bean1 == null ? beanListfour.get(k).getChildrenList().get(l).getValue() : bean1);
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
                                return Integer.valueOf(dataBean.getItemid()).compareTo(Integer.valueOf(t1.getItemid()));
                            }
                        });

                        mAdapter.setDataBeanList(dataBeanList);
                        mAdapter.notifyDataSetChanged();
                    }
                    List<EquipmentcheckBean.TwoCompanyEquipmentItemBean> twoCompanyEquipmentItem = eleRoomResultList.getTwoCompanyEquipmentItem();
                    if (twoCompanyEquipmentItem.size() != 0) {
                        //如果是二级节点得话，只展示list
                        showHoulder(1);
                        //二级节点 三级设备信息
                        //查找设备零件
                        List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(eleRoomResultList.getTwoCompanyEquipment().getEquipmentId());
                        Log.i(TAG, "onSuccess  二级  : " + itemList.size());
                        Log.i(TAG, "onSuccess: 二级 name" + itemList.get(0).getItemName());
                        List<EquipmentLayoutBean> beanLists = new ArrayList<>();
                        List<EquipmentLayoutBean> beanListall = new ArrayList<>();
                        for (int i = 0; i < itemList.size(); i++) {
                            EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemList.get(i).getEquipmentItemId());
                            itemBean.setTextName(itemList.get(i).getItemName());
                            itemBean.setValue(itemList.get(i).getDefaultValue());
                            beanLists.add(itemBean);
                        }
                        final List<EquipmentLayoutBean> beanList = new ArrayList<>();
                        Log.i(TAG, "onSuccess444: " + twoCompanyEquipmentItem.size());
                        Map<Integer, String> itemMap = new HashMap<>();
                        Map<Integer, Integer> itemMapinfoid = new HashMap<>();
                        for (int i = 0; i < twoCompanyEquipmentItem.size(); i++) {
                            EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, twoCompanyEquipmentItem.get(i).getEquipmentItem().getEquipmentItemId());
                            itemMap.put(twoCompanyEquipmentItem.get(i).getEquipmentItem().getEquipmentItemId(), twoCompanyEquipmentItem.get(i).getEquipmentInfo());
                            if (twoCompanyEquipmentItem.get(i).getEquipmentInfoId() == null) {
                                itemMapinfoid.put(twoCompanyEquipmentItem.get(i).getEquipmentItem().getEquipmentItemId(), 0);
                            } else {
                                itemMapinfoid.put(twoCompanyEquipmentItem.get(i).getEquipmentItem().getEquipmentItemId(), twoCompanyEquipmentItem.get(i).getEquipmentInfoId());
                            }
                            beanList.add(itemBean);
                        }
                        for (int i = 0; i < beanLists.size(); i++) {
                            EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, beanLists.get(i).getItemId());
                            String info = itemMap.get(beanLists.get(i).getItemId());
                            Integer infoid = itemMapinfoid.get(beanLists.get(i).getItemId());
                            itemBean.setTextName(itemList.get(i).getItemName());
                            itemBean.setValue(info == null ? itemList.get(i).getDefaultValue() : info);
                            //企业设备属性iD  因为是导入得，所以不需要EquipmentInfoId 和公司id
                            itemBean.setEquipmentInfoId(0);
//                            itemBean.setEquipmentInfoId(infoid == 0 ? 0 : infoid);
                            beanListall.add(itemBean);
                        }

                        //叶子节点适配器
                        detailAdapter.setDataList(beanListall);
                        detailAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }
    private void setData(){
        //加载节点布局
        detailAdapter = new EquipmentLayoutAdapter(this, new ArrayList<EquipmentLayoutBean>());
        curHolder.import_con_leaf_list.setAdapter(detailAdapter);
        curHolder.import_con_details.setLayoutManager(new WrapContentLinearLayoutManager(this));
        mAdapter = new EquiRecyclerAdapter(this);
        curHolder.import_con_details.setAdapter(mAdapter);
        curHolder.import_con_details.getItemAnimator().setChangeDuration(300);
        curHolder.import_con_details.getItemAnimator().setMoveDuration(300);
    }
    private void showHoulder(int type) {
        switch (type){
            case 1:
                curHolder.import_con_all.setVisibility(View.GONE);
                curHolder.import_con_hite.setVisibility(View.VISIBLE);
                break;
            case 2:
                curHolder.import_con_all.setVisibility(View.VISIBLE);
                curHolder.import_con_hite.setVisibility(View.GONE);
                break;

        }
    }
}
