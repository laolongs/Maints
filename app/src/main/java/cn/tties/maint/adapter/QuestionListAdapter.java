package cn.tties.maint.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tties.maint.R;
import cn.tties.maint.activity.QuestionFragment;
import cn.tties.maint.bean.UserInfoBean;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.dao.PatrolItemDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.PatrolItemEntity;
import cn.tties.maint.enums.OtherPatrolItemType;
import cn.tties.maint.enums.QuestionStatusType;
import cn.tties.maint.httpclient.result.AdviceResult;
import cn.tties.maint.httpclient.result.DescriptionResult;
import cn.tties.maint.httpclient.result.QuertionResult;
import cn.tties.maint.util.AppUtils;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.view.AdviceDialog;
import cn.tties.maint.view.ScheduleDialog;

/**
 * 问题Adapter.
 * Created by chensi on 2018/1/11.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {

    public List<QuertionResult> mQuestionList;
    public LayoutInflater inflater;
    private Map<Integer, String> otherEquipType;
    private QuestionFragment mFragment;

    public QuestionListAdapter(QuestionFragment baseFragment, List<QuertionResult> questionList) {
        this.mQuestionList = questionList;
        inflater = LayoutInflater.from(baseFragment.getContext());
        mFragment = baseFragment;

        otherEquipType = new HashMap<Integer, String>();
        List<EquipmentEntity> baseEquipList = EquipmentDao.getInstance().queryByPid(-1);
        for (EquipmentEntity entity : baseEquipList) {
            for (OtherPatrolItemType e : OtherPatrolItemType.values()) {
                if (e.getInfo().equals(entity.getEquipmentName())) {
                    otherEquipType.put(entity.getEquipmentId(), e.getInfo());
                }
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.listview_question_item, parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final QuertionResult result = mQuestionList.get(position);
        //设备类型名称
        PatrolItemEntity item = PatrolItemDao.getInstance().queryByPatrolItemId(result.getPatrolItemId());
        if (otherEquipType.containsKey(item.getEquipmentId())) {
            holder.textKindName.setText(otherEquipType.get(item.getEquipmentId()));
        } else {
            holder.textKindName.setText("设备");
        }
        //公司名称
        holder.textCompanyName.setText(result.getCompanyName());
        //引导
        String descriotion = result.getDescription();
        if (!StringUtil.isEmpty(descriotion)) {
            descriotion = descriotion.substring(1, descriotion.length() - 1).replaceAll(",", ">");
        }
        holder.textGuide.setText(descriotion);
        //问题描述
        final List<DescriptionResult> descriptionList = new ArrayList<>();
        DescriptionResult res = new DescriptionResult();
        res.setContent(result.getTitle());
        descriptionList.add(res);
        if (result.getDescriptionList() != null) {
            descriptionList.addAll(result.getDescriptionList());
        }
//        DescriptionListViewAdapter descriptionAdapter = new DescriptionListViewAdapter(mFragment, descriptionList);
//        holder.listViewDescription.setAdapter(descriptionAdapter);
        AppUtils.setListViewHeight(holder.listViewDescription, 30);
        //缺陷类型
        holder.textType.setText(result.getFaultTypeType());
        //缺陷危害
        holder.textFaultHarm.setText(result.getFaultHarm());
        //处理建议
        List<String> adviceList = new ArrayList<>();
        adviceList.add(result.getDealDesc());
        if (result.getAdviceList() != null) {
            for (AdviceResult result1 : result.getAdviceList()) {
                adviceList.add(result1.getContent());
            }
        }
        AdviceAdapter adviceAdapter = new AdviceAdapter(x.app(), adviceList);
        holder.listViewAdvice.setAdapter(adviceAdapter);
        AppUtils.setListViewHeight(holder.listViewAdvice);
        //处理进度
        if (result.getScheduleList() != null && result.getScheduleList().size() > 0) {
            result.getScheduleList().get(0).setHead(true);
            if (result.getStatus() == QuestionStatusType.END.getType()) {
                result.getScheduleList().get( result.getScheduleList().size() -1).setEnd(true);
            }
            ScheduleListViewAdapter adapter = new ScheduleListViewAdapter(mFragment.getContext(), result.getScheduleList());
            holder.listViewSehedule.setAdapter(adapter);
            AppUtils.setListViewHeight(holder.listViewSehedule);
        }
        //设置按钮点击事件
        setBtnClick(holder, result);
        if (result.getStatus() == QuestionStatusType.END.getType()) {
            holder.btnDescription.setVisibility(View.GONE);
            holder.btnHandle.setVisibility(View.GONE);
            holder.btnAdvice.setVisibility(View.GONE);
        }
    }

    /**
     *  按钮点击事件
     * @param holder ViewHolder
     * @param result QuertionResult
     */
    private void setBtnClick(final ViewHolder holder,  final QuertionResult result ) {
        holder.btnDescription.setVisibility(View.VISIBLE);
        holder.btnHandle.setVisibility(View.VISIBLE);
        holder.btnAdvice.setVisibility(View.VISIBLE);
        final UserInfoBean bean = MyApplication.getUserInfo();
        //问题描述
        holder.btnDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mFragment.openDescriptionDialog(bean.getStaffId(), result.getQuestionId());
            }
        });
        //进度操作
        holder.btnHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ScheduleDialog dialog = new ScheduleDialog(bean.getStaffId(), result.getQuestionId());
                dialog.loading();
            }
        });
        //处理建议
        holder.btnAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AdviceDialog dialog = new AdviceDialog(bean.getStaffId(), result.getQuestionId());
                dialog.loading();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuestionList == null ? 0 : mQuestionList.size();
    }


    public QuertionResult getItem(int position) {
        try {
            return mQuestionList.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            this.textKindName = itemView.findViewById(R.id.text_kind_name);
            this.textCompanyName = itemView.findViewById(R.id.text_company_name);
            this.textGuide = itemView.findViewById(R.id.text_guide);
            this.textType = itemView.findViewById(R.id.text_type);
            this.textFaultHarm = itemView.findViewById(R.id.text_fault_harm);
            this.btnDescription = itemView.findViewById(R.id.btn_description);
            this.btnHandle = itemView.findViewById(R.id.btn_handle);
            this.btnAdvice = itemView.findViewById(R.id.btn_advice);
            this.listViewAdvice = itemView.findViewById(R.id.listview_advice);
            this.listViewDescription = itemView.findViewById(R.id.listview_description);
            this.listViewSehedule = itemView.findViewById(R.id.listView_sehedule);
        }

        public TextView textKindName;
        public TextView textCompanyName;
        public TextView textGuide;
        public TextView textType;
        public TextView textFaultHarm;

        public Button btnDescription;
        public Button btnHandle;
        public Button btnAdvice;
        public ListView listViewAdvice;
        public ListView listViewDescription;
        public ListView listViewSehedule;
    }

    public void setmQuestionList(List<QuertionResult> mQuestionList) {
        this.mQuestionList = mQuestionList;
        this.notifyDataSetChanged();
    }

    class AdviceAdapter extends BaseAdapter {
        public List<String> list;
        public LayoutInflater inflater;

        public AdviceAdapter(Context context, List<String> list) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        public void setList(List list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            int ret = 0;
            if (list != null) {
                ret = list.size();
            }
            return ret;
        }

        @Override
        public String getItem(int position) {
            try {
                return list.get(position);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String result = list.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_advice, parent,false);//解决宽度不能铺满  
                AdviceAdapter.ViewHolder viewHolder = new AdviceAdapter.ViewHolder();
                viewHolder.textAdvice = (TextView) convertView.findViewById(R.id.text_advice);
                convertView.setTag(viewHolder);
            }
            AdviceAdapter.ViewHolder viewHolder = (AdviceAdapter.ViewHolder)convertView.getTag();
            viewHolder.textAdvice.setText(result);
            return convertView;
        }

        public class ViewHolder {
            public TextView textAdvice;
        }
    }
}