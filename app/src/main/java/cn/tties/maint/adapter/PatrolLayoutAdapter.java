package cn.tties.maint.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewActivity;

import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.tties.maint.R;
import cn.tties.maint.activity.BaseFragment;
import cn.tties.maint.activity.PatrolFragment;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.entity.PatrolItemEntity;
import cn.tties.maint.enums.FaultType;
import cn.tties.maint.enums.OtherPatrolItemType;
import cn.tties.maint.enums.QuestionStatusType;
import cn.tties.maint.holder.PatrolInputBodyItemHolder;
import cn.tties.maint.holder.PatrolInputItemHolder;
import cn.tties.maint.holder.PatrolItemHolder;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.Patrol_inSertQuestionParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.DescriptionResult;
import cn.tties.maint.httpclient.result.LoginResult;
import cn.tties.maint.httpclient.result.PatrolResult;
import cn.tties.maint.httpclient.result.QuestionNewScheduleResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.view.Patrol_NewQuestionDialog;
import cn.tties.maint.view.Patrol_QuestionDialog;
import cn.tties.maint.view.Patrol_SelectMoreDialog;
import cn.tties.maint.view.Question_SelectMoreDialog;
import cn.tties.maint.widget.CustomDatePicker;

public class PatrolLayoutAdapter extends BaseAdapter {
    private static final String TAG = "PatrolLayoutAdapter";
     //没有巡视过得  数据补出来得
    public static final int NOQUESTION = 0;
     // 巡视过得  数据请求回来得
    public static final int QUESTION = 1;
    public static final int NEWQUESTION = 2;
    private List<PatrolItemEntity> beanList;
    OnClick listener;
    BaseFragment mFragment;
    Integer faultType;
    Integer faultTypes;
    int companyEquipmentId,eleAccountId,roomId,workOrderId;
    String companyName;
    @Override
    public int getCount() {
        return beanList==null?0:beanList.size();
    }
    public void setOnClick(OnClick listener){
        this.listener=listener;
    }
    public void setData(int companyEquipmentId,int eleAccountId,int roomId,int workOrderId,String companyName){
        this.companyEquipmentId=companyEquipmentId;
        this.eleAccountId=eleAccountId;
        this.roomId=roomId;
        this.workOrderId=workOrderId;
        this.companyName=companyName;
    }
    @Override
    public int getItemViewType(int position) {
        //这里是根据position返回指定的布局类型，会根据这个返回值加载不同布局
        return beanList.get(position).getInputType();
    }
    @Override
    public int getViewTypeCount() {
        //这里是adapter里有几种布局
        return 3;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void setDataList(List<PatrolItemEntity> beanList) {
        this.beanList = beanList;
    }

    public PatrolLayoutAdapter(List<PatrolItemEntity> beanList, BaseFragment mFragment) {
        this.beanList = beanList;
        this.mFragment = mFragment;
    }
    public List<PatrolItemEntity> getDataList() {
        return beanList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PatrolItemEntity item = beanList.get(position);
        NoQuestionViewHolder noQuestionViewHolder=null;
        QuestionViewHolder questionViewHolder=null;
        NEWQuestionViewHolder newquestionViewHolder=null;
        if (convertView == null) {
            switch (item.getInputType()) {
                case NOQUESTION:
                    convertView = View.inflate(x.app(), R.layout.listview_patrol_noquestion, null);
                    noQuestionViewHolder = new NoQuestionViewHolder(convertView);
                    convertView.setTag(noQuestionViewHolder);
                    break;
                case QUESTION:
                    convertView = View.inflate(x.app(), R.layout.listview_patrol_question, null);
                    questionViewHolder = new QuestionViewHolder(convertView);
                    convertView.setTag(questionViewHolder);
                    break;
                case NEWQUESTION:
                    convertView = View.inflate(x.app(), R.layout.listview_patrol_question, null);
                    newquestionViewHolder = new NEWQuestionViewHolder(convertView);
                    convertView.setTag(newquestionViewHolder);
                    break;

            }
        } else {
            switch (item.getInputType()) {
                case NOQUESTION:
                    noQuestionViewHolder = (NoQuestionViewHolder) convertView.getTag();
                    break;
                case QUESTION:
                    questionViewHolder = (QuestionViewHolder) convertView.getTag();
                    break;
                case NEWQUESTION:
                    newquestionViewHolder = (NEWQuestionViewHolder) convertView.getTag();
                    break;
            }
        }
        final PatrolResult bean = item.getResult();
        switch (item.getInputType()){
            case NOQUESTION:
                noQuestionViewHolder.text_title.setText(item.getTitle());
                if(String.valueOf(item.getFaultTypeString()).equals("一般")){
                    noQuestionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_general);
                    faultType=3;
                }
                if(String.valueOf(item.getFaultTypeString()).equals("重大")){
                    noQuestionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_importcacel);
                    faultType=2;
                }
                if(String.valueOf(item.getFaultTypeString()).equals("紧急")){
                    noQuestionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_ungency);
                    faultType=1;
                }

