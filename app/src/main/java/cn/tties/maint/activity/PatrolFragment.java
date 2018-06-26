package cn.tties.maint.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonSwipListViewAdapter;
import cn.tties.maint.adapter.PatrolLayoutAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.dao.PatrolItemDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.PatrolItemEntity;
import cn.tties.maint.holder.PatrolInfoHolder;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.SelectEleRoomParams;
import cn.tties.maint.httpclient.params.SelectEquipmentByEleRoomParams;
import cn.tties.maint.httpclient.params.SelectPatrolItemParams;
import cn.tties.maint.httpclient.params.SelectPatrolOrderParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.EleRoomResult;
import cn.tties.maint.httpclient.result.OrderResult;
import cn.tties.maint.httpclient.result.PatrolResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.view.ConfirmDialog;
import cn.tties.maint.view.InfoDialog;
import cn.tties.maint.view.Patrol_NewQuestionDialog;
import cn.tties.maint.widget.PtrListViewOnScrollListener;
import cn.tties.maint.widget.RecyclerManager;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 电房巡视
 * Created by Justin on 2018/1/9.
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_patrol)
public class PatrolFragment extends BaseFragment {
    private static final String TAG = "PatrolFragment";
    public static PatrolFragment patrolFragmentInstance;
    //详细界面 最大得布局 用于绑定id
    @ViewInject(R.id.patrol_info)
    private LinearLayout layoutInfo;
    @ViewInject(R.id.ptrlayout_order)
    public PtrClassicFrameLayout orderPtrlayout;
    private PtrListViewOnScrollListener mPtrListViewOnScrollListener;
    private PatrolLayoutAdapter patrolItemAdapter;
    private ConfirmDialog dialog;
    private CompanyResult curCompany;
    private Integer curEleId;
    private String curEleNo;
    private PatrolInfoHolder curhodler;
    CommonSwipListViewAdapter rootAdapter;
    protected SwipeItemClickListener rootListener;
    protected CommonSwipListViewAdapter lv2Adapter;
    protected SwipeItemClickListener lv2Listener;
    private List<PatrolItemEntity> showItemEntities;
    private List<PatrolResult> recordResult;
    private Patrol_NewQuestionDialog dialognew;
    private CompanyEquipmentResult result;
//    int orderworkid=223;
    int numFlag=0;//0 代表新建问题， 1 代表添加问题，2 代表有温度的问题
    int workOrderId;
    private String address;
    CommonListViewInterface selBeans;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        patrolFragmentInstance = this;
    }
    @SuppressLint("ValidFragment")
    public PatrolFragment(Integer curEleId,String curEleNo, CompanyResult curCompany,int workOrderId){
        this.curEleId=curEleId;
        this.curEleNo=curEleNo;
        this.curCompany=curCompany;
        this.workOrderId=workOrderId;
        Log.i(TAG, "showPatrolItem:工单Id "+workOrderId);
    }
    @Override
    public void changeEleAccountNextSteps(Integer curEleId, String curEleNo, CompanyResult curCompany) {
        this.curEleId=curEleId;
        this.curEleNo=curEleNo;
        this.curCompany=curCompany;
    }

    //当前电房
    protected void initRootListView() {
        rootAdapter = new CommonSwipListViewAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        curhodler.listview_root.setLayoutManager(manager);
        curhodler.listview_root.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
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
        curhodler.listview_root.setSwipeItemClickListener(rootListener);
        curhodler.listview_root.setAdapter(rootAdapter);
    }
    //二级  获取采集点列表.
    public void initLv2ListView() {
        lv2Adapter = new CommonSwipListViewAdapter();
        curhodler.listview_lv2.setLayoutManager(new LinearLayoutManager(getActivity()));
        curhodler.listview_lv2.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
        lv2Listener = new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                CommonListViewInterface selBean = lv2Adapter.getItem(position);
                if (selBean.isChecked()) {
                    return;
                } else {//切换操作
                    //设置选中
                    lv2Adapter.setChecked(selBean);
                    lv2Adapter.notifyDataSetChanged();
                    selBeans=selBean;
                    lv2UpListViewClick(selBean);
                }
            }
        };
        curhodler.listview_lv2.setSwipeItemClickListener(lv2Listener);
        curhodler.listview_lv2.setAdapter(lv2Adapter);

    }
    /**
     * 初始化界面.
     */
    private void initView() {
        curhodler = new PatrolInfoHolder(layoutInfo);
        dialog = new ConfirmDialog(PatrolFragment.this.getActivity());
        showItemEntities = new ArrayList<>();
        initRootListView();
        initLv2ListView();
        // 初始化巡视项
        initDetailListView();
        getPatrolTypeList();
//        getWorkOrderId();
        initPullRefresh();
        //添加额外新问题
        curhodler.patrol_addquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                numFlag=0;
                dialognew.setNumFlag(numFlag);
                dialognew.setContentView();
                dialognew.loading();
            }
        });
    }
    //查询二级企业设备 查询当前电房下属设备项
    protected void rootListViewClick(CommonListViewInterface bean) {
        lv2Adapter.clearCheck();
        EleRoomResult selectEleRoomResult = (EleRoomResult) bean;
        SelectEquipmentByEleRoomParams params = new SelectEquipmentByEleRoomParams();
        params.setEleAccountId(curEleId);
        params.setRoomId(selectEleRoomResult.getRoomId());
        Log.i(TAG, "rootListViewClick: "+selectEleRoomResult.getRoomId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CompanyEquipmentResult> companyEquipmentList = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<CompanyEquipmentResult>>() {
                        }.getType());
                if(companyEquipmentList.size()>0){
                    showHoulder(4);
                    lv2Adapter.setmDataList(companyEquipmentList);
                    lv2Adapter.notifyDataSetChanged();
                }else{
                    showHoulder(3);
                }

            }
        });
    }
    //二级请求后天及本地数据库数据
    protected void lv2UpListViewClick(CommonListViewInterface bean) {
        showItemEntities.clear();
        result = (CompanyEquipmentResult) bean;
        String position = result.getPosition();
        //地址
        address = StringUtil.substring(position, 1, position.length()-1);
        Log.i(TAG, "lv2UpListViewClick: "+ result.getEquipmentId());
        queryPatrolList();
    }
    public void queryPatrolList(){
        showItemEntities.clear();
        SelectPatrolItemParams params = new SelectPatrolItemParams();
        params.setEleAccountId(curEleId);
        params.setWorkOrderId(workOrderId);
        params.setCompanyEquipmentId(result.getCompanyEquipmentId());
        params.setRoomId(result.getRoomId());
        dialognew = new Patrol_NewQuestionDialog(PatrolFragment.this,result,workOrderId,curEleId,curCompany.getCompanyId(),address,curCompany.getCompanyName());
        patrolItemAdapter.setData(result.getCompanyEquipmentId(),curEleId,result.getRoomId(),workOrderId,curCompany.getCompanyName());
        Log.i(TAG, "lv2UpListViewClick: curEleId"+curEleId);
        Log.i(TAG, "lv2UpListViewClick: orderId"+workOrderId);
        Log.i(TAG, "lv2UpListViewClick: result.getCompanyEquipmentId()"+ result.getCompanyEquipmentId());
        Log.i(TAG, "lv2UpListViewClick: result.getRoomId()"+ result.getRoomId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {

            @Override
            public void onSuccess(String resultStr) {
                BaseResult ret = JsonUtils.deserialize(resultStr, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                recordResult = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<PatrolResult>>() {
                        }.getType());
                showPatrolItem(recordResult, result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });
    }
    /**
     * 初始化巡视项.
     */
    private void initDetailListView() {
        patrolItemAdapter = new PatrolLayoutAdapter(new ArrayList<PatrolItemEntity>(),PatrolFragment.this);
        this.mPtrListViewOnScrollListener = new PtrListViewOnScrollListener(this.orderPtrlayout, true, false);
        curhodler.listview_lv3.setAdapter(patrolItemAdapter);
        curhodler.listview_lv3.setOnScrollListener(this.mPtrListViewOnScrollListener);

        //有问题回调
        patrolItemAdapter.setOnClick(new PatrolLayoutAdapter.OnClick() {
            @Override
            public void OnClickListenter(boolean flag,int patrolItemId,int faultType) {
                numFlag=1;
                dialognew.setNumFlag(numFlag);
                dialognew.setpatrolItemIdAndfaultType(patrolItemId,faultType);
                dialognew.setData(flag);
                dialognew.setContentView();
                dialognew.loading();
            }
        });
    }
    /**
     * 初始化刷新.
     */
    private void initPullRefresh() {
        orderPtrlayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                PatrolFragment.this.queryPatrolList();
            }
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    /**
     * 查询巡视类型列表.
     */
    public void getPatrolTypeList() {
        SelectEleRoomParams params = new SelectEleRoomParams();
        params.setAccountId(curEleId);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                // 获取电房列表
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), "获取电房失败", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<EleRoomResult> eleRoomResultList = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<EleRoomResult>>() {
                        }.getType());
                if(eleRoomResultList.size()>0){
                    showHoulder(2);
                    rootAdapter.setmDataList(eleRoomResultList);
                    rootAdapter.notifyDataSetChanged();
                }else{
                    showHoulder(1);
                }
            }
        });
    }
    private void showPatrolItem(List<PatrolResult> recordResult, final CompanyEquipmentResult result) {
        showHoulder(5);
        Log.i(TAG, "showPatrolItem:二级设备id "+result.getEquipmentId());
        //已有record记录转成map
        Map<Integer, PatrolResult> recordMap = new HashMap<Integer, PatrolResult>();
        List<PatrolItemEntity> newEntities = new ArrayList<>();
        for (PatrolResult result1 : recordResult) {
            if (result1.getPatrolItemId()==null) {
                PatrolItemEntity entity = new PatrolItemEntity();
                entity.setInputType(PatrolLayoutAdapter.NEWQUESTION);
                entity.setResult(result1);
                newEntities.add(entity);
            } else {
                recordMap.put(result1.getPatrolItemId(), result1);
            }
        }

        //该设备巡视项目
        List<PatrolItemEntity> itemEntities = PatrolItemDao.getInstance().queryByEquipId(result.getEquipmentId());
        Log.i(TAG, "showPatrolItem:-------- "+itemEntities.size());
        for (PatrolItemEntity entity : itemEntities) {//26
            //如果已有巡视记录
            if (recordMap.containsKey(entity.getPatrolItemId())) {
                entity.setInputType(PatrolLayoutAdapter.QUESTION);
                entity.setResult(recordMap.get(entity.getPatrolItemId()));
            }else {
                entity.setInputType(PatrolLayoutAdapter.NOQUESTION);
                entity.setResult(new PatrolResult());
            }
            //判断是否有温度 ，并且弹出温度框
            if (entity.getHasTemperature() != null){
                entity.setTemperature(1);
            }
            //页面显示项目
            showItemEntities.add(entity);
        }
        showItemEntities.addAll(newEntities);
        patrolItemAdapter.setDataList(showItemEntities);
        patrolItemAdapter.notifyDataSetChanged();
        orderPtrlayout.refreshComplete();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == EventKind.REQUEST_CODE_SELECT) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    if (images.get(0).size > 5242880) {
                        Toast.makeText(x.app(),"不能长传查过5M大小的图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dialognew.setImages(images);

                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == EventKind.REQUEST_CODE_PREVIEW) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images == null || images.size() == 0) {
                    dialognew.deleteImage();
                }
            }
        }
    }
    private void showHoulder(int type) {
        switch (type){
            case 1:
                curhodler.layout_lv1_all.setVisibility(View.GONE);
                curhodler.layout_lv1_hite.setVisibility(View.VISIBLE);
                curhodler.layout_lv2_all.setVisibility(View.GONE);
                curhodler.layout_lv2_hite.setVisibility(View.VISIBLE);
                curhodler.layout_lv3_all.setVisibility(View.GONE);
                curhodler.layout_lv3_hite.setVisibility(View.VISIBLE);
                break;
            case 2:
                curhodler.layout_lv1_all.setVisibility(View.VISIBLE);
                curhodler.layout_lv1_hite.setVisibility(View.GONE);
                curhodler.layout_lv2_all.setVisibility(View.GONE);
                curhodler.layout_lv2_hite.setVisibility(View.VISIBLE);
                curhodler.layout_lv3_all.setVisibility(View.GONE);
                curhodler.layout_lv3_hite.setVisibility(View.VISIBLE);
                break;
            case 3:
                curhodler.layout_lv1_all.setVisibility(View.VISIBLE);
                curhodler.layout_lv1_hite.setVisibility(View.GONE);
                curhodler.layout_lv2_all.setVisibility(View.GONE);
                curhodler.layout_lv2_hite.setVisibility(View.VISIBLE);
                curhodler.layout_lv3_all.setVisibility(View.GONE);
                curhodler.layout_lv3_hite.setVisibility(View.VISIBLE);
                break;
            case 4:
                curhodler.layout_lv1_all.setVisibility(View.VISIBLE);
                curhodler.layout_lv1_hite.setVisibility(View.GONE);
                curhodler.layout_lv2_all.setVisibility(View.VISIBLE);
                curhodler.layout_lv2_hite.setVisibility(View.GONE);
                curhodler.layout_lv3_all.setVisibility(View.GONE);
                curhodler.layout_lv3_hite.setVisibility(View.VISIBLE);
                break;
            case 5:
                curhodler.layout_lv1_all.setVisibility(View.VISIBLE);
                curhodler.layout_lv1_hite.setVisibility(View.GONE);
                curhodler.layout_lv2_all.setVisibility(View.VISIBLE);
                curhodler.layout_lv2_hite.setVisibility(View.GONE);
                curhodler.layout_lv3_all.setVisibility(View.VISIBLE);
                curhodler.layout_lv3_hite.setVisibility(View.GONE);
                break;

        }
    }
}
