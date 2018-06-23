package cn.tties.maint.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import cn.tties.maint.adapter.PrettiftAndDustAdapter;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.PrettiftAndDustParams;
import cn.tties.maint.httpclient.params.Prettift_StatusAndDetilsParams;
import cn.tties.maint.httpclient.params.QueryPrettiftParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.PrettiftResult;
import cn.tties.maint.httpclient.result.PrettiftStatusAndDetailsResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.view.Prettift_DescriptionDialog;
import cn.tties.maint.view.Prettift_SelectDescriptionDialog;
import cn.tties.maint.view.Question_SelectMoreDialog;
import cn.tties.maint.widget.PtrListViewOnScrollListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by li on 2018/6/15
 * description：美化安规
 * author：guojlli
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_prettift)
public class PrettiftFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "FaultFragment";
    public static PrettiftFragment prettiftFragmentIntener;
    @ViewInject(R.id.layout_all)
    private LinearLayout all;
    @ViewInject(R.id.text_title)
    private TextView text_title;
    @ViewInject(R.id.prettift_before)
    private LinearLayout before;
    @ViewInject(R.id.text_before)
    private TextView text_before;
    @ViewInject(R.id.prettift_later)
    private LinearLayout later;
    @ViewInject(R.id.text_later)
    private TextView text_later;
    @ViewInject(R.id.list)
    private ListView list;
    @ViewInject(R.id.layout_hite)
    private LinearLayout hite;
    @ViewInject(R.id.ptrlayout_order)
    private PtrClassicFrameLayout orderPtrlayout;
    private PtrListViewOnScrollListener mPtrListViewOnScrollListener;
    private CompanyResult curCompany;
    private Integer curEleId;
    private String curEleNo;
    private PrettiftAndDustAdapter andDustAdapter;
    int workOrderId;
    private Prettift_DescriptionDialog dialog;
    private int bgcolor;
    private int bgbefore;
    private int bglater;
    boolean isbefore;
    boolean islater;
    int beforeNum=1;
    int laterNum=2;
    int PrettiftNum=2;//美化
    PrettiftStatusAndDetailsResult.ResultBean resultBeanbefor;
    PrettiftStatusAndDetailsResult.ResultBean resultBeanlater;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initPullRefresh();
        getPrettiftStatusList();
        prettiftFragmentIntener = this;
    }
    @SuppressLint("ValidFragment")
    public PrettiftFragment(Integer curEleId, String curEleNo, CompanyResult curCompany,int workOrderId){
        this.curEleId=curEleId;
        this.curEleNo=curEleNo;
        this.curCompany=curCompany;
        this.workOrderId=workOrderId;
    }
    @Override
    public void changeEleAccountNextSteps(Integer curEleId, String curEleNo, CompanyResult curCompany) {
        this.curEleId=curEleId;
        this.curEleNo=curEleNo;
        this.curCompany=curCompany;
    }
    private void initPullRefresh() {
        orderPtrlayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                PrettiftFragment.this.getPrettiftList();
            }
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }
    private void initView() {
        bgcolor = Color.parseColor("#000000");
        bgbefore = Color.parseColor("#4DDBCF");
        bglater = Color.parseColor("#1B92EE");
        andDustAdapter = new PrettiftAndDustAdapter();
        list.setAdapter(andDustAdapter);
        before.setOnClickListener(this);
        later.setOnClickListener(this);
        getPrettiftList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.prettift_before://处理前
                if(!isbefore){
                    getDialog(beforeNum);
                }else{//处理前效果
                    Prettift_SelectDescriptionDialog moreDialog=new Prettift_SelectDescriptionDialog(PrettiftFragment.this,resultBeanbefor,beforeNum);
                    moreDialog.setContentView();
                    moreDialog.loading();
                }
                break;
            case R.id.prettift_later://处理后
                if(!islater){
                    getDialog(laterNum);
                }else{//处理后效果
                    Prettift_SelectDescriptionDialog moreDialog=new Prettift_SelectDescriptionDialog(PrettiftFragment.this,resultBeanlater,laterNum);
                    moreDialog.setContentView();
                    moreDialog.loading();
                }
                break;
        }
    }
    public void getDialog(int description_type){
        dialog = new Prettift_DescriptionDialog(PrettiftFragment.this,curCompany.getCompanyId(),workOrderId,description_type);
        dialog.setContentView();
        dialog.loading();
    }
    public void getPrettiftList(){
        PrettiftAndDustParams params=new PrettiftAndDustParams();
        params.setPrettifyDustType(PrettiftNum);
        HttpClientSend.getInstance().send(params,new BaseStringCallback(){
            @Override
            public void onSuccess(String result) {
                PrettiftResult ret = JsonUtils.deserialize(result, PrettiftResult.class);
                if(ret.getErrorCode()!=0){
                    orderPtrlayout.refreshComplete();
                    Toast.makeText(x.app(), "获取美化安规巡视项失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<PrettiftResult.ResultBean> result1 = ret.getResult();
                if(result1.size()>0){
                    hite.setVisibility(View.GONE);
                    all.setVisibility(View.VISIBLE);
                    andDustAdapter.setDataList(result1);
                    andDustAdapter.notifyDataSetChanged();
                    orderPtrlayout.refreshComplete();
                }else{
                    hite.setVisibility(View.VISIBLE);
                    all.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                orderPtrlayout.refreshComplete();
            }
        });
    }
    public void getPrettiftStatusList(){
        Prettift_StatusAndDetilsParams params=new Prettift_StatusAndDetilsParams();
        params.setPrettifyDustType(PrettiftNum);
        params.setWorkOrderId(workOrderId);
        HttpClientSend.getInstance().send(params,new BaseStringCallback(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onSuccess(String result) {
                PrettiftStatusAndDetailsResult ret = JsonUtils.deserialize(result, PrettiftStatusAndDetailsResult.class);
                if(ret.getErrorCode()!=0){
                    Toast.makeText(x.app(), "查询美化安规状态失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                //处理前处理后
                if(ret.getResult().size()<=0||ret.getResult()==null){
                    isbefore=false;
                    islater=false;
                    before.setBackgroundColor(bgbefore);
                    text_before.setText("处理前");
                    later.setBackgroundColor(bglater);
                    text_later.setText("处理后");
                }else{//查看详情
                    for (PrettiftStatusAndDetailsResult.ResultBean resultBean:ret.getResult()) {
                        if(resultBean.getDescriptionType()==1){//处理前效果
                            before.setBackgroundColor(bgcolor);
                            text_before.setText("处理前效果");
                            isbefore=true;
                            resultBeanbefor=resultBean;
                        }
                        if(resultBean.getDescriptionType()==2){//处理后效果
                            later.setBackgroundColor(bgcolor);
                            text_later.setText("处理后效果");
                            islater=true;
                            resultBeanlater=resultBean;
                        }
                    }
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);

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
                    dialog.setImages(images);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == EventKind.REQUEST_CODE_PREVIEW) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images == null || images.size() == 0) {
                    dialog.deleteImage();
                }
            }
        }
    }

}