                noQuestionViewHolder.text_description.setText(item.getFaultHarm());
                //无问题  提交数据  隐藏图片展示 。。。 更新巡视项状态
                noQuestionViewHolder.ll_noquestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /////////     ----------康再写-------
                        sendInsertQuestion(item.getPatrolItemId());
                    }
                });
                //有问题  提交图片文本  。。。
                noQuestionViewHolder.ll_question.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isTemperature;
                        //是否有温度
                        if(item.getTemperature()==1){
                            isTemperature=true;
                        }else{
                            isTemperature=false;
                        }
                        listener.OnClickListenter(isTemperature,item.getPatrolItemId(),faultType);
                    }
                });
                break;
            case QUESTION:
                    questionViewHolder.text_title.setText(item.getTitle());
                        if(String.valueOf(item.getFaultTypeString()).equals("一般")){
                            questionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_general);
                            faultTypes=3;
                        }
                        if(String.valueOf(item.getFaultTypeString()).equals("重大")){
                            questionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_importcacel);
                            faultTypes=2;
                        }
                        if(String.valueOf(item.getFaultTypeString()).equals("紧急")){
                            questionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_ungency);
                            faultTypes=1;
                        }
                    if(bean.getHasquestion()){//true 有问题
                        questionViewHolder.question_imglist.setVisibility(View.VISIBLE);
                        questionViewHolder.text_noquestion.setText("已上报有问题");
                        questionViewHolder.ll_question.setVisibility(View.INVISIBLE);
                        //------------------------------------
//                        缺数据   没有图片集合
//                        if(bean.getQuertion().getStatus()== QuestionStatusType.END.getType()) {//已完成  展示进度的图片和
//                            //图片列表  图片集合盘空
//                            final List<PatrolResult.QuertionBean.QuestionScheduleListBean> scheduleList = new ArrayList<>();
//                            if (bean.getQuertion().getQuestionScheduleList() != null&&bean.getQuertion().getQuestionScheduleList().size()>0) {
//                                scheduleList.add(bean.getQuertion().getQuestionScheduleList().get(bean.getQuertion().getQuestionScheduleList().size()-1));
//                            }
//                            PatrolEndListViewAdapter descriptionAdapter = new PatrolEndListViewAdapter(mFragment, scheduleList);
//                            questionViewHolder.question_imglist.setAdapter(descriptionAdapter);
//                        }
                        //if(item.getStatus()== QuestionStatusType.UNSTART.getType())//未进行
                        if(bean.getQuertion().getStatus()== QuestionStatusType.HANDING.getType()||bean.getQuertion().getStatus()== QuestionStatusType.UNSTART.getType()) {//进行中
                            //图片列表  图片集合盘空
                            final List<PatrolResult.QuertionBean.QuestionDescriptionListBean> descriptionList = new ArrayList<>();
                            if (bean.getQuertion().getQuestionDescriptionList() != null&&bean.getQuertion().getQuestionDescriptionList().size()>0) {
                                descriptionList.add(bean.getQuertion().getQuestionDescriptionList().get(bean.getQuertion().getQuestionDescriptionList().size()-1));
                            }
                            PatrolDescriptionListViewAdapter descriptionAdapter = new PatrolDescriptionListViewAdapter(mFragment, descriptionList);
                            questionViewHolder.question_imglist.setAdapter(descriptionAdapter);
                        }
                        if(bean.getQuertion().getQuestionDescriptionList().size()>1){
                            questionViewHolder.text_more.setVisibility(View.VISIBLE);
                            //查看更多
                            questionViewHolder.text_more.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (NoFastClickUtils.isFastClick()) {
                                        return;
                                    }
                                    Patrol_SelectMoreDialog moreDialog=new Patrol_SelectMoreDialog(mFragment,bean.getQuertion(),item.getTitle(),companyName);
                                    moreDialog.setContentView();
                                    moreDialog.loading();
                                }
                            });
                        }else{
                            questionViewHolder.text_more.setVisibility(View.INVISIBLE);
                        }
                    }else{//false 无问题
                        questionViewHolder.text_noquestion.setText("已上报无问题");
                        questionViewHolder.ll_question.setVisibility(View.VISIBLE);
                        questionViewHolder.question_imglist.setVisibility(View.INVISIBLE);
                        questionViewHolder.text_more.setVisibility(View.INVISIBLE);
                        //重新上报问题
                        questionViewHolder.ll_question.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (NoFastClickUtils.isFastClick()) {
                                    return;
                                }
                                boolean isTemperature;
                                //是否有温度
                                if(item.getTemperature()==1){
                                    isTemperature=true;
                                }else{
                                    isTemperature=false;
                                }
                                listener.OnClickListenter(isTemperature,item.getPatrolItemId(),faultTypes);
                            }
                        });
                    }
                break;
            case NEWQUESTION:
                newquestionViewHolder.text_title.setText("新建问题");
                if(bean.getQuertion().getStatus()== FaultType.FAULT.getType()){//一般
                    newquestionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_general);
                }
                if(bean.getQuertion().getStatus()== FaultType.DANGEROUS.getType()){//重大
                    newquestionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_importcacel);
                }
                if(bean.getQuertion().getStatus()== FaultType.NORLMAL.getType()){//紧急
                    newquestionViewHolder.img_type.setImageResource(R.mipmap.img_patrol_ungency);
                }
                if(bean.getHasquestion()) {//true 有问题
                    newquestionViewHolder.question_imglist.setVisibility(View.VISIBLE);
                    newquestionViewHolder.text_noquestion.setText("已上报有问题");
                    newquestionViewHolder.ll_question.setVisibility(View.INVISIBLE);
                    //------------------------------------
//                        缺数据   没有图片集合
//                if(bean.getQuertion().getStatus()== QuestionStatusType.END.getType()) {//已完成  展示进度的图片和
//                    //图片列表  图片集合盘空
//                    final List<PatrolResult.QuertionBean.QuestionScheduleListBean> scheduleList = new ArrayList<>();
//                    if (bean.getQuertion().getQuestionScheduleList() != null&&bean.getQuertion().getQuestionScheduleList().size()>0) {
//                        scheduleList.add(bean.getQuertion().getQuestionScheduleList().get(bean.getQuertion().getQuestionScheduleList().size()-1));
//                    }
//                    PatrolEndListViewAdapter descriptionAdapter = new PatrolEndListViewAdapter(mFragment, scheduleList);
//                    newquestionViewHolder.question_imglist.setAdapter(descriptionAdapter);
//                }
                    //if(item.getStatus()== QuestionStatusType.UNSTART.getType())//未进行
                    if(bean.getQuertion().getStatus()== QuestionStatusType.HANDING.getType()||bean.getQuertion().getStatus()== QuestionStatusType.UNSTART.getType()) {//进行中
                        //图片列表  图片集合盘空
                        final List<PatrolResult.QuertionBean.QuestionDescriptionListBean> descriptionList = new ArrayList<>();
                        if (bean.getQuertion().getQuestionDescriptionList() != null&&bean.getQuertion().getQuestionDescriptionList().size()>0) {
                            descriptionList.add(bean.getQuertion().getQuestionDescriptionList().get(bean.getQuertion().getQuestionDescriptionList().size()-1));
                        }
                        PatrolDescriptionListViewAdapter descriptionAdapter = new PatrolDescriptionListViewAdapter(mFragment, descriptionList);
                        newquestionViewHolder.question_imglist.setAdapter(descriptionAdapter);
                    }
                    if(bean.getQuertion().getQuestionDescriptionList().size()>1){
                        newquestionViewHolder.text_more.setVisibility(View.VISIBLE);
                        //查看更多
                        newquestionViewHolder.text_more.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Patrol_SelectMoreDialog moreDialog=new Patrol_SelectMoreDialog(mFragment,bean.getQuertion(),"新建问题",companyName);
                                moreDialog.setContentView();
                                moreDialog.loading();
                            }
                        });
                    }else{
                        newquestionViewHolder.text_more.setVisibility(View.INVISIBLE);
                    }
                }else{//不会存在新建无问题的情况

                }
//                //以上报无问题-------------------
//                newquestionViewHolder.ll_noquestion.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                //重新上报问题-----------------
//                newquestionViewHolder.ll_question.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//

                break;
        }
        return convertView;
    }
    public class NoQuestionViewHolder{
            ImageView img_type;
            TextView text_title;
            TextView text_description;
            LinearLayout ll_question;
            LinearLayout ll_noquestion;
        public NoQuestionViewHolder(View view) {
            img_type= view.findViewById(R.id.img_type);
            text_title= view.findViewById(R.id.text_title);
            text_description= view.findViewById(R.id.text_description);
            ll_question= view.findViewById(R.id.ll_question);
            ll_noquestion= view.findViewById(R.id.ll_noquestion);
        }
    }
    public class QuestionViewHolder{
        ImageView img_type;
        TextView text_title;
//        TextView text_description;
        LinearLayout ll_question;
        TextView text_noquestion;
        TextView text_question;
        LinearLayout ll_noquestion;
        ListView question_imglist;
        TextView text_more;
        @SuppressLint("WrongViewCast")
        public QuestionViewHolder(View view) {
            img_type= view.findViewById(R.id.img_type);
            text_title= view.findViewById(R.id.text_title);
//            text_description= view.findViewById(R.id.text_description);
            ll_question= view.findViewById(R.id.ll_question);
            text_noquestion= view.findViewById(R.id.text_noquestion);
            text_question= view.findViewById(R.id.text_question);
            ll_noquestion= view.findViewById(R.id.ll_noquestion);
            question_imglist= view.findViewById(R.id.question_imglist);
            text_more= view.findViewById(R.id.text_more);
        }
    }
    public class NEWQuestionViewHolder{
        ImageView img_type;
        TextView text_title;
//        TextView text_description;
        LinearLayout ll_question;
        TextView text_noquestion;
        TextView text_question;
        LinearLayout ll_noquestion;
        ListView question_imglist;
        TextView text_more;
        @SuppressLint("WrongViewCast")
        public NEWQuestionViewHolder(View view) {
            img_type= view.findViewById(R.id.img_type);
            text_title= view.findViewById(R.id.text_title);
//            text_description= view.findViewById(R.id.text_description);
            ll_question= view.findViewById(R.id.ll_question);
            text_noquestion= view.findViewById(R.id.text_noquestion);
            text_question= view.findViewById(R.id.text_question);
            ll_noquestion= view.findViewById(R.id.ll_noquestion);
            question_imglist= view.findViewById(R.id.question_imglist);
            text_more= view.findViewById(R.id.text_more);
        }
    }
    public interface OnClick{
        public void OnClickListenter(boolean flag,int patrolItemId,int fult);
    }
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }
    public void sendInsertQuestion(int patrolItemId){
        Patrol_inSertQuestionParams params=new Patrol_inSertQuestionParams();
        params.setCompanyEquipmentId(companyEquipmentId);
        params.setEleAccountId(eleAccountId);
        params.setHasquestion(false);
        params.setRoomId(roomId);
        params.setWorkOrderId(workOrderId);
        params.setPatrolItemId(patrolItemId);
        HttpClientSend.getInstance().send(params, new BaseStringCallback(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                BaseResult ret= JsonUtils.deserialize(result, BaseResult.class);
                if(ret.getErrorCode()!=0){
                    Toast.makeText(x.app(), "提交问题失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(x.app(), "提交问题成功", Toast.LENGTH_SHORT).show();
                PatrolFragment.patrolFragmentInstance.queryPatrolList();
            }

        });
    }

}