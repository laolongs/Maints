package cn.tties.maint.view;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.activity.BaseFragment;
import cn.tties.maint.activity.QuestionFragment;
import cn.tties.maint.adapter.DescriptionImageListViewAdapter;
import cn.tties.maint.adapter.SafetyImageListViewAdapter;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.SafetyParams;
import cn.tties.maint.httpclient.params.UploadImgDescriptionParam;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.SafetyResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.StringUtil;


/**
 * 处理建议弹出框
 * author chensi
 */
public class SafetyDialog extends BaseCustomDialog {
    Integer staffId;
    Integer orderworkid;
    String orderworkName;
    List<ImageItem> imageItemsList;
    SafetyImageListViewAdapter adapter;
    BaseFragment mFragment;
    int maxCount = 9;
    int mPostion;
    private Button btn_skip;
    private Button btn_submit;
    OnClickSkipSuccess skipSuccess;
    OnClickSubmitSuccess submitSuccess;

    public SafetyDialog(BaseFragment fragment, Integer staffId, Integer orderworkid, String orderworkName) {
        super(MyApplication.curActivity, null);
        this.staffId = staffId;
        this.orderworkid = orderworkid;
        this.orderworkName=orderworkName;
        adapter = new SafetyImageListViewAdapter(fragment.getContext(), new ArrayList<ImageItem>(), maxCount);
        adapter.setOnItemClickListener(clickListener);
        this.mFragment = fragment;
        imageItemsList = new ArrayList<>();
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_safety);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btn_skip = (Button) findViewById(R.id.btn_skip);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        recyclerView.setLayoutManager(new GridLayoutManager(mFragment.getContext(), 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                int count = imageItemsList == null ? 0 : imageItemsList.size();
                if (count == 0) {
                    Toast.makeText(x.app(), "请至少上传一张图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                SafetyParams params=new SafetyParams();
                params.setWorkOrderId(orderworkid);
                params.setMaintStaffId(staffId);
                params.setCompanyNameAndSafe(orderworkName+"安全交底");
//                UploadImgDescriptionParam params = new UploadImgDescriptionParam();
//                params.setQuestionId(orderworkid);
//                params.setStaffId(staffId);
                List<File> fileList = new ArrayList<>();
                for (ImageItem imageItem : imageItemsList) {
                    if (imageItem != null) {
                        File file = new File(imageItem.path);
                        fileList.add(file);
                    }
                }
                HttpClientSend.getInstance().sendFile(params, fileList,new BaseStringCallback() {
                    @Override
                    public void onSuccess(String result) {
                        SafetyResult ret = JsonUtils.deserialize(result, SafetyResult.class);
                        if (ret.getErrorCode() != 0) {
                            Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(ret.getResult().isFlag()){
                            Toast.makeText(x.app(), "交底成功", Toast.LENGTH_SHORT).show();
                            SafetyDialog.this.dismiss();
                            submitSuccess.SubmitSuccessListenter();
                        }else{
                            Toast.makeText(x.app(), "交底失败", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                });
            }
        });
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                SafetyDialog.this.dismiss();
                skipSuccess.SkipSuccessListenter();
            }
        });
//        super.setContentView();
    }
    public void setOnClickSubmitSuccess(OnClickSubmitSuccess submitSuccess){
        this.submitSuccess=submitSuccess;
    }
    public void setOnClickSkipSuccess(OnClickSkipSuccess skipSuccess){
        this.skipSuccess=skipSuccess;
    }
    public void setImages(List<ImageItem> list) {
        for (ImageItem oldImageItem : imageItemsList) {
            boolean isExsist = false;
            int index = 0;
            for (ImageItem newImageItem : list) {
                if (newImageItem.path.equals(oldImageItem.path)) {
                    isExsist = true;
                    break;
                }
                index++;
            }
            if (isExsist) {
                list.remove(index);
            }
        }
        if (list.size() == 0) {
            Toast.makeText(x.app(),"图片已选择",Toast.LENGTH_SHORT).show();
        }
        imageItemsList.addAll(list);
        adapter.setImages(imageItemsList);
    }

    SafetyImageListViewAdapter.OnRecyclerViewItemClickListener clickListener = new SafetyImageListViewAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {
            mPostion = position;
            if (position > imageItemsList.size() - 1) {
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxCount);
                Intent intent1 = new Intent(mFragment.getActivity(), ImageGridActivity.class);
                mFragment.startActivityForResult(intent1, EventKind.REQUEST_CODE_SELECT);
            } else {
                Intent intentPreview = new Intent(mFragment.getContext(), ImagePreviewDelActivity.class);
                ImageItem item = new ImageItem();
                item.path = imageItemsList.get(position).path;
                List<ImageItem> list = new ArrayList<>();
                list.add(item);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) list);
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                mFragment.startActivityForResult(intentPreview, EventKind.REQUEST_CODE_PREVIEW);
            }
        }
    };

    public void deleteImage() {
        imageItemsList.remove(mPostion);
        adapter.setImages(imageItemsList);
        adapter.notifyDataSetChanged();
    }

    public void loading() {

        this.show();
    }
    public interface OnClickSubmitSuccess{
        public void SubmitSuccessListenter();
    }
    public interface OnClickSkipSuccess{
        public void SkipSuccessListenter();
    }
}