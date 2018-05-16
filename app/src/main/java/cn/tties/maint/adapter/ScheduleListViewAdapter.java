package cn.tties.maint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.httpclient.result.QuestionScheduleResult;

/**
 * Created by Justin on 2018/1/8.
 */

public class ScheduleListViewAdapter extends BaseAdapter {

    private List<QuestionScheduleResult> scheduleList;
    private LayoutInflater inflater;

    public ScheduleListViewAdapter(Context context, List<QuestionScheduleResult> mList){
        this.scheduleList = mList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return scheduleList == null ? 0 : scheduleList.size();
    }

    @Override
    public Object getItem(int position) {
        return scheduleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.listview_question_schedule, null);
            convertView.setTag(new Holder(convertView));
        }
        Holder holder = (Holder)convertView.getTag();
        QuestionScheduleResult result = scheduleList.get(position);
        setViewShowHide(holder);
        if(result.isHead()){
            holder.relativeLayoutInfo.setVisibility(View.VISIBLE);
            holder.lineHanding.setVisibility(View.VISIBLE);
            holder.lineHandingPoint.setVisibility(View.VISIBLE);
            holder.textName.setText("处理中");
            holder.textDate.setText(result.getCreateTime());
            holder.textInfo.setText(result.getContent());
        }else if(result.isEnd()){
            holder.relativeLayoutInfo.setVisibility(View.VISIBLE);
            holder.lineEnd.setVisibility(View.VISIBLE);
            holder.lineEndPoint.setVisibility(View.VISIBLE);
            holder.textName.setText("处理完成");
            holder.textDate.setText(result.getCreateTime());
            holder.textInfo.setText(result.getContent());
        } else {
            holder.relativeLayoutInfo.setVisibility(View.GONE);
            holder.lineHanding.setVisibility(View.VISIBLE);
            holder.lineHandingPoint.setVisibility(View.VISIBLE);
            holder.textInfo.setText(result.getContent());
            holder.textDate.setText(result.getCreateTime());
        }
        return convertView;
    }

    private void setViewShowHide(Holder holder) {
        holder.relativeLayoutInfo.setVisibility(View.GONE);
        holder.lineHanding.setVisibility(View.GONE);
        holder.lineHandingPoint.setVisibility(View.GONE);
        holder.lineEnd.setVisibility(View.GONE);
        holder.lineEndPoint.setVisibility(View.GONE);
    }

    class Holder{
        public Holder(View convertView) {
            relativeLayoutInfo = (RelativeLayout)convertView.findViewById(R.id.relativeLayout_info);
            lineHanding = (View)convertView.findViewById(R.id.line_handing);
            lineHandingPoint = (View)convertView.findViewById(R.id.line_handing_point);
            lineEnd = (View)convertView.findViewById(R.id.line_end);
            lineEndPoint = (View)convertView.findViewById(R.id.line_end_point);
            textName = (TextView)convertView.findViewById(R.id.text_name);
            textDate = (TextView)convertView.findViewById(R.id.text_date);
            textInfo = (TextView)convertView.findViewById(R.id.text_info);
        }
        View lineHanding;
        View lineHandingPoint;
        View lineEnd;
        View lineEndPoint;
        TextView textName;
        TextView textDate;
        TextView textInfo;
        RelativeLayout relativeLayoutInfo;
    }
}