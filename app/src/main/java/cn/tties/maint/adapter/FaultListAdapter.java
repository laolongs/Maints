package cn.tties.maint.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.imagepicker.bean.ImageItem;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.activity.BaseFragment;
import cn.tties.maint.dao.PatrolItemDao;
import cn.tties.maint.entity.PatrolItemEntity;
import cn.tties.maint.enums.FaultType;
import cn.tties.maint.enums.QuestionStatusType;
import cn.tties.maint.httpclient.result.DescriptionResult;
import cn.tties.maint.httpclient.result.QuertionNewResult;
import cn.tties.maint.httpclient.result.QueryFaultResult;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.view.Fault_DescriptionDialog;
import cn.tties.maint.view.Fault_SelectMoreDialog;
import cn.tties.maint.view.Question_DescriptionDialog;
import cn.tties.maint.view.Question_SelectMoreDialog;

/**
 * 问题Adapter.
 * Created by chensi on 2018/1/11.
 */

public class FaultListAdapter extends BaseAdapter {
    private static final String TAG = "FaultListAdapter";
    private List<QueryFaultResult.ResultBean> beanList;
    BaseFragment mFragment;
    int workorderId;
    int workType;
    private int textcolor;
    private Fault_DescriptionDialog dialog;
    @Override
    public int getCount() {
        return beanList==null?0:beanList.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void setDataList(List<QueryFaultResult.ResultBean> beanList,int workorderId,int workType) {
        this.beanList = beanList;
        this.workorderId=workorderId;
        this.workType=workType;
        textcolor = Color.parseColor("#888888");
    }
    public FaultListAdapter(BaseFragment mFragment) {
        this.mFragment = mFragment;
    }
    public List<QueryFaultResult.ResultBean> getDataList() {
        return beanList;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final  QueryFaultResult.ResultBean resultBean = beanList.get(position);
        QuestionViewHolder questionViewHolder=null;
            if (convertView == null) {
                convertView = View.inflate(x.app(), R.layout.listview_newquestion, null);
                questionViewHolder = new QuestionViewHolder(convertView);
                convertView.setTag(questionViewHolder);
            } else {
                questionViewHolder = (QuestionViewHolder) convertView.getTag();
            }
        GradientDrawable background = (GradientDrawable)questionViewHolder.btn_description.getBackground();
            if(!resultBean.getCompanyShortName().isEmpty()){
                questionViewHolder.text_company.setText(resultBean.getCompanyShortName()+"");
            }
            if(!resultBean.getCreateTime().isEmpty()){
                questionViewHolder.text_time.setText(resultBean.getCreateTime()+"");
            }
            //该设备巡视项目
            PatrolItemEntity patrolItemEntity = PatrolItemDao.getInstance().queryByPatrolItemId(resultBean.getPatrolItemId());
            if(patrolItemEntity!=null){
                questionViewHolder.text_title.setText(patrolItemEntity.getTitle());
            }else{
                questionViewHolder.text_title.setText("新建问题");
            }
            //地址
            String substring = StringUtil.substring(resultBean.getDescription(), 1, resultBean.getDescription().length()-1);
            Log.i(TAG, "getView: substring"+resultBean.getDescription().length());
            String[] split = StringUtil.split(substring, ", ");
            if(0<=split.length-1){
                questionViewHolder.text_address1.setText(split[0]);
            }
            if(1<=split.length-1){
                questionViewHolder.text_address1.setText(split[1]);
            }
            if(2<=split.length-1){
                questionViewHolder.text_address1.setText(split[2]);
            }
            if(resultBean.getFaultType()==FaultType.FAULT.getType()){
                questionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_general);
            }
            if(resultBean.getFaultType()==FaultType.DANGEROUS.getType()){
                questionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_importcacel);
            }
            if(resultBean.getFaultType()==FaultType.NORLMAL.getType()){
                questionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_ungency);
            }

            if(resultBean.getQuestionDescriptionList().size()>1){
                questionViewHolder.text_more.setVisibility(View.VISIBLE);
                //查看更多
                questionViewHolder.text_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fault_SelectMoreDialog moreDialog=new Fault_SelectMoreDialog(mFragment,resultBean.getQuestionDescriptionList());
                        moreDialog.setContentView();
                        moreDialog.loading();
                    }
                });
            }else{
                questionViewHolder.text_more.setVisibility(View.GONE);
            }

        //if(resultBean.getStatus()== QuestionStatusType.UNSTART.getType())//未进行
            if(resultBean.getStatus()== QuestionStatusType.HANDING.getType()){//进行中
                //图片列表  图片集合盘空
                final List<QueryFaultResult.ResultBean.QuestionDescriptionListBean> descriptionList = new ArrayList<>();
                if (resultBean.getQuestionDescriptionList() != null&&resultBean.getQuestionDescriptionList().size()>0) {
                    descriptionList.add(resultBean.getQuestionDescriptionList().get(resultBean.getQuestionDescriptionList().size()-1));
                }
                FaultDescriptionListViewAdapter descriptionAdapter = new FaultDescriptionListViewAdapter(mFragment, descriptionList);
                questionViewHolder.question_imglist.setAdapter(descriptionAdapter);

                questionViewHolder.btn_text.setText("完成消缺");
                //完成消缺
                questionViewHolder.btn_description.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog = new Fault_DescriptionDialog(mFragment,workorderId,workType);
                        dialog.setContentView();
                        dialog.loading();
                    }
                });
            }
            if(resultBean.getStatus()== QuestionStatusType.END.getType()){//已完成
                background.setStroke(2,x.app().getResources().getColor(R.color.common_text_min_gray));
                background.setColor(x.app().getResources().getColor(R.color.common_bg));
                questionViewHolder.btn_text.setText("已完成消缺");
                questionViewHolder.btn_text.setTextColor(textcolor);
                //图片列表  图片集合盘空
                final List<QueryFaultResult.ResultBean.QuestionScheduleListBean> descriptionList = new ArrayList<>();
                if (resultBean.getQuestionScheduleList() != null&&resultBean.getQuestionScheduleList().size()>0) {
                    descriptionList.add(resultBean.getQuestionScheduleList().get(resultBean.getQuestionScheduleList().size()-1));
                }
                FaultEndListViewAdapter descriptionAdapter = new FaultEndListViewAdapter(mFragment, descriptionList);
                questionViewHolder.question_imglist.setAdapter(descriptionAdapter);
            }


        return convertView;
    }
    public class QuestionViewHolder{
        ImageView img_type;
//        TextView text_schedule;
        TextView text_title;
        ListView question_imglist;
        TextView text_time;
        TextView text_more;
        TextView text_company;
        TextView text_address1;
        TextView text_address2;
        TextView text_address3;
        LinearLayout btn_description;
        TextView btn_text;
        @SuppressLint("WrongViewCast")
        public QuestionViewHolder(View view) {
            img_type= view.findViewById(R.id.img_type);
            text_title= view.findViewById(R.id.text_title);
//            text_schedule= view.findViewById(R.id.text_schedule);
            question_imglist= view.findViewById(R.id.question_imglist);
            text_time= view.findViewById(R.id.text_time);
            text_more= view.findViewById(R.id.text_more);
            text_company= view.findViewById(R.id.text_company);
            text_address1= view.findViewById(R.id.text_address1);
            text_address2= view.findViewById(R.id.text_address2);
            text_address3= view.findViewById(R.id.text_address3);
            btn_description= view.findViewById(R.id.btn_description);
            btn_text= view.findViewById(R.id.btn_text);
        }
    }

    public void showDialog(List<ImageItem> itemList){
        dialog.setImages(itemList);
    }
    public void showdeleteDialog(){
        dialog.deleteImage();
    }
}