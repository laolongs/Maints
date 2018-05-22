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
import cn.tties.maint.widget.WrapContentLinearLayoutManager;

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

    //用于四级得详情条目展示
    public void initDetailListView() {
        //模拟数据
        dataBeanList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            dataBean = new DataBean();
            dataBean.setID(i+"");
            dataBean.setType(0);
            dataBean.setParentLeftTxt("开关装置");
            dataBean.setParentRightTxt("详情");
//            dataBean.setBean();
//            dataBean.setChildLeftTxt("子--"+i);
//            dataBean.setChildRightTxt("子内容--"+i);
            dataBean.setChildBean(dataBean);
            dataBeanList.add(dataBean);
        }
        setData();
    }
    //二级详情展示
    private void setData(){
        curHolder.recy_detail.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
        mAdapter = new RecyclerAdapter(getActivity());
        mAdapter.setDataBeanList(dataBeanList);
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
                curHolder.layout_lv2_all.setVisibility(View.VISIBLE);
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
}
