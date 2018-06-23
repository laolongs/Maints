package cn.tties.maint.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import cn.tties.maint.adapter.DescriptionListViewAdapter;
import cn.tties.maint.adapter.QuestionMoreListViewAdapter;
import cn.tties.maint.adapter.SafetyImageListViewAdapter;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.UploadImgDescriptionParam;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.DescriptionResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.StringUtil;


/**
 * 新建问题弹出框
 * author chensi
 */
public class Question_SelectMoreDialog {
    private static final String TAG = "Question_SelectMoreD";
    List<ImageItem> imageItemsList;
    BaseFragment mFragment;
    private Button btn_cancel;
    CompanyEquipmentResult result;
    AlertDialog dialog;
    List<DescriptionResult> questionDescriptionList;
    public Question_SelectMoreDialog(BaseFragment fragment,List<DescriptionResult> questionDescriptionList) {
        this.mFragment = fragment;
       this.questionDescriptionList=questionDescriptionList;
    }
    public void setContentView() {
        // 指定布局
        AlertDialog.Builder builder = new AlertDialog.Builder(mFragment.getContext());
        View view = View.inflate(mFragment.getContext(), R.layout.dialog_question_more, null);
        builder.setView(view);
        TextView text_name = (TextView) view.findViewById(R.id.text_name);
        ListView list = (ListView) view.findViewById(R.id.list);
        btn_cancel = (Button)  view.findViewById(R.id.btn_cancel);
        dialog = builder.create();
        QuestionMoreListViewAdapter descriptionAdapter = new QuestionMoreListViewAdapter(mFragment, questionDescriptionList);
        list.setAdapter(descriptionAdapter);
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
    public void loading() {
        dialog.show();
    }

}