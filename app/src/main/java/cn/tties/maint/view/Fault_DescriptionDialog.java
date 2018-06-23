package cn.tties.maint.view;

import android.app.AlertDialog;
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
import cn.tties.maint.activity.FaultFragment;
import cn.tties.maint.activity.QuestionFragment;
import cn.tties.maint.adapter.SafetyImageListViewAdapter;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.UpdateOrderStatusParams;
import cn.tties.maint.httpclient.params.UpdateQuertionActionParams;
import cn.tties.maint.httpclient.params.UploadImgDescriptionParam;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.StringUtil;


/**
 * 完成消缺弹出框
 *
 */
public class Fault_DescriptionDialog {
    private static final String TAG = "Fault_DescriptionD";
    List<ImageItem> imageItemsList;
    SafetyImageListViewAdapter adapter;
    BaseFragment mFragment;
    int maxCount = 9;
    int mPostion;
    private Button btn_cancel;
    private Button btn_add;
    private EditText edit_describe;
    CompanyEquipmentResult result;
    int workOrderId;
    int workOrderStatus=1;//已完成
    int workType;
    AlertDialog dialog;
    public Fault_DescriptionDialog(BaseFragment fragment, int workOrderId,int workType) {
        adapter = new SafetyImageListViewAdapter(fragment.getContext(), new ArrayList<ImageItem>(), maxCount);
        adapter.setOnItemClickListener(clickListener);
        this.mFragment = fragment;
        this.workOrderId=workOrderId;
        this.workType=workType;
        imageItemsList = new ArrayList<>();

    }
    public void setContentView() {
        // 指定布局
        AlertDialog.Builder builder = new AlertDialog.Builder(mFragment.getContext());
        View view = View.inflate(mFragment.getContext(), R.layout.dialog_question, null);
        builder.setView(view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        edit_describe = (EditText)  view.findViewById(R.id.edit_describe);
        btn_cancel = (Button)  view.findViewById(R.id.btn_cancel);
        btn_add = (Button)  view.findViewById(R.id.btn_add);
        dialog = builder.create();
        recyclerView.setLayoutManager(new GridLayoutManager(mFragment.getContext(), 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        //完成消缺
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                String title = edit_describe.getText().toString();
                int count = imageItemsList == null ? 0 : imageItemsList.size();
//                if (StringUtil.isEmpty(title) && count == 0) {
//                    Toast.makeText(x.app(), "请输入描述或至少上传一张图片", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                //这个接口还没测试，不知道传文本能不能成功
                UpdateQuertionActionParams params = new UpdateQuertionActionParams();
                params.setQuestionId(workOrderId);
                params.setStatus(workOrderStatus);
                params.setContent(title);
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
                        BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                        if (ret.getErrorCode() != 0) {
                            Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(x.app(), "消缺成功", Toast.LENGTH_SHORT).show();
                        FaultFragment.faultFragmentInstance.getQuestionList();
                      dialog.dismiss();
                    }
                });
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                dialog.dismiss();
            }
        });
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
        dialog.show();
    }

}