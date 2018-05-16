package cn.tties.maint.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.result.TaskResult;

/**
 * 任务配置
 * Created by Justin on 2018/1/12.
 */

public class TaskDialog extends BaseCustomDialog {

    @NotEmpty(message = "电房名称不能为空")
    public ListView taskListView;
    MyAdapter myAdapter;
    View.OnClickListener closeClick;

    public TaskDialog(View.OnClickListener clickListener) {
        super(MyApplication.curActivity, null);
        myAdapter = new MyAdapter(context , new ArrayList<TaskResult>());
        this.closeClick = clickListener;
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_task);
        taskListView = findViewById(R.id.listView_task);
        taskListView.setAdapter(myAdapter);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(closeClick);
        btnCancel.setClickable(false);
        btnCancel.setText("请等待");
        this.setCancelable(false);
    }

    public List<TaskResult> getTaskList() {
       return myAdapter.getMList();
    }

    public void setExecuting() {
        btnCancel.setClickable(false);
        btnCancel.setText("请等待");
    }

    public void setFinish() {
        btnCancel.setClickable(true);
        btnCancel.setText("关闭");
    }

    public void refreshMDataList(List<TaskResult> mDataList) {
        myAdapter.setMList(mDataList);
        myAdapter.notifyDataSetChanged();
    }

    public void changeMsg(long taskId, String msg) {
        List<TaskResult> list = myAdapter.getMList();
        for (TaskResult taskResult : list) {
            if (taskResult.getTaskId() == taskId) {
                taskResult.setMsg(msg);
            }
        }
        myAdapter.notifyDataSetChanged();
    }

    public void setFinish(long taskId) {
        List<TaskResult> list = myAdapter.getMList();
        for (TaskResult taskResult : list) {
            if (taskResult.getTaskId() == taskId) {
                taskResult.setLast(true);
            }
        }
        myAdapter.notifyDataSetChanged();
    }

    public class MyAdapter extends BaseAdapter {

        public List<TaskResult> list;

        public LayoutInflater inflater;

        public MyAdapter(Context context, List<TaskResult> list) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        public void setMList(List<TaskResult> list) {
           this.list = list;
        }

        public List<TaskResult> getMList() {
           return this.list;
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
        public long getItemId(int position) {
            return position;
        }

        @Override
        public TaskResult getItem(int position) {
            return (TaskResult) list.get(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_task, null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.taskName = (TextView) convertView.findViewById(R.id.task_name);
                viewHolder.taskStatus = (TextView) convertView.findViewById(R.id.task_status);
                viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
                convertView.setTag(viewHolder);
            }
            ViewHolder viewHolder = (ViewHolder)convertView.getTag();
            TaskResult taskResult = list.get(position);
            viewHolder.taskName.setText(taskResult.getTaskName());

            viewHolder.progressBar.setVisibility(View.VISIBLE);
            if (taskResult.getLast()) {
                viewHolder.progressBar.setVisibility(View.GONE);
            }
            viewHolder.taskStatus.setText(taskResult.getMsg());
            return convertView;
        }

        public class ViewHolder {
            public TextView taskName;
            public TextView taskStatus;
            public ProgressBar progressBar;
        }

    }
}
