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

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonAppendListViewAdapter;
import cn.tties.maint.adapter.CommonSwipListViewAdapter;
import cn.tties.maint.adapter.EquipmentCheckDetailsAdapter;
import cn.tties.maint.adapter.EquipmentLayoutAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.dao.EquipmentItemDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.EquipmentItemEntity;
import cn.tties.maint.holder.EquipmentDetailsActivityHolder;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.util.PinYinUtils;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.view.EquipmentDetailsDialog;

import static cn.tties.maint.dao.EquipmentDao.getInstance;

/**
 * 管理档案 新建同类设备
 */
@ContentView(R.layout.activity_equipment_check_details)
public class EquipmentCheckDetailsActivity extends BaseFragmentActivity {
    private static final String TAG = "EquipmentCheckDetailsAc";
    private CommonListViewInterface eEntity;
    private EquipmentCheckDetailsActivity detailsActivity;
    @ViewInject(R.id.eq_de_layout_info)
    private LinearLayout layout_info;
    EquipmentDetailsActivityHolder curHolder;

    protected CommonSwipListViewAdapter lv3Adapter1;
    protected CommonSwipListViewAdapter lv3Adapter2;
    private EquipmentEntity entity;

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
    }

    private void initView() {
        curHolder.eq_de_name.setText(entity.getEquipmentName());
        curHolder.edit_value.setText(entity.getEquipmentName());
    }

    //四级请求数据展示
    protected void initLv3ListView() {
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
        //查询下级设备
        List<EquipmentEntity> childist = getInstance().queryByPid(entity.getEquipmentId());
        for (EquipmentEntity entity : childist) {
            switch (entity.getEquipmentType()) {
                case CABINET:
                    EquipmentLayoutBean bean = new EquipmentLayoutBean(EquipmentLayoutAdapter.RADIO, entity.getEquipmentId());
                    bean.setTextName("是否有柜体");
                    bean.setCheck(true);
                    beanList.add(bean);
//                            是否有柜体
                    curHolder.eq_de_yes.setChecked(true);
                    break;
                default:
                    EquipmentLayoutBean bean1 = new EquipmentLayoutBean(EquipmentLayoutAdapter.COUNT, entity.getEquipmentId());
                    if (entity.getUnitName() == null) {
                        bean1.setTextName(entity.getEquipmentName());
                    } else {
                        bean1.setTextName(entity.getEquipmentName() + "(" + entity.getUnitName() + ")");
                    }
                    beanList.add(bean1);
                    Log.i(TAG, "initLv3ListView: "+bean1.getTextName());
                    break;
            }
        }

        lv3Adapter2.setmDataList(childist);
        lv3Adapter2.notifyDataSetChanged();

    }

    protected void lv3DownListViewClick(final EquipmentEntity bean) {
        List<EquipmentLayoutBean> beanList = new ArrayList<>();
//      //如果不是叶子节点
        if (!bean.getIsLeafNode()) {
            if (bean.getEquipmentLevel() > 2) {
                //查询4级设备
                List<EquipmentEntity> ccEntityList = getInstance().queryByPidAndCequipId(bean.getEquipmentId());
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
        } else {//如果是叶子节点
            //查找设备零件
            List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(bean.getEquipmentId());
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
        }
        EquipmentDetailsDialog dialog=new EquipmentDetailsDialog(this,bean.getEquipmentName(), beanList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialog.show();
    }
}
