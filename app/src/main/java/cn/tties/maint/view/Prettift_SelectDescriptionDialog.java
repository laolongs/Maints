package cn.tties.maint.view;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.activity.BaseFragment;
import cn.tties.maint.adapter.DescriptionListViewAdapter;
import cn.tties.maint.adapter.PrettiftDescriptionListViewAdapter;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.DescriptionResult;
import cn.tties.maint.httpclient.result.PrettiftStatusAndDetailsResult;
import cn.tties.maint.util.NoFastClickUtils;


/**
 * 新建问题弹出框
 * author chensi
 */
public class Prettift_SelectDescriptionDialog {
    private static final String TAG = "Prettift_SelectDesc";
    List<ImageItem> imageItemsList;
    BaseFragment mFragment;
    private Button btn_cancel;
    CompanyEquipmentResult result;
    AlertDialog dialog;
    PrettiftStatusAndDetailsResult.ResultBean resultBeanAll;
    int titleType;
    public Prettift_SelectDescriptionDialog(BaseFragment fragment, PrettiftStatusAndDetailsResult.ResultBean resultBeanAll,int titleType) {
        this.mFragment = fragment;
        this.resultBeanAll=resultBeanAll;
        this.titleType=titleType;
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
        if(titleType==1){
            text_name.setText("处理前效果");
        }else{
            text_name.setText("处理后效果");
        }
        PrettiftDescriptionListViewAdapter descriptionAdapter = new PrettiftDescriptionListViewAdapter(mFragment, resultBeanAll);
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