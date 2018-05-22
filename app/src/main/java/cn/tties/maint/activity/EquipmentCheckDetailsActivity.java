package cn.tties.maint.activity;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonAppendListViewAdapter;
import cn.tties.maint.adapter.CommonSwipListViewAdapter;
import cn.tties.maint.adapter.EquipmentCheckDetailsAdapter;
import cn.tties.maint.adapter.EquipmentLayoutAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.dao.EquipmentItemDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.EquipmentItemEntity;
import cn.tties.maint.holder.EquipmentDetailsActivityHolder;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.secondLv.RecyclerAdapter;
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

    protected CommonSwipListViewAdapter lv3Adapter1;
    protected CommonSwipListViewAdapter lv3Adapter2;
    //加载节点布局
    private EquipmentLayoutAdapter detailAdapter;
    private EquipmentEntity entity;
    //二级列表展示
    private List<DataBean> dataBeanList = new ArrayList<>();;
    private DataBean dataBean;
    private RecyclerAdapter mAdapter;
    int j=1;
    int k=0;
    int num=0;
    int nums=0;
    List<String> listName = new ArrayList<>();
    List<Boolean> isAllClose = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsActivity = this;
        eEntity = (CommonListViewInterface) getIntent().getSerializableExtra("bean");
        entity = (EquipmentEntity) eEntity;
        initFindeViewById();
        Log.i(TAG, "onCreate: " + eEntity.getItemId());
    }

    private void initFindeViewById() {
        curHolder = new EquipmentDetailsActivityHolder(layout_info);
        initView();
        initLv3ListView();
        setData();
        curHolder.eq_de_confirm.setOnClickListener(this);
        curHolder.eq_de_cancel.setOnClickListener(this);
        curHolder.eq_de_start.setOnClickListener(this);
        curHolder.eq_de_old.setOnClickListener(this);
//        initDetailListView();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //保存
            case R.id.eq_de_confirm:
//                addCompanyEquip
                break;
            //取消
            case R.id.eq_de_cancel:
                finish();
                break;
            //恢复初始
            case R.id.eq_de_start:
                k=0;
                dataBeanList.clear();
                listName.clear();
                isAllClose.clear();
                mAdapter.notifyDataSetChanged();
                num=0;
                nums=0;
                break;
            //导入先前配置
            case R.id.eq_de_old:
                break;
                default:
                    break;

        }
    }

    private void initView() {
        curHolder.eq_de_name.setText(entity.getEquipmentName());
        curHolder.edit_value.setText(entity.getEquipmentName());

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
                listName.add(entity.getEquipmentName());
            }
            lv3Adapter2.setmDataList(childist);
            lv3Adapter2.notifyDataSetChanged();
        } else {
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
        for (int i = 0; i < isAllClose.size(); i++) {
            if (isAllClose.get(i)){
                num++;
            }else {
                nums++;
            }
        }
        Log.i(TAG, "ClickDialogDetails---num: "+num);
        Log.i(TAG, "ClickDialogDetails---nums: "+nums);
        if(num!=nums){
            ToastUtil.showShort(this,"请关闭所有打开的详情,在进行新建");
            nums=0;
            num=0;
            return;
        }
        dialog.show();
    }

    //判断回来的数据是否为叶结点，展示不一样的视图
    public void IsVisibility(boolean flag){
        if(flag){
            curHolder.eq_de_list_feal_list.setVisibility(View.GONE);
        }else{
            curHolder.eq_de_list_feal_list.setVisibility(View.VISIBLE);
            curHolder.eq_de_list_lv4.setVisibility(View.GONE);
            curHolder.eq_de_list_lv4_details.setVisibility(View.GONE);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean bean) {
        if(bean.getKind().equals(EventKind.EVENT_COMPANY_APAPTER)){
            String message = bean.getMessage();
            boolean success = bean.getSuccess();
            List<EquipmentLayoutBean> beans = bean.getObj();
            String name = bean.getName();
            int equipmentId = Integer.parseInt(message);

            ClickDialogDetails(success,name,equipmentId,beans);
        }
        if(bean.getKind().equals(EventKind.EVENT_COMPANY_DATAILS)){
            isAllClose.add(bean.getSuccess());

        }
    }
    protected void ClickDialogDetails(boolean flag,String name,int equipmentId,List<EquipmentLayoutBean> beans) {
        List<EquipmentLayoutBean> beanList = new ArrayList<>();
        dataBean = new DataBean();
        dataBean.setType(0);
        dataBean.setID(j+"");
        j++;
        dataBean.setParentLeftTxt(name);
        dataBean.setParentRightTxt("详情");
//      //如果不是叶子节点
        if (!flag) {
            //查询4级设备
            List<EquipmentEntity> ccEntityList = getInstance().queryByPidAndCequipId(equipmentId);
            for (int i = 0; i <ccEntityList.size() ; i++) {
                EquipmentLayoutBean leafBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, ccEntityList.get(i).getEquipmentId());
                leafBean.setTitleName(name);
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
                beanList.add(leafBean);
                dataBean.setBean(beanList);
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
//              同级展示不好实现，所以添加到子条目里
                itemBean.setValue(beans.get(i).getValue());
                itemBean.setTitleName(name);
                beanList.add(itemBean);
                dataBean.setBean(beanList);
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
        mAdapter.setDataBeanList(dataBeanList);
        mAdapter.notifyDataSetChanged();
    }
    //四级设备   二级详情展示
    private void setData(){
        curHolder.eq_de_list_lv4_details.setLayoutManager(new WrapContentLinearLayoutManager(this));
        mAdapter = new RecyclerAdapter(this);
        curHolder.eq_de_list_lv4_details.setAdapter(mAdapter);
        //滚动监听
        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                curHolder.eq_de_list_lv4_details.scrollToPosition(pos);
            }
        });
    }
}