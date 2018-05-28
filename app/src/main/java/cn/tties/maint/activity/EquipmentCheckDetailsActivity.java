package cn.tties.maint.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.security.spec.PSSParameterSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonAppendListViewAdapter;
import cn.tties.maint.adapter.CommonSwipListViewAdapter;
import cn.tties.maint.adapter.EquipmentCheckDetailsAdapter;
import cn.tties.maint.adapter.EquipmentLayoutAdapter;
import cn.tties.maint.adapter.MyEquipmentLayout_RecyAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.CreateBoxBody;
import cn.tties.maint.bean.CreateEquipFiveParam;
import cn.tties.maint.bean.CreateEquipFourParam;
import cn.tties.maint.bean.CreateEquipItemParam;
import cn.tties.maint.bean.CreateEquipParam;
import cn.tties.maint.bean.CreateEquipTwoParam;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.dao.EquipmentItemDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.EquipmentItemEntity;
import cn.tties.maint.holder.EquipmentDetailsActivityHolder;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.CreateComEquParams;
import cn.tties.maint.httpclient.params.CreateNewComEquParams;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.secondLv.RecyclerAdapter;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.PinYinUtils;
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

    protected CommonSwipListViewAdapter lv3Adapter2;
    //加载节点布局
    private EquipmentLayoutAdapter detailAdapter;
    private EquipmentEntity entity;
    //二级列表展示
    private List<DataBean> dataBeanList = new ArrayList<>();;
    private DataBean dataBean;
    private RecyclerAdapter mAdapter;
    boolean cabinet=false;
