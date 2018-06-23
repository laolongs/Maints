package cn.tties.maint.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewActivity;

import org.w3c.dom.Text;
import org.xutils.x;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tties.maint.R;
import cn.tties.maint.activity.BaseFragment;
import cn.tties.maint.activity.QuestionFragment;
import cn.tties.maint.bean.UserInfoBean;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.dao.PatrolItemDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.PatrolItemEntity;
import cn.tties.maint.enums.FaultType;
import cn.tties.maint.enums.OtherPatrolItemType;
import cn.tties.maint.enums.QuestionStatusType;
import cn.tties.maint.httpclient.result.AdviceResult;
import cn.tties.maint.httpclient.result.DescriptionResult;
import cn.tties.maint.httpclient.result.PatrolResult;
import cn.tties.maint.httpclient.result.QuertionNewResult;
import cn.tties.maint.httpclient.result.QuertionResult;
import cn.tties.maint.httpclient.result.QuestionNewScheduleResult;
import cn.tties.maint.httpclient.result.QuestionScheduleResult;
import cn.tties.maint.util.AppUtils;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.view.AdviceDialog;
import cn.tties.maint.view.Question_DescriptionDialog;
import cn.tties.maint.view.Question_SelectMoreDialog;
import cn.tties.maint.view.ScheduleDialog;

/**
 * 问题Adapter.
 * Created by chensi on 2018/1/11.
 */

public class QuestionNewListAdapter extends BaseAdapter {
    private static final String TAG = "QuestionNewListAdapter";
    private List<QuertionNewResult.ResultBean.QuertionListBean> beanList;
    BaseFragment mFragment;
    private Question_DescriptionDialog dialog;
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
    public void setDataList(List<QuertionNewResult.ResultBean.QuertionListBean> beanList) {
        this.beanList = beanList;
    }
    public QuestionNewListAdapter(BaseFragment mFragment) {
        this.mFragment = mFragment;
    }
    public List<QuertionNewResult.ResultBean.QuertionListBean> getDataList() {
        return beanList;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            final QuertionNewResult.ResultBean.QuertionListBean item = beanList.get(position);
            QuestionViewHolder questionViewHolder=null;
            if (convertView == null) {
                convertView = View.inflate(x.app(), R.layout.listview_newquestion, null);
                questionViewHolder = new QuestionViewHolder(convertView);
                convertView.setTag(questionViewHolder);
            } else {
                questionViewHolder = (QuestionViewHolder) convertView.getTag();
            }
            if(!item.getCompanyShortName().isEmpty()){
                questionViewHolder.text_company.setText(item.getCompanyShortName()+"");
            }
            if(!item.getCreateTime().isEmpty()){
                questionViewHolder.text_time.setText(item.getCreateTime()+"");
            }
            //该设备巡视项目
            PatrolItemEntity patrolItemEntity = PatrolItemDao.getInstance().queryByPatrolItemId(item.getPatrolItemId());
            if(patrolItemEntity!=null){
                questionViewHolder.text_title.setText(patrolItemEntity.getTitle());
            }else{
                questionViewHolder.text_title.setText("新建问题");
            }
            //地址
            String substring = StringUtil.substring(item.getDescription(), 1, item.getDescription().length()-1);
            Log.i(TAG, "getView: substring"+item.getDescription().length());
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
            if(item.getFault_type()==FaultType.FAULT.getType()){
                questionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_general);
            }
            if(item.getFault_type()==FaultType.DANGEROUS.getType()){
                questionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_importcacel);
            }
            if(item.getFault_type()==FaultType.NORLMAL.getType()){
                questionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_ungency);
            }

            if(item.getStatus()== QuestionStatusType.END.getType()) {//已完成  展示进度的图片和
                //图片列表  图片集合盘空
                final List<QuestionNewScheduleResult> scheduleList = new ArrayList<>();
                if (item.getQuestionScheduleList() != null&&item.getQuestionScheduleList().size()>0) {
                    scheduleList.add(item.getQuestionScheduleList().get(item.getQuestionScheduleList().size()-1));
                }
                QuestionEndListViewAdapter descriptionAdapter = new QuestionEndListViewAdapter(mFragment, scheduleList);
                questionViewHolder.question_imglist.setAdapter(descriptionAdapter);
            }
        //if(item.getStatus()== QuestionStatusType.UNSTART.getType())//未进行
            if(item.getStatus()== QuestionStatusType.HANDING.getType()||item.getStatus()== QuestionStatusType.UNSTART.getType()) {//进行中
                //图片列表  图片集合盘空
                final List<DescriptionResult> descriptionList = new ArrayList<>();
                if (item.getQuestionDescriptionList() != null&&item.getQuestionDescriptionList().size()>0) {
                    descriptionList.add(item.getQuestionDescriptionList().get(item.getQuestionDescriptionList().size()-1));
                }
                DescriptionListViewAdapter descriptionAdapter = new DescriptionListViewAdapter(mFragment, descriptionList);
                questionViewHolder.question_imglist.setAdapter(descriptionAdapter);
            }
            if(item.getQuestionDescriptionList().size()>1){
                questionViewHolder.text_more.setVisibility(View.VISIBLE);
                //查看更多
                questionViewHolder.text_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Question_SelectMoreDialog moreDialog=new Question_SelectMoreDialog(mFragment,item.getQuestionDescriptionList());
                        moreDialog.setContentView();
                        moreDialog.loading();
                    }
                });
            }else{
                questionViewHolder.text_more.setVisibility(View.GONE);
            }
            //问题描述
            questionViewHolder.btn_description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = new Question_DescriptionDialog(mFragment,item.getQuestionId());
                    dialog.setContentView();
                    dialog.loading();
                }
            });

        return convertView;
    }
    public class QuestionViewHolder{
        ImageView img_type;
        TextView text_title;
        TextView text_schedule;
        ListView question_imglist;
        TextView text_time;
        TextView text_more;
        TextView text_company;
        TextView text_address1;
        TextView text_address2;
        TextView text_address3;
        LinearLayout btn_description;
        @SuppressLint("WrongViewCast")
        public QuestionViewHolder(View view) {
            img_type= view.findViewById(R.id.img_type);
            text_title= view.findViewById(R.id.text_title);
            text_schedule= view.findViewById(R.id.text_schedule);
            question_imglist= view.findViewById(R.id.question_imglist);
            text_time= view.findViewById(R.id.text_time);
            text_more= view.findViewById(R.id.text_more);
            text_company= view.findViewById(R.id.text_company);
            text_address1= view.findViewById(R.id.text_address1);
            text_address2= view.findViewById(R.id.text_address2);
            text_address3= view.findViewById(R.id.text_address3);
            btn_description= view.findViewById(R.id.btn_description);
        }
    }

    public void showDialog(List<ImageItem> itemList){
        dialog.setImages(itemList);
    }
    public void showdeleteDialog(){
        dialog.deleteImage();
    }
}