package cn.tties.maint.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.QuestionListAdapter;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.enums.QuestionStatusType;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.CompanyParams;
import cn.tties.maint.httpclient.params.QueryQuertionParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.QuertionResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.XUtils3ImageLoader;
import cn.tties.maint.view.DescriptionDialog;

/**
 * 问题列表.
 */
@ContentView(R.layout.fragment_question)
public class QuestionFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "QuestionFragment";

    @ViewInject(R.id.refreshlayout)
    private SwipeRefreshLayout questionPtrlayout;

    @ViewInject(R.id.list_question_list)
    private RecyclerView questionListView;

    @ViewInject(R.id.spinner_company)
    private Spinner spinnerComapny;

    @ViewInject(R.id.spinner_status)
    private Spinner spinnerStatus;

    private QuertionResult searchEntity;

    private List<CompanyResult> companyList;

    private ArrayAdapter<String> companyAdapter;

    private ArrayAdapter<String> statusAdapter;

    private QuestionListAdapter questionListAdapter;
    private List<QuertionResult> quertionList;
    public static QuestionFragment mQuestionFragmentInstance;
    private DescriptionDialog dialog;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchEntity = new QuertionResult();
        searchEntity.setStatus(QuestionStatusType.HANDING.getType());
        mQuestionFragmentInstance = this;
        initImageLoad();
        this.initView();
        this.initData();
    }

    /**
     * 初始化界面.
     */
    private void initView() {
        questionListView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        this.questionPtrlayout.setOnRefreshListener(this);
        questionListAdapter = new QuestionListAdapter(QuestionFragment.this, quertionList);
        questionListView.setAdapter(questionListAdapter);
        questionListAdapter.notifyDataSetChanged();

    }

    private void initImageLoad() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new XUtils3ImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);
    }

    /**
     * 初始化数据.
     */
    private void initData() {
        getCompanyList();
        getStatusList();
        this.getQuestionList();
    }

    private void getCompanyList() {
        companyList = new ArrayList<>();
        CompanyParams params = new CompanyParams();
        params.setMaintStaffId(MyApplication.getUserInfo().getStaffId());
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
                spinnerComapny.setAdapter(companyAdapter);
                spinnerComapny.setOnItemSelectedListener(
                        new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i == 0) {
                                    searchEntity.setCompanyId(null);
                                } else {
                                    searchEntity.setCompanyId(companyList.get(i - 1).getCompanyId());
                                }
                                getQuestionList();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        }
                );
            }
        });
    }

    private void getStatusList() {
        List<String> questionKinds = new ArrayList<>();
        for (QuestionStatusType e : QuestionStatusType.values()) {
            questionKinds.add(e.getInfo());
        }
        statusAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, questionKinds);
        spinnerStatus.setAdapter(statusAdapter);
        spinnerStatus.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        searchEntity.setStatus(QuestionStatusType.getValue(statusAdapter.getItem(i)));
                        getQuestionList();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
    }

    public void getQuestionList() {
        QueryQuertionParams queryQuertionParams = new QueryQuertionParams();
        int staffId = MyApplication.getUserInfo().getStaffId();
        queryQuertionParams.setStaffId(staffId);
        queryQuertionParams.setCompanyId(searchEntity.getCompanyId());
        queryQuertionParams.setStatus(searchEntity.getStatus());
        HttpClientSend.getInstance().send(queryQuertionParams, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                questionPtrlayout.setRefreshing(false);
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), "获取问题数据失败", Toast.LENGTH_SHORT).show();

                    return;
                }
                quertionList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<QuertionResult>>() {
                }.getType());
                questionListAdapter.setmQuestionList(quertionList);
                questionListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                questionPtrlayout.setRefreshing(false);
            }
        });
    }

    public void openDescriptionDialog(Integer staffId, Integer questionId) {
        dialog =  new DescriptionDialog(this, staffId, questionId);
        dialog.loading();
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

    @Override
    public void onRefresh() {
        this.getQuestionList();
    }
}