//    List<String> listName = new ArrayList<>();\
    boolean isTowLeaf=false;
    private int curEleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsActivity = this;
        eEntity = (CommonListViewInterface) getIntent().getSerializableExtra("bean");
        curEleId = (Integer) getIntent().getSerializableExtra("curEleId");
        entity = (EquipmentEntity) eEntity;
        initFindeViewById();
        Log.i(TAG, "onCreate: " + eEntity.getItemId());
    }

    private void initFindeViewById() {
        curHolder = new EquipmentDetailsActivityHolder(layout_info);
        initView();
        initLv3ListView();
        initisCabinet();
        setData();
        curHolder.eq_de_confirm.setOnClickListener(this);
        curHolder.eq_de_cancel.setOnClickListener(this);
        curHolder.eq_de_start.setOnClickListener(this);
        curHolder.eq_de_old.setOnClickListener(this);
//        initDetailListView();
    }
    //是否展示柜体可选
    private void initisCabinet() {
        if(!cabinet){
           curHolder.eq_de_radiogroup.setVisibility(View.GONE);
           curHolder.eq_de_CabinetTV.setVisibility(View.GONE);
        }else{
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
                dataBeanList.clear();
//                listName.clear();
//                isAllClose.clear();
                mAdapter.notifyDataSetChanged();
                break;
            //导入先前配置
            case R.id.eq_de_old:
                break;
                default:
                    break;

        }
    }

    private void addCompanyEquip() {
        //最大级的list集合包含二级（三级（四级））
        List<CreateEquipParam> twoList = new ArrayList<>();
        //企业设备下级设备
        List<CreateEquipItemParam> threeList = new ArrayList<>();
        List<CreateEquipTwoParam> arrTwoList = new ArrayList<>();
        //二级子节点
        List<CreateEquipTwoParam> arrTwoLists = new ArrayList<>();
        List<CreateEquipFourParam> arrFourList = new ArrayList<>();
        //是否又柜子
        List<CreateBoxBody> boxBodyList = new ArrayList<>();

        //二级企业设备
        CreateEquipParam cEquipParam = new CreateEquipParam();
        //设备id 如果是二级子节点得话，curHolder.id就是刚跳转过来bean得ID
        cEquipParam.setEquipmentId(curHolder.id);
        //公司表号
        cEquipParam.setEleAccountId(curEleId);//不知道会不会拿到；
        //设备名称
        cEquipParam.setComEquName(curHolder.edit_value.getText().toString());
        //顶级id 都是-1;
        cEquipParam.setPid(-1);

        //是否有柜体
        if (curHolder.isCabinet) {
            CreateBoxBody boxBody=new CreateBoxBody();
//                    传递过来得二级设备id
            List<CreateEquipFiveParam> arrayLists = new ArrayList<>();
            CreateEquipFiveParam createEquipFiveParam=new CreateEquipFiveParam();
            createEquipFiveParam.setEquItemId(eEntity.getItemId());
            arrayLists.add(createEquipFiveParam);
            boxBody.setBoxBodyEquItemArr(arrayLists);
            boxBodyList.add(boxBody);
            cEquipParam.setBoxBody(boxBodyList);
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
            for (int i = 0; i < dataBeanList.size(); i++) {
                DataBean dataBean = dataBeanList.get(i);
                //三级
                CreateEquipItemParam threeParam = new CreateEquipItemParam();
                //三级id
                threeParam.setEquId(dataBean.getItemid());
                //三级输入框值
                threeParam.setEquName(dataBean.getParentLeftTxt());
                for (int j = 0; j <dataBeanList.get(i).getBean().size() ; j++) {
                    EquipmentLayoutBean bean = dataBeanList.get(i).getBean().get(j);
                    switch (bean.getType()){
                        //三级节点
                        case EquipmentLayoutAdapter.EDITTEXT:
                            CreateEquipTwoParam createEquipTwoParam=new CreateEquipTwoParam();
                            createEquipTwoParam.setEquItemId(bean.getItemId());
                            createEquipTwoParam.setEquItemContent(bean.getValue());
                            arrTwoList.add(createEquipTwoParam);
                            //三级添加节点
                            threeParam.setComItemThree(arrTwoList);
                            break;
                        //三级含四级
                        case EquipmentLayoutAdapter.LEAF:
                            //四级
                            CreateEquipFourParam fourParam = new CreateEquipFourParam();
                            fourParam.setEquIdFour(bean.getItemId());
                            List<CreateEquipFiveParam> fourItemList = new ArrayList<>();
                            for (EquipmentLayoutBean itemBean : bean.getChildrenList()) {
                                CreateEquipFiveParam createEquipFiveParam=new CreateEquipFiveParam();
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
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }

    private void initView() {
        //设备名称
        curHolder.eq_de_name.setText(entity.getEquipmentName());
        //设备重命名
        curHolder.edit_value.setText(entity.getEquipmentName());
        //是否又柜体
//        if(curHolder.isCabinet){
//            curHolder.eq_de_yes.setChecked(true);
//        }else{
//            curHolder.eq_de_no.setChecked(true);
//        }

    }
    //四级请求数据展示
    protected void initLv3ListView() {
        //加载节点布局
        detailAdapter = new EquipmentLayoutAdapter(this, new ArrayList<EquipmentLayoutBean>());
        curHolder.eq_de_list_feal_list.setAdapter(detailAdapter);
        lv3Adapter2 = new CommonSwipListViewAdapter();
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
        //是否是叶子节点
        if (!entity.getIsLeafNode()) {
            IsVisibility(true);
            //查询下级设备
            List<EquipmentEntity> childist = getInstance().queryByPid(entity.getEquipmentId());
            for (EquipmentEntity entity : childist) {
                switch (entity.getEquipmentType()) {
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
//                listName.add(entity.getEquipmentName());
            }
            lv3Adapter2.setmDataList(childist);
            lv3Adapter2.notifyDataSetChanged();
        } else {
            //如果是二级节点得话，只展示list
            IsVisibility(false);
            //查找设备零件
            List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(entity.getEquipmentId());
            curHolder.id=eEntity.getItemId();
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
    }

    protected void lv3DownListViewClick(final EquipmentEntity bean) {
        boolean flag=false;
        List<EquipmentLayoutBean> beanList = new ArrayList<>();
        curHolder.id=bean.getEquipmentId();
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
                beanList.add(itemBean);
            }
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
        if(bean.getKind().equals(EventKind.EVENT_COMPANY_EDITOR)){
            boolean success = bean.getSuccess();
            List<EquipmentLayoutBean> equipmentlayoutBean= bean.getObj();
            String  equipmentlayoutBean_name = bean.getName();
            int position = bean.getPosition();
            DataBean dataBean = new DataBean();
            dataBean.setBean(equipmentlayoutBean);
            dataBean.setParentLeftTxt(equipmentlayoutBean_name);
            mAdapter.setEditor(position,dataBean);
        }

    }

    protected void ClickDialogDetails(final boolean flag,boolean isNew,String name,int equipmentId,List<EquipmentLayoutBean> beans) {
        List<EquipmentLayoutBean> beanList = new ArrayList<>();
        dataBean = new DataBean();
        dataBean.setParentLeftTxt(name);
//      //如果不是叶子节点
        if (!flag) {
            //查询4级设备
            List<EquipmentEntity> ccEntityList = getInstance().queryByPidAndCequipId(equipmentId);
            for (int i = 0; i <ccEntityList.size() ; i++) {
                EquipmentLayoutBean leafBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, ccEntityList.get(i).getEquipmentId());
                if(!isNew){
                    dataBean.setParentLeftTxt(name);
                }else{
                    dataBean.setParentLeftTxt(name+"复制");
                }

                //遍历添加二级数据
                leafBean.setTextName(ccEntityList.get(i).getEquipmentName());
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
            //查找设备零件
            List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(equipmentId);
            //生成零件页面
            for (int i = 0; i < itemList.size(); i++) {
                EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemList.get(i).getEquipmentItemId());
                itemBean.setInputType(itemList.get(i).getInputType());
                if (itemList.get(i).getUnitName() == null) {
                    itemBean.setTextName(itemList.get(i).getItemName());
                } else {
                    itemBean.setTextName(itemList.get(i).getItemName() + "(" + itemList.get(i).getUnitName() + ")");
                }
                //记录是否叶结点
                dataBean.setLeaf(flag);
//              同级展示不好实现，所以添加到子条目里
                itemBean.setValue(beans.get(i).getValue());
                if(!isNew){
                    dataBean.setParentLeftTxt(name);
                }else{
                    dataBean.setParentLeftTxt(name+"复制");
                }
                beanList.add(itemBean);
                dataBean.setBean(beanList);
                //排序比较
                dataBean.setItemid(equipmentId);
            }

        }
        dataBean.setChildBean(dataBean);
        dataBeanList.add(dataBean);
        Collections.sort(dataBeanList, new Comparator<DataBean>() {
            @Override
            public int compare(DataBean dataBean, DataBean t1) {
                return  Integer.valueOf(dataBean.getItemid()).compareTo(Integer.valueOf(t1.getItemid()));
            }
        });
        //包含设备数
        curHolder.eq_de_num.setText(dataBeanList.size()+"");
        mAdapter.setDataBeanList(dataBeanList);
        mAdapter.notifyDataSetChanged();
    }
    //四级设备   二级详情展示
    private void setData(){
        curHolder.eq_de_list_lv4_details.setLayoutManager(new WrapContentLinearLayoutManager(this));
        mAdapter = new RecyclerAdapter(this);
        curHolder.eq_de_list_lv4_details.setAdapter(mAdapter);
        curHolder.eq_de_list_lv4_details.getItemAnimator().setChangeDuration(300);
        curHolder.eq_de_list_lv4_details.getItemAnimator().setMoveDuration(300);
    }

}