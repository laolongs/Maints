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
import cn.tties.maint.adapter.EquipmentLayoutAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.CreateBoxBody;
import cn.tties.maint.bean.CreateEquipFourParam;
import cn.tties.maint.bean.CreateEquipItemParam;
import cn.tties.maint.bean.CreateEquipParam;
import cn.tties.maint.bean.CreateEquipTwoParam;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.UpdateEquipParam;
import cn.tties.maint.bean.UpdateItemParam;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.dao.EquipmentItemDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.EquipmentItemEntity;
import cn.tties.maint.holder.EquipmentDetailHolder;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.CopyCompanyEquipmentParams;
import cn.tties.maint.httpclient.params.CreateComEquParams;
import cn.tties.maint.httpclient.params.DeleteComEquParams;
import cn.tties.maint.httpclient.params.QueryComEquParams;
import cn.tties.maint.httpclient.params.SelectCEquipAndItemByPidParams;
import cn.tties.maint.httpclient.params.SelectEquItemContentParams;
import cn.tties.maint.httpclient.params.UpdateComEquParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CEquipAndItemByPidResult;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.EquipmentInfoResult;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.secondLv.RecyclerAdapter;
import cn.tties.maint.util.CopyBean;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.view.ConfirmDialog;
import cn.tties.maint.widget.RecyclerManager;

import static cn.tties.maint.dao.EquipmentDao.getInstance;

/**
 * 管理档案
 */
@ContentView(R.layout.fragment_equipment_check)
public class EquipmentCheckFragment extends BaseFragment {
    private static final String TAG = "EquipmentCheckFragment";
    //详细界面 最大得布局 用于绑定id
    @ViewInject(R.id.layout_info)
    private LinearLayout layoutInfo;
    private List<DataBean> dataBeanList;
    private DataBean dataBean;
    private RecyclerAdapter mAdapter;
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

    private CompanyEquipmentResult curPidResult;

    public static EquipmentCheckFragment equipmentCheckFragmentInstance;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        equipmentCheckFragmentInstance = this;
        initListView();
    }
    private void initListView() {
        curHolder = new EquipmentDetailHolder(layoutInfo);
        initRootListView();
        initLv2ListView();
//        initLv3ListView();
        initDetailListView();
    }
    //有了公司电表Id后查询本体设备类型  二级数据
    @Override
    public void changeEleAccountNextStep() {
        super.changeEleAccountNextStep();
        List<EquipmentEntity> dataList = EquipmentDao.getInstance().queryByLevel(1);
        rootAdapter.setmDataList(dataList);
        rootAdapter.notifyDataSetChanged();
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
    //二级点击请求三级数据
    public void rootListViewClick(CommonListViewInterface bean) {
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
    //四级请求数据展示
//    protected void initLv3ListView() {
//        lv3Adapter1 = new CommonSwipListViewAdapter();
//        lv3ListView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        lv3ListView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
//        lv3Listener = new SwipeItemClickListener() {
//            @Override
//            public void onItemClick(View itemView, int position) {
//                CommonListViewInterface selBean = lv3Adapter1.getItem(position);
//                if (selBean.isChecked()) {
//                    return;
//                } else {//切换操作
//                    //设置选中
//                    lv3Adapter1.setChecked(selBean);
//                    lv3Adapter1.notifyDataSetChanged();
//                    lv3UpListViewClick(selBean);
//                }
//            }
//        };
//        lv3ListView.setSwipeItemClickListener(lv3Listener);
//
//        lv3Adapter2 = new CommonAppendListViewAdapter();
//        lv3ListView2.setLayoutManager(new LinearLayoutManager(getActivity()));
//        lv3ListView2.setSwipeItemClickListener(new SwipeItemClickListener() {
//            @Override
//            public void onItemClick(View itemView, int position) {
//                CommonListViewInterface selBean = lv3Adapter2.getItem(position);
//                lv3DownListViewClick(selBean);
//            }
//        });
//        setLv3ListViewMenu();
//        lv3ListView.setAdapter(lv3Adapter1);
//        lv3ListView2.setAdapter(lv3Adapter2);
//    }

//    protected void setLv2ListViewMenu() {
//        RecyclerManager.initConfig(lv2ListView, getActivity());
//        lv2ListView.setSwipeMenuItemClickListener(new EquipmentCheckFragment.Lv2MenuItemClickListener());
//    }
//
//    @Override
//    protected void setLv3ListViewMenu() {
//        RecyclerManager.initConfig(lv3ListView, getActivity());
//        lv3ListView.setSwipeMenuItemClickListener(new EquipmentCheckFragment.Lv3MenuItemClickListener());
//    }
//      标签
//    private void initTagListView() {
//        tagAdapter = new MTagAdapter();
//        tagAdapter.setDataList(new ArrayList<CompanyEquipmentResult>());
//        tagListView.setAdapter(tagAdapter);
//    }
    //用于四级得详情条目展示
    public void initDetailListView() {
        //7中布局的adapter
//        detailAdapter = new EquipmentLayoutAdapter(EquipmentCheckFragment.this.getActivity(), new ArrayList<EquipmentLayoutBean>());
//        detailListView.setAdapter(detailAdapter);
        //模拟数据
        dataBeanList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            dataBean = new DataBean();
            dataBean.setID(i+"");
            dataBean.setType(0);
            dataBean.setParentLeftTxt("父--"+i);
            dataBean.setParentRightTxt("父内容--"+i);
            dataBean.setChildLeftTxt("子--"+i);
            dataBean.setChildRightTxt("子内容--"+i);
            dataBean.setChildBean(dataBean);
            dataBeanList.add(dataBean);
        }
        setData();



    }
    //二级详情展示
    private void setData(){
        curHolder.recy_detail.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecyclerAdapter(getActivity(),dataBeanList);
        curHolder.recy_detail.setAdapter(mAdapter);
        //滚动监听
        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                curHolder.recy_detail.scrollToPosition(pos);
            }
        });
    }
//  点击三级数据请求四级数据
    protected void lv2UpListViewClick(CommonListViewInterface bean) {
        CompanyEquipmentResult selBean = (CompanyEquipmentResult) bean;
//        lv3Adapter1.clearCheck();
        selBean.setEquipmentEntity((EquipmentEntity)rootAdapter.getChecked());
        selBean.setEquipmentId(curPidResult.getEquipmentEntity().getEquipmentId());
        selBean.setPid(-1);
        selBean.setCompanyEquipmentId(-1);

        //详细页面 四级详情展示----------------------------------------------------
            showMuiltListView(selBean, lv3Adapter1, lv3Adapter2, false);
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
        startActivity(intent);
//        原是否复制  默认否
//        curHolder.radioButton.setChecked(true);
//        showAddCEquipLayout(selBean, lv2Adapter1, lv2Adapter2);
    }
//
//    @Override
//    protected void lv3UpListViewClick(CommonListViewInterface bean) {
//        CompanyEquipmentResult selBean = (CompanyEquipmentResult) bean;
//
//        CompanyEquipmentResult lv2Bean = (CompanyEquipmentResult) lv2Adapter1.getChecked();
//        curPidResult.setEquipmentEntity(lv2Bean.getEquipmentEntity());
//        curPidResult.setEquipmentId(lv2Bean.getEquipmentId());
//        curPidResult.setPid(lv2Bean.getPid());
//        curPidResult.setCompanyEquipmentId(lv2Bean.getCompanyEquipmentId());
//
//        showUpdateLayout(selBean, lv3Adapter1, lv3Adapter2);
//    }
//
//    @Override
//    protected void lv3DownListViewClick(CommonListViewInterface bean) {
//        lv3Adapter1.clearCheck();
//        CompanyEquipmentResult lv2Bean = (CompanyEquipmentResult) lv2Adapter1.getChecked();
//        curPidResult.setEquipmentEntity(lv2Bean.getEquipmentEntity());
//        curPidResult.setEquipmentId(lv2Bean.getEquipmentId());
//        curPidResult.setPid(lv2Bean.getPid());
//        curPidResult.setCompanyEquipmentId(lv2Bean.getCompanyEquipmentId());
//        EquipmentEntity selBean = (EquipmentEntity) bean;
//        curHolder.radioButton.setChecked(true);
//        showAddCEquipLayout(selBean, lv3Adapter1, lv3Adapter2);
//    }
//
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
//                layoutlv2all.setVisibility(View.VISIBLE);
                ObjectAnimator ra = ObjectAnimator.ofFloat(curHolder.lv2_img,"rotation", 0f, 180f);
                ra.setDuration(1000);
                ra.start();
//                new Thread(){
//                    @Override
//                    public void run() {
//                        try {
//                            sleep(1000);
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
                                    List<EquipmentEntity> list = EquipmentDao.getInstance().queryByPid(selBean.getEquipmentId());
                                    adapter2.setmDataList(list);
                                    adapter2.notifyDataSetChanged();
//                                }
//                            });
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
            }

        });
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
            }
        });
    }

//    private void refreshUpdateLayoutDetail(final CompanyEquipmentResult entity, final CommonSwipListViewAdapter adapter, final CommonAppendListViewAdapter adapter2) {
//        //查询该类型的公司设备
//        curHolder.updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (NoFastClickUtils.isFastClick()) {
//                    return;
//                }
//                updateCompanyEquip(entity, adapter, adapter2);
//            }
//        });
//        curHolder.delBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (NoFastClickUtils.isFastClick()) {
//                    return;
//                }
//                sendDelInfo(String.valueOf(entity.getCompanyEquipmentId()), adapter, adapter2);
//            }
//        });
//        curHolder.editName.setText(entity.getName());
//        if (!entity.getEquipmentEntity().getIsLeafNode()) {
//            SelectCEquipAndItemByPidParams params = new SelectCEquipAndItemByPidParams();
//            params.setPid(entity.getCompanyEquipmentId());
//            HttpClientSend.getInstance().send(params, new BaseStringCallback() {
//                        @Override
//                        public void onSuccess(String result) {
//                            try {
//                                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
//                                if (ret.getErrorCode() != 0) {
//                                    Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                                List<CEquipAndItemByPidResult> list = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<CEquipAndItemByPidResult>>() {
//                                }.getType());
//                                //重命名
//                                List<EquipmentLayoutBean> beanList = new ArrayList<>();
//
//                                //对应item 的Map
//                                Map<Integer, CEquipAndItemByPidResult> equipMap = new HashMap<>();
//                                for (CEquipAndItemByPidResult result1 : list) {
//                                    equipMap.put(result1.getCompanyEquipment().getEquipmentId(), result1);
//                                }
//                                //该设备下4级设备
//                                List<EquipmentEntity> equipList = EquipmentDao.getInstance().queryByPid(entity.getEquipmentId());
//
//                                //4级设备
//                                for (EquipmentEntity equipmentEntity : equipList) {
//                                    CompanyEquipmentResult result1 = new CompanyEquipmentResult();
//                                    List<EquipmentInfoResult> itemEntityList = new ArrayList<>();
//                                    if (equipMap.containsKey(equipmentEntity.getEquipmentId())) {
//                                        result1 = equipMap.get(equipmentEntity.getEquipmentId()).getCompanyEquipment();
//                                        itemEntityList = equipMap.get(equipmentEntity.getEquipmentId()).getSelectListEquInfo();
//                                    }
//                                    EquipmentLayoutBean leafBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, equipmentEntity.getEquipmentId(), result1.getCompanyEquipmentId());
//                                    leafBean.setTextName(equipmentEntity.getEquipmentName());
//                                    leafBean.setValue(result1.getName());
//                                    List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(equipmentEntity.getEquipmentId());
//                                    //对应item 的Map
//                                    Map<Integer, EquipmentInfoResult> itemMap = new HashMap<>();
//                                    for (EquipmentInfoResult infoEntity : itemEntityList) {
//                                        itemMap.put(infoEntity.getEquipmentItemId(), infoEntity);
//                                    }
//                                    //循环item列表
//                                    for (EquipmentItemEntity itemEntity : itemList) {
//                                        //如果item不为空放入对应值
//                                        EquipmentInfoResult info = itemMap.get(itemEntity.getEquipmentItemId());
//                                        EquipmentLayoutBean childBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemEntity.getEquipmentItemId(), info == null ? null : info.getEquipmentInfoId());
//                                        childBean.setInputType(itemEntity.getInputType());
//                                        if (itemEntity.getUnitName() == null) {
//                                            childBean.setTextName(itemEntity.getItemName());
//                                        } else {
//                                            childBean.setTextName(itemEntity.getItemName() + "(" + itemEntity.getUnitName() + ")");
//                                        }
//                                        childBean.setValue(setNullEdit(info == null ? itemEntity.getDefaultValue() : info.getContent()));
//                                        leafBean.getChildrenList().add(childBean);
//                                    }
//                                    beanList.add(leafBean);
//                                }
//                                detailAdapter.setDataList(beanList);
//                                detailAdapter.notifyDataSetChanged();
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            } finally {
//                            }
//                        }
//                    }
//            );
//        } else {
//            SelectEquItemContentParams params = new SelectEquItemContentParams();
//            params.setComEquId(entity.getCompanyEquipmentId());
//            HttpClientSend.getInstance().send(params, new BaseStringCallback() {
//                        @Override
//                        public void onSuccess(String result) {
//                            try {
//                                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
//                                if (ret.getErrorCode() != 0) {
//                                    Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                                } else {
//                                    List<EquipmentInfoResult> list = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<EquipmentInfoResult>>() {
//                                    }.getType());
//                                    HashMap<Integer, EquipmentInfoResult> equipmentInfoMap = new HashMap<>();
//                                    for (EquipmentInfoResult equipmentInfoEntity : list) {
//                                        equipmentInfoMap.put(equipmentInfoEntity.getEquipmentItemId(), equipmentInfoEntity);
//                                    }
//                                    List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(entity.getEquipmentId());
//                                    List<EquipmentLayoutBean> beanList = new ArrayList<>();
////                                    EquipmentLayoutBean reNameBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, entity.getEquipmentId(), entity.getCompanyEquipmentId());
////                                    reNameBean.setValue(entity.getName());
////                                    reNameBean.setTextName("重命名");
////                                    beanList.add(reNameBean);
//                                    for (EquipmentItemEntity itemEntity : itemList) {
//                                        EquipmentInfoResult entity = equipmentInfoMap.get(itemEntity.getEquipmentItemId());
//                                        if (null == entity) {
//                                            entity = new EquipmentInfoResult();
//                                        }
//                                        EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemEntity.getEquipmentItemId(), entity.getEquipmentInfoId());
//                                        itemBean.setInputType(itemEntity.getInputType());
//                                        if (itemEntity.getUnitName() == null) {
//                                            itemBean.setTextName(itemEntity.getItemName());
//                                        } else {
//                                            itemBean.setTextName(itemEntity.getItemName() + "(" + itemEntity.getUnitName() + ")");
//                                        }
//                                        itemBean.setValue(setNullEdit(entity.getContent()));
//                                        itemBean.setValue(setNullEdit(StringUtil.isEmpty(entity.getContent()) ? itemEntity.getDefaultValue() : entity.getContent()));
//                                        beanList.add(itemBean);
//                                    }
//                                    detailAdapter.setDataList(beanList);
//                                    detailAdapter.notifyDataSetChanged();
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            } finally {
//                            }
//                        }
//                    }
//            );
//        }
//    }

//    private void showAddCEquipLayout(final EquipmentEntity eEntity, final CommonSwipListViewAdapter adapter, final CommonAppendListViewAdapter adapter2) {
//        layoutInfo.setVisibility(View.VISIBLE);
//        //设置页面名称
//        curHolder.id = eEntity.getEquipmentId();
//        curHolder.textName.setText(eEntity.getEquipmentName());
//        curHolder.showAdd();
//        curHolder.setCopyShow(curHolder.radioButton);
//        //页面元素list
//        List<EquipmentLayoutBean> beanList = new ArrayList<>();
//
//        List<String> spinnerList = new ArrayList<>();
//        List<CompanyEquipmentResult> allList = getEntityByEquipId(eEntity.getEquipmentId(), adapter);
//        for (CompanyEquipmentResult entity : allList) {
//            spinnerList.add(entity.getName());
//            curHolder.copySpinner.setEnabled(true);
//        }
//        if (spinnerList.size() == 0) {
//            spinnerList.add("无可复制对象");
//            curHolder.copySpinner.setEnabled(false);
//        }
//        ArrayAdapter<String> copyAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, spinnerList);
//        curHolder.copySpinner.setAdapter(copyAdapter);
//        curHolder.editName.setText(eEntity.getEquipmentName());
//
//        //如果不是叶子节点
//        if (!eEntity.getIsLeafNode()) {
//            if (eEntity.getEquipmentLevel() > 2) {
//                //查询4级设备
//                List<EquipmentEntity> ccEntityList = getInstance().queryByPidAndCequipId(eEntity.getEquipmentId());
//                for (EquipmentEntity ccEntity : ccEntityList) {
//                    EquipmentLayoutBean leafBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.LEAF, ccEntity.getEquipmentId());
//                    leafBean.setTextName(ccEntity.getEquipmentName());
//                    List<EquipmentItemEntity> itemList = ccEntity.getItemList();
//                    for (EquipmentItemEntity itemEntity : itemList) {
//                        EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemEntity.getEquipmentItemId());
//                        itemBean.setInputType(itemEntity.getInputType());
//                        if (itemEntity.getUnitName() == null) {
//                            itemBean.setTextName(itemEntity.getItemName());
//                        } else {
//                            itemBean.setTextName(itemEntity.getItemName() + "(" + itemEntity.getUnitName() + ")");
//                        }
//                        itemBean.setValue(setNullEdit(itemEntity.getDefaultValue()));
//                        leafBean.getChildrenList().add(itemBean);
//                    }
//                    beanList.add(leafBean);
//                }
//            } else {
//                //查询下级设备
//                List<EquipmentEntity> childist = getInstance().queryByPid(eEntity.getEquipmentId());
//                for (EquipmentEntity entity : childist) {
//                    switch (entity.getEquipmentType()) {
//                        case CABINET:
//                            EquipmentLayoutBean bean = new EquipmentLayoutBean(EquipmentLayoutAdapter.RADIO, entity.getEquipmentId());
//                            bean.setTextName("是否有柜体");
//                            bean.setCheck(true);
//                            beanList.add(bean);
//                            break;
//                        default:
//                            EquipmentLayoutBean bean1 = new EquipmentLayoutBean(EquipmentLayoutAdapter.COUNT, entity.getEquipmentId());
//                            if (entity.getUnitName() == null) {
//                                bean1.setTextName(entity.getEquipmentName());
//                            } else {
//                                bean1.setTextName(entity.getEquipmentName() + "(" + entity.getUnitName() + ")");
//                            }
//                            beanList.add(bean1);
//                            break;
//                    }
//                }
//            }
//        } else {//如果是叶子节点
//            //查找设备零件
//            List<EquipmentItemEntity> itemList = EquipmentItemDao.getInstance().queryByEquipId(eEntity.getEquipmentId());
//            //生成零件页面
//            for (EquipmentItemEntity itemEntity : itemList) {
//                EquipmentLayoutBean itemBean = new EquipmentLayoutBean(EquipmentLayoutAdapter.EDITTEXT, itemEntity.getEquipmentItemId());
//                itemBean.setInputType(itemEntity.getInputType());
//                if (itemEntity.getUnitName() == null) {
//                    itemBean.setTextName(itemEntity.getItemName());
//                } else {
//                    itemBean.setTextName(itemEntity.getItemName() + "(" + itemEntity.getUnitName() + ")");
//                }
//                itemBean.setValue(setNullEdit(itemEntity.getDefaultValue()));
//                beanList.add(itemBean);
//            }
//        }
//        detailAdapter.setDataList(beanList);
//        detailAdapter.notifyDataSetChanged();
//        curHolder.addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (NoFastClickUtils.isFastClick()) {
//                    return;
//                }
//                addCompanyEquip(adapter, adapter2);
//            }
//        });
//    }

//    private void showUpdateLayout(final CompanyEquipmentResult checkEntity, final CommonSwipListViewAdapter adapter, final CommonAppendListViewAdapter adapter2) {
//        layoutInfo.setVisibility(View.VISIBLE);
        //标签点击
//        tagListView.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                tagAdapter.setCheckPostion(position);
//                refreshUpdateLayoutDetail(tagAdapter.getItem(position), adapter, adapter2);
//                return false;
//            }
//        });
//        if (checkEntity.getEquipmentEntity().getIsLeafNode() || checkEntity.getEquipmentEntity().getEquipmentLevel() > 2) {
//            curHolder.showUpdate();
//            List<CompanyEquipmentResult> list = getSameEquip(checkEntity.getEquipmentId(), adapter);
//            if (list != null) {
//                tagAdapter.setDataList(list);
//                tagAdapter.notifyDataChanged();
//            }
//            tagAdapter.setSelectedList(0);
//            refreshUpdateLayoutDetail(list.get(0), adapter, adapter2);
//        } else {
//            //查询该类型的公司设备
//            curHolder.showSimpleUpdate();
//            curHolder.editName.setText(checkEntity.getName());
//
//            detailAdapter.setDataList(new ArrayList());
//            detailAdapter.notifyDataSetChanged();
//            curHolder.updateBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //企业设备
//                    UpdateEquipParam uEquipParam = new UpdateEquipParam();
//                    uEquipParam.setComEquId(checkEntity.getCompanyEquipmentId());
//                    uEquipParam.setNewComEquName(curHolder.editName.getText().toString());
//                    List<UpdateEquipParam> list = new ArrayList<>();
//                    list.add(uEquipParam);
//                    sendUpdateInfo(list, adapter, adapter2);
//                }
//            });
//        }
//    }
//
//    private void delCompanyEquip(final CompanyEquipmentResult entity, final CommonSwipListViewAdapter adapter, final CommonAppendListViewAdapter adapter2) {
//        ConfirmDialog dialog = new ConfirmDialog(EquipmentCheckFragment.this.getActivity());
//        dialog.loadDialog("是否删除设备？", null, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                List<String> list = new ArrayList<>();
//                //如果是合并显示的设备
//                if ((entity.getEquipmentEntity().getIsLeafNode() || entity.getEquipmentEntity().getEquipmentLevel() > 2)) {
//                    for (CompanyEquipmentResult entity1 : getSameEquip(entity.getEquipmentId(), adapter)) {
//                        list.add(String.valueOf(entity1.getCompanyEquipmentId()));
//                    }
//                } else {
//                    list.add(String.valueOf(entity.getCompanyEquipmentId()));
//                }
//                sendDelInfo(StringUtil.listToString(list), adapter, adapter2);
//            }
//        });
//    }
//
//    private void addCompanyEquip(CommonSwipListViewAdapter adapter, final CommonAppendListViewAdapter adapter2) {
//        List<EquipmentLayoutBean> beanList = detailAdapter.getDataList();
//        if (StringUtil.isEmpty(curHolder.editName.getText().toString())) {
//            Toast.makeText(x.app(), "名称不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (curHolder.copyFlag) {
//            String str = (String) curHolder.copySpinner.getAdapter().getItem(0);
//            if (str.equals("无可复制对象")) {
//                Toast.makeText(x.app(), "无可复制对象", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            List<CompanyEquipmentResult> list = getEntityByEquipId(curHolder.id, adapter);
//            CompanyEquipmentResult copyEntity = list.get(curHolder.copySpinner.getSelectedItemPosition());
//            sendCopyAddInfo(curHolder.editName.getText().toString(), copyEntity.getCompanyEquipmentId(), adapter, adapter2);
//        } else {
//            //企业设备下级设备
//            List<CreateEquipItemParam> itemList = new ArrayList<>();
//            List<CreateEquipTwoParam> arrTwoList = new ArrayList<>();
//            List<CreateEquipFourParam> arrFourList = new ArrayList<>();
//
//            //新建企业设备
//            CreateEquipParam cEquipParam = new CreateEquipParam();
//            cEquipParam.setEquipmentId(curHolder.id);
//            cEquipParam.setEleAccountId(curEleId);
//            cEquipParam.setComEquName(curHolder.editName.getText().toString());
//            cEquipParam.setPid(curPidResult == null ? -1 : curPidResult.getCompanyEquipmentId());
//
//            for (int i = 0; i < beanList.size(); i++) {
//                EquipmentLayoutBean bean = beanList.get(i);
//                //下级设备（对应后台 添加二级企业设备时添加三级企业设备）
//                switch (bean.getType()) {
//                    case EquipmentLayoutAdapter.COUNT:
//                        int count = bean.getValue() == null ? 0 : Integer.valueOf(bean.getValue());
//                        if (count > 0) {
//                            //根据count创建2级设备
//                            CreateEquipTwoParam param = new CreateEquipTwoParam();
//                            param.setEquName(bean.getTextName());
//                            param.setEquId(bean.getItemId());
//                            param.setEquCount(count);
//                            arrTwoList.add(param);
//                        }
//                        break;
//                    case EquipmentLayoutAdapter.RADIO:
//                        if (bean.getCheck() == null || bean.getCheck()) {
//                            CreateBoxBody boxBody = new CreateBoxBody();
//                            boxBody.setBoxBodyId(bean.getItemId());
//                            cEquipParam.setBoxBody(boxBody);
//                        }
//                        break;
//                    case EquipmentLayoutAdapter.EDITTEXT:
//                        CreateEquipItemParam itemParam = new CreateEquipItemParam();
//                        itemParam.setEquItemId(bean.getItemId());
//                        itemParam.setEquItemContent(bean.getValue());
//                        itemList.add(itemParam);
//                        break;
//                    case EquipmentLayoutAdapter.LEAF:
//                        CreateEquipFourParam fourParam = new CreateEquipFourParam();
//                        fourParam.setEquIdFour(bean.getItemId());
//                        List<CreateEquipItemParam> fourItemList = new ArrayList<>();
//                        for (EquipmentLayoutBean itemBean : bean.getChildrenList()) {
//                            CreateEquipItemParam itemParams = new CreateEquipItemParam();
//                            itemParams.setEquItemId(itemBean.getItemId());
//                            itemParams.setEquItemContent(itemBean.getValue());
//                            fourItemList.add(itemParams);
//                        }
//                        fourParam.setEquItemFourArr(fourItemList);
//                        arrFourList.add(fourParam);
//                        break;
//                }
//                cEquipParam.setEquNameArrTwo(arrTwoList);
//                cEquipParam.setComItemThree(itemList);
//                cEquipParam.setEquArrFour(arrFourList);
//            }
//            sendAddInfo(cEquipParam, adapter, adapter2);
//        }
//    }
//
//    private void updateCompanyEquip(CompanyEquipmentResult checkEntity, final CommonSwipListViewAdapter adapter, final CommonAppendListViewAdapter adapter2) {
//        List<EquipmentLayoutBean> beanList = detailAdapter.getDataList();
//        List<UpdateEquipParam> updateList = new ArrayList<>();
//        UpdateEquipParam updateEquipParam = new UpdateEquipParam();
//        updateEquipParam.setComEquId(checkEntity.getCompanyEquipmentId());
//        updateEquipParam.setEquipId(checkEntity.getEquipmentId());
//        updateEquipParam.setNewComEquName(curHolder.editName.getText().toString());
//        updateEquipParam.setPid(checkEntity.getPid());
//        updateList.add(updateEquipParam);
//
//        //企业设备
//        if (!checkEntity.getEquipmentEntity().getIsLeafNode()) {
//            //企业设备下级设备
//            for (int i = 0; i < beanList.size(); i++) {
//                EquipmentLayoutBean leafBean = beanList.get(i);
//
//                UpdateEquipParam param = new UpdateEquipParam();
//                param.setComEquId(leafBean.getDbId());
//                param.setNewComEquName(leafBean.getValue());
//                param.setEquipId(leafBean.getItemId());
//                param.setPid(checkEntity.getCompanyEquipmentId());
//                List<UpdateItemParam> itemList = new ArrayList<>();
//                for (EquipmentLayoutBean itemBean : leafBean.getChildrenList()) {
//                    UpdateItemParam itemParam = new UpdateItemParam();
//                    itemParam.setEquipmentItemId(itemBean.getItemId());
//                    itemParam.setEquipmentInfoId(itemBean.getDbId());
//                    itemParam.setNewEquipmentInfo(itemBean.getValue());
//                    itemList.add(itemParam);
//                }
//                param.setUpdateEquItemInfo(itemList);
//                updateList.add(param);
//            }
//        } else {
//            //企业设备下级设备
//            List<UpdateItemParam> itemList = new ArrayList<>();
//            for (int i = 0; i < beanList.size(); i++) {
//                EquipmentLayoutBean itemBean = beanList.get(i);
//                UpdateItemParam itemParam = new UpdateItemParam();
//                itemParam.setEquipmentItemId(itemBean.getItemId());
//                itemParam.setEquipmentInfoId(itemBean.getDbId());
//                itemParam.setNewEquipmentInfo(itemBean.getValue());
//                itemList.add(itemParam);
//            }
//            updateEquipParam.setUpdateEquItemInfo(itemList);
//        }
//        sendUpdateInfo(updateList, adapter, adapter2);
//    }
//
//    private void sendAddInfo(final CreateEquipParam cEquipParam, final CommonSwipListViewAdapter adapter, final CommonAppendListViewAdapter adapter2) {
//        CreateComEquParams params = new CreateComEquParams();
//        params.setParamJson(JsonUtils.getJsonObjectStr(cEquipParam));
//        HttpClientSend.getInstance().send(params, new BaseAlertCallback("新增设备成功","新增设备失败") {
//            @Override
//            public void onSuccessed(String result) {
//                try {
//                    showMuiltListView(curPidResult, adapter, adapter2, false);
//                    // 更新电房管理中的未分配设备
//                    EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleAccountComEqu();
//                    //重置新增界面
//                    showAddCEquipLayout(EquipmentDao.getInstance().queryById(curHolder.id), adapter, adapter2);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                }
//            }
//        });
//    }
//
//    private void sendCopyAddInfo(String name, Integer cEquipId, final CommonSwipListViewAdapter adapter, final CommonAppendListViewAdapter adapter2) {
//        CopyCompanyEquipmentParams params = new CopyCompanyEquipmentParams();
//        params.setCompanyEquipmentName(name);
//        params.setCompanyEquipmentId(cEquipId);
//        HttpClientSend.getInstance().send(params, new BaseAlertCallback("新增设备成功","新增设备失败") {
//            @Override
//            public void onSuccessed(String result) {
//                showMuiltListView(curPidResult, adapter, adapter2, false);
//                // 更新电房管理中的未分配设备
//                EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleAccountComEqu();
//                //重置新增界面
//                showAddCEquipLayout(EquipmentDao.getInstance().queryById(curHolder.id), adapter, adapter2);
//            }
//        });
//    }
//
//    private void sendUpdateInfo(List<UpdateEquipParam> updateList, final CommonSwipListViewAdapter adapter, final CommonAppendListViewAdapter adapter2) {
//        UpdateComEquParams params = new UpdateComEquParams();
//        params.setParamJson(JsonUtils.getJsonArrayStr(updateList));
//        HttpClientSend.getInstance().send(params, new BaseAlertCallback("更新设备成功","更新设备失败") {
//            @Override
//            public void onSuccessed(String result) {
//                try {
//                    showMuiltListView(curPidResult, adapter, adapter2, true);
//                    EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleAccountComEqu();
//                    EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleRoomComEqu();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                }
//            }
//        });
//    }
//
//    private void sendDelInfo(final String cEqupIds, final CommonSwipListViewAdapter adapter, final CommonAppendListViewAdapter adapter2) {
//        DeleteComEquParams params = new DeleteComEquParams();
//        params.setComEquIds(cEqupIds);
//        HttpClientSend.getInstance().send(params, new BaseAlertCallback("删除设备成功","删除设备失败") {
//            @Override
//            public void onSuccessed(String result) {
//                try {
//                    showMuiltListView(curPidResult, adapter, adapter2, true);
//                    EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleAccountComEqu();
//                    EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleRoomComEqu();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                }
//            }
//        });
//    }
//
//    public class Lv2MenuItemClickListener implements SwipeMenuItemClickListener {
//
//        public void onItemClick(SwipeMenuBridge menuBridge) {
//            menuBridge.closeMenu();
//            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
//            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
//            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
//
//            EquipmentEntity entity1 = (EquipmentEntity)rootAdapter.getChecked();
//            CompanyEquipmentResult ceBean = new CompanyEquipmentResult();
//            ceBean.setEquipmentEntity(entity1);
//            ceBean.setEquipmentId(entity1.getEquipmentId());
//            ceBean.setPid(-1);
//            ceBean.setCompanyEquipmentId(-1);
//            curPidResult = ceBean;
//
//            final CompanyEquipmentResult entity = (CompanyEquipmentResult)lv2Adapter1.getItem(adapterPosition);
//            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//                lv2Adapter1.setChecked(entity);
//                lv2Adapter1.notifyDataSetChanged();
//                showUpdateLayout(entity, lv2Adapter1, lv2Adapter2);
//            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                delCompanyEquip(entity, lv2Adapter1, lv2Adapter2);
//            }
//        }
//    }
//
//    public class Lv3MenuItemClickListener implements SwipeMenuItemClickListener {
//
//        public void onItemClick(SwipeMenuBridge menuBridge) {
//            menuBridge.closeMenu();
//            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
//            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
//            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
//            final CompanyEquipmentResult entity = (CompanyEquipmentResult)lv3Adapter1.getItem(adapterPosition);
//            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//                lv3Adapter1.setChecked(entity);
//                lv3Adapter1.notifyDataSetChanged();
//                showUpdateLayout(entity, lv3Adapter1, lv3Adapter2);
//            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                delCompanyEquip(entity, lv3Adapter1, lv3Adapter2);
//            }
//        }
//    }
    //标签适配器
//    class MTagAdapter extends TagAdapter<CompanyEquipmentResult> {
//
//        int checkPostion = 0;
//
//        private List<CompanyEquipmentResult> dataList;
//
//        public MTagAdapter() {
//            super(new ArrayList<CompanyEquipmentResult>());
//        }
//
//        public void setDataList(List<CompanyEquipmentResult> dataList) {
//            this.dataList = dataList;
//            if (dataList.size() > checkPostion) {
//                this.setSelectedList(checkPostion);
//            } else {
//                setCheckPostion(dataList.size() - 1);
//            }
//        }
//
//        public void setCheckPostion(int postion) {
//            postion = postion < 1 ? 0 : postion;
//            checkPostion = postion;
//            this.setSelectedList(checkPostion);
//        }
//
//        @Override
//        public int getCount() {
//            return dataList == null ? 0 : dataList.size();
//        }
//
//        @Override
//        public CompanyEquipmentResult getItem(int position) {
//            return dataList.get(position);
//        }
//
//        @Override
//        public View getView(FlowLayout parent, int position, CompanyEquipmentResult s) {
//            CompanyEquipmentResult result = getItem(position);
//            final LayoutInflater mInflater = LayoutInflater.from(getActivity());
//            TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_text, tagListView, false);
//            tv.setText(result.getName());
//            return tv;
//        }
//    }
}
