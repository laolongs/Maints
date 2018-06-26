package cn.tties.maint.view;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.activity.BaseFragment;
import cn.tties.maint.adapter.QuestionMoreListViewAdapter;
import cn.tties.maint.adapter.patrolMoreListViewAdapter;
import cn.tties.maint.enums.FaultType;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.DescriptionResult;
import cn.tties.maint.httpclient.result.PatrolResult;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.StringUtil;


/**
 * 新建问题弹出框
 * author chensi
 */
public class Patrol_SelectMoreDialog {
    private static final String TAG = "Patrol_SelectMoreD";
    List<ImageItem> imageItemsList;
    BaseFragment mFragment;
    private Button btn_cancel;
    CompanyEquipmentResult result;
    AlertDialog dialog;
    PatrolResult.QuertionBean bean;
    String title;
    String company;
    public Patrol_SelectMoreDialog(BaseFragment fragment, PatrolResult.QuertionBean questionDescriptionList,String title,String company) {
        this.mFragment = fragment;
       this.bean=questionDescriptionList;
       this.title=title;
       this.company=company;
    }
    public void setContentView() {
        // 指定布局
        AlertDialog.Builder builder = new AlertDialog.Builder(mFragment.getContext());
        View view = View.inflate(mFragment.getContext(), R.layout.dialog_patrol_more, null);
        builder.setView(view);
        ImageView img_type = (ImageView) view.findViewById(R.id.img_type);
        TextView text_name = (TextView) view.findViewById(R.id.text_name);
        TextView text_companyname = (TextView) view.findViewById(R.id.text_companyname);
        TextView text_address = (TextView) view.findViewById(R.id.text_address);
        ListView list = (ListView) view.findViewById(R.id.list);
        btn_cancel = (Button)  view.findViewById(R.id.btn_cancel);
        dialog = builder.create();

        if(bean.getStatus()== FaultType.FAULT.getType()){//一般
            img_type.setImageResource(R.mipmap.img_patrol_general);
        }
        if(bean.getStatus()== FaultType.DANGEROUS.getType()){//重大
            img_type.setImageResource(R.mipmap.img_patrol_importcacel);
        }
        if(bean.getStatus()== FaultType.NORLMAL.getType()){//紧急
            img_type.setImageResource(R.mipmap.img_patrol_ungency);
        }
        text_name.setText(title);
        text_companyname.setText(company);
        String address=StringUtil.substring(bean.getDescription(), 1, bean.getDescription().length()-1);
        text_address.setText(address);
        patrolMoreListViewAdapter descriptionAdapter = new patrolMoreListViewAdapter(mFragment, bean.getQuestionDescriptionList(),bean.getCreateTime());
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