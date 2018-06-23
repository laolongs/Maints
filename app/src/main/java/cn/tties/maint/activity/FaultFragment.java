package cn.tties.maint.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.FaultListAdapter;
import cn.tties.maint.adapter.QuestionNewListAdapter;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.QueryFaultParams;
import cn.tties.maint.httpclient.params.QueryPrettiftParams;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.QuertionNewResult;
import cn.tties.maint.httpclient.result.QueryFaultResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.widget.PtrListViewOnScrollListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by li on 2018/6/15
 * description：消缺
 * author：guojlli
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_fault)
public class FaultFragment extends BaseFragment {
    private static final String TAG = "FaultFragment";
    public static FaultFragment faultFragmentInstance;
    @ViewInject(R.id.layout_fault_all)
    private LinearLayout fault_all;
    @ViewInject(R.id.text_title)
    private TextView text_title;
    @ViewInject(R.id.list_fault)
    private ListView list_fault;
    @ViewInject(R.id.layout_fault_hite)
    private LinearLayout fault_hite;
    @ViewInject(R.id.ptrlayout_order)
    private PtrClassicFrameLayout orderPtrlayout;
    private PtrListViewOnScrollListener mPtrListViewOnScrollListener;
    private CompanyResult curCompany;
    private Integer curEleId;
    private String curEleNo;
    int workOrderId;
    int workType;
    FaultListAdapter adapter;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initPullRefresh();
        getQuestionList();
        faultFragmentInstance = this;
    }
    @SuppressLint("ValidFragment")
    public FaultFragment(Integer curEleId,String curEleNo, CompanyResult curCompany,int workOrderId,int workType){
        this.curEleId=curEleId;
        this.curEleNo=curEleNo;
        this.curCompany=curCompany;
        this.workOrderId=workOrderId;
        this.workType=workType;
    }
    @Override
    public void changeEleAccountNextSteps(Integer curEleId, String curEleNo, CompanyResult curCompany) {
        this.curEleId=curEleId;
        this.curEleNo=curEleNo;
        this.curCompany=curCompany;
    }
    private void initView() {
        adapter = new FaultListAdapter(FaultFragment.this);
        list_fault.setAdapter(adapter);
    }
    private void initPullRefresh() {
        orderPtrlayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                FaultFragment.this.getQuestionList();
            }
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    public void getQuestionList() {
        QueryFaultParams params=new QueryFaultParams();
        params.setWorkOrderId(workOrderId);
        Log.i(TAG, "getQuestionList: "+workOrderId);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                QueryFaultResult ret = JsonUtils.deserialize(result, QueryFaultResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), "获取消缺数据失败", Toast.LENGTH_SHORT).show();
                    orderPtrlayout.refreshComplete();
                    return;
                }

                adapter.setDataList(ret.getResult(),workOrderId,workType);
                adapter.notifyDataSetChanged();
                orderPtrlayout.refreshComplete();
            }
        });
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
                    adapter.showDialog(images);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == EventKind.REQUEST_CODE_PREVIEW) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images == null || images.size() == 0) {
                    adapter.showdeleteDialog();
                }
            }
        }
    }

}
