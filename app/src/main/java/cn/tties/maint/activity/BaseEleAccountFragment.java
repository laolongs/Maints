package cn.tties.maint.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonAppendListViewAdapter;
import cn.tties.maint.adapter.CommonSwipListViewAdapter;
import cn.tties.maint.adapter.EleAccountAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.EleAccountResult;
import cn.tties.maint.util.ACache;
import cn.tties.maint.util.CopyBean;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.view.BaseCustomDialog;

/**
 * 公司及用电户号的展示和切换.
 * Created by Justin on 2018/1/8.
 */
public abstract class BaseEleAccountFragment extends BaseFragment {

    protected int mChangeCompanyId = -1;

    protected Integer curEleId;

    protected String curEleNo;

    protected CompanyResult curCompany;

    protected CommonSwipListViewAdapter rootAdapter;

    protected CommonSwipListViewAdapter lv2Adapter1;

    protected CommonAppendListViewAdapter lv2Adapter2;

    protected CommonSwipListViewAdapter lv3Adapter1;

    protected CommonAppendListViewAdapter lv3Adapter2;

    @ViewInject(R.id.listview_lv1_2)
    protected SwipeMenuRecyclerView rootListView;

    @ViewInject(R.id.listview_lv2)
    protected SwipeMenuRecyclerView lv2ListView;

    @ViewInject(R.id.listview_lv2_2)
    protected SwipeMenuRecyclerView lv2ListView2;

    @ViewInject(R.id.listview_lv3)
    protected SwipeMenuRecyclerView lv3ListView;

    @ViewInject(R.id.listview_lv3_2)
    protected SwipeMenuRecyclerView lv3ListView2;

    @ViewInject(R.id.layout_lv1)
    protected LinearLayout lv1Layout;

    @ViewInject(R.id.layout_lv2)
    protected LinearLayout lv2Layout;

    @ViewInject(R.id.layout_lv3)
    protected LinearLayout lv3Layout;

    @ViewInject(R.id.layout_info)
    protected LinearLayout infoLayout;

    @ViewInject(R.id.lv2_img)
    protected ImageView lv2Iimg;

    @ViewInject(R.id.lv2_tv)
    protected TextView lv2Tv;

    @ViewInject(R.id.layout_lv2_all)
    protected LinearLayout layoutlv2all;

    protected SwipeItemClickListener rootListener;

    protected SwipeItemClickListener lv2Listener;

    protected SwipeItemClickListener lv3Listener;

    OnimgClickListener onimgClickListener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        curEleId = null;

        initRootListView();
        initLv2ListView();
        initLv3ListView();

    }
    public void setOnimgClickListener(OnimgClickListener onimgClickListener){
        this.onimgClickListener=onimgClickListener;
    }

    protected void intoSelectedCompany(int companyId) {
        this.mChangeCompanyId = companyId;
    }


    protected Integer getCurEleId() {
        return curEleId;
    }

    protected void  setCurEleId(int curEleId) {
        this.curEleId=curEleId;
    }
    protected String getCurEleNo() {
        return curEleNo;
    }

    protected void  setCurEleNo(String curEleNo) {
        this.curEleNo=curEleNo;
    }
    protected abstract void changeEleAccountNextStep();

    protected void initRootListView() {
        rootAdapter = new CommonSwipListViewAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rootListView.setLayoutManager(manager);
        rootListView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
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
        rootListView.setSwipeItemClickListener(rootListener);
        rootListView.setAdapter(rootAdapter);
        //img点击监听
        lv2Iimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onimgClickListener.OnimgClick(view);
            }
        });
    }

    protected void initLv2ListView() {
        lv2Adapter1 = new CommonSwipListViewAdapter();
        lv2ListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv2ListView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
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
        lv2ListView.setSwipeItemClickListener(lv2Listener);

        lv2Adapter2 = new CommonAppendListViewAdapter();
        lv2ListView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv2ListView2.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                CommonListViewInterface selBean = lv2Adapter2.getItem(position);
                lv2DownListViewClick(selBean);
            }
        });
        setLv2ListViewMenu();
        lv2ListView.setAdapter(lv2Adapter1);
        lv2ListView2.setAdapter(lv2Adapter2);
    }

    protected void setLv2ListViewMenu() {

    }


    protected void initLv3ListView() {
        lv3Adapter1 = new CommonSwipListViewAdapter();
        lv3ListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv3ListView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
        lv3Listener = new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                CommonListViewInterface selBean = lv3Adapter1.getItem(position);
                if (selBean.isChecked()) {
                    return;
                } else {//切换操作
                    //设置选中
                    lv3Adapter1.setChecked(selBean);
                    lv3Adapter1.notifyDataSetChanged();
                    lv3UpListViewClick(selBean);
                }
            }
        };
        lv3ListView.setSwipeItemClickListener(lv3Listener);

        lv3Adapter2 = new CommonAppendListViewAdapter();
        lv3ListView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv3ListView2.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                CommonListViewInterface selBean = lv3Adapter2.getItem(position);
                lv3DownListViewClick(selBean);
            }
        });
        setLv3ListViewMenu();
        lv3ListView.setAdapter(lv3Adapter1);
        lv3ListView2.setAdapter(lv3Adapter2);
    }

    protected void setLv3ListViewMenu() {

    }

    protected void rootListViewClick(CommonListViewInterface bean) {

    }

    protected void lv2UpListViewClick(CommonListViewInterface bean) {

    }

    protected void lv2DownListViewClick(CommonListViewInterface bean) {

    }

    protected void lv3UpListViewClick(CommonListViewInterface bean) {

    }

    protected void lv3DownListViewClick(CommonListViewInterface bean) {

    }
    public interface OnimgClickListener{
        public void OnimgClick(View view);
    }

    protected void setRootClick(int pos) {
        rootListener.onItemClick(rootListView, pos);
    }


    protected void setLv2Click(int pos) {
        lv2Listener.onItemClick(lv2ListView, pos);
    }

    protected void setLv3Click(int pos) {
        lv3Listener.onItemClick(lv3ListView, pos);
    }

    protected List<CompanyEquipmentResult> getSameEquip(int equipId, CommonSwipListViewAdapter adapter) {
        Map<Integer, List<CompanyEquipmentResult>> map = adapter.getMap();
        List<CompanyEquipmentResult> list = new ArrayList<CompanyEquipmentResult>();
        if (map.containsKey(equipId)) {
            for (CompanyEquipmentResult entity : map.get(equipId)) {
                if (entity.getEquipmentId() == equipId) {
                    list.add(entity);
                }
            }
        }
        return list;
    }

    protected void setMergeDataList(List<CompanyEquipmentResult> oldDataList, CommonSwipListViewAdapter adapter) {
        List<CompanyEquipmentResult> mDataList = new ArrayList<>();
        Map<Integer, List<CompanyEquipmentResult>> orgMap = new HashMap<>();
        for (CompanyEquipmentResult entity : oldDataList) {
            //3级以上设备合并显示
            if (entity.getEquipmentEntity().getIsLeafNode() || entity.getEquipmentEntity().getEquipmentLevel() > 2) {
                if (!orgMap.containsKey(entity.getEquipmentId())) {
                    //合并的entity
                    CompanyEquipmentResult newEntity = new CompanyEquipmentResult();
                    CopyBean.copyProperties(entity, newEntity);
                    newEntity.setCompanyEquipmentId(entity.getEquipmentId());
                    newEntity.setName(entity.getEquipmentEntity().getEquipmentName());
                    mDataList.add(newEntity);
                    //原始list
                    List<CompanyEquipmentResult> list = new ArrayList<>();
                    list.add(entity);
                    orgMap.put(newEntity.getEquipmentId(), list);
                } else {
                    List<CompanyEquipmentResult> list = orgMap.get(entity.getEquipmentId());
                    list.add(entity);
                }

            } else { //非叶子节点或3级以下设备 单体显示
                mDataList.add(entity);
            }
        }
        CompanyEquipmentResult oldCheck = (CompanyEquipmentResult) adapter.getChecked();
        if (oldCheck != null) {
            for (CompanyEquipmentResult newRes : mDataList) {

                if (oldCheck.getCompanyEquipmentId().equals(newRes.getCompanyEquipmentId())) {
                    newRes.setChecked(true);
                }
            }
        }
        if(oldDataList.size()==0){
            layoutlv2all.setVisibility(View.GONE);
        }else{
            layoutlv2all.setVisibility(View.VISIBLE);
        }

        adapter.setmDataList(mDataList);
        adapter.setMap(orgMap);
    }

    public List<CompanyEquipmentResult> getEntityByEquipId(Integer equipId, CommonSwipListViewAdapter adapter) {
        List<CompanyEquipmentResult> list = (List<CompanyEquipmentResult>) adapter.getMap().get(equipId);
        if (list == null || list.size() == 0) {
            list = new ArrayList<CompanyEquipmentResult>();
            List<CompanyEquipmentResult> alist = (List<CompanyEquipmentResult>) adapter.getMList();
            for (CompanyEquipmentResult entity : alist) {
                if (entity.getEquipmentId() == equipId) {
                    list.add(entity);
                }
            }
        }
        return list;
    }

    protected void hideLayout(int level) {
        lv2Layout.setVisibility(View.VISIBLE);
        lv3Layout.setVisibility(View.VISIBLE);
        lv1Layout.setVisibility(View.VISIBLE);
        switch (level) {
//            case -1:
//                lv1Layout.setVisibility(View.GONE);
//            case 0:
//                lv2Layout.setVisibility(View.GONE);
//            case 1:
//                lv3Layout.setVisibility(View.GONE);
//            case 2:
//                infoLayout.setVisibility(View.GONE);
//                break;
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(EventBusBean bean) {
////        if (bean.getKind().equals(EventKind.EVENT_COMPANYLIST)) {
////            initCompanyList();
////        }
//        if (bean.getKind().equals(EventKind.EVENT_COMPANY_CHANGEID)) {
//            //电号ID
//            int result = bean.getObj();
//            //总电量
//            String message = bean.getMessage();
//            this.setCurEleId(result);
//            this.setCurEleNo(message);
//            ToastUtil.showShort(getActivity(),""+result);
//            changeEleAccountNextStep();
////            getEleAccountList();
//        }
//    }
}
