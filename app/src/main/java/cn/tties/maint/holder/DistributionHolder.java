package cn.tties.maint.holder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.AllocationLeftListViewAdapter;
import cn.tties.maint.adapter.AllocationRightListViewAdapter;
import cn.tties.maint.adapter.CommonListViewAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.EventKind;

public class DistributionHolder {

    public TextView textLeft;

    public ListView listViewLeft;

    public TextView textRight;

    public ListView listViewRight;

    public ImageView leftIv;

    public ImageView rightIv;

    public AllocationLeftListViewAdapter leftAdapter;

    public AllocationRightListViewAdapter rightAdapter;

    public boolean isEditor=false;

    public DistributionHolder(View contentView) {
        this.textLeft = (TextView) contentView.findViewById(R.id.text_left);
        this.listViewLeft = (ListView) contentView.findViewById(R.id.listview_left);
        this.textRight = (TextView) contentView.findViewById(R.id.text_right);
        this.listViewRight = (ListView) contentView.findViewById(R.id.listview_right);
        this.leftIv = (ImageView) contentView.findViewById(R.id.button_left);
        this.rightIv = (ImageView) contentView.findViewById(R.id.button_right);
        initListView();
        initButton();
    }

    private void initListView() {
        leftAdapter = new AllocationLeftListViewAdapter();
        listViewLeft.setAdapter(leftAdapter);
        listViewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CommonListViewInterface selEntity = leftAdapter.getItem(position);
                if (selEntity.isChecked()) {
                    selEntity.setChecked(false);
                } else {
                    selEntity.setChecked(true);
                }
                leftAdapter.notifyDataSetChanged();
            }
        });
        rightAdapter = new AllocationRightListViewAdapter();
        listViewRight.setAdapter(rightAdapter);
        listViewRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CommonListViewInterface selEntity = rightAdapter.getItem(position);
                if (selEntity.isChecked()) {
                    selEntity.setChecked(false);
                } else {
                    selEntity.setChecked(true);
                }
                rightAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initButton() {
        leftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CommonListViewInterface> checkList = rightAdapter.getAllChecked();
                if (checkList.size() == 0) {
                    return;
                }
                for (CommonListViewInterface checkBean : checkList) {
                    checkBean.setChecked(false);
                    leftAdapter.addItem(checkBean);
                    rightAdapter.removeItem(checkBean);
                }
                isEditor=true;
                leftAdapter.notifyDataSetChanged();
                rightAdapter.notifyDataSetChanged();
                EventBusBean eventBusBean=new EventBusBean();
                eventBusBean.setKind(EventKind.EVENT_COMPANY_ELEISEDITOR);
                eventBusBean.setSuccess(isEditor);
                EventBus.getDefault().post(eventBusBean);
            }
        });
        rightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CommonListViewInterface> checkList = leftAdapter.getAllChecked();
                if (checkList.size() == 0) {
                    return;
                }
                for (CommonListViewInterface checkBean : checkList) {
                    checkBean.setChecked(false);
                    rightAdapter.addItem(checkBean);
                    leftAdapter.removeItem(checkBean);
                }
                isEditor=true;
                rightAdapter.notifyDataSetChanged();
                leftAdapter.notifyDataSetChanged();
                EventBusBean eventBusBean=new EventBusBean();
                eventBusBean.setKind(EventKind.EVENT_COMPANY_ELEISEDITOR);
                eventBusBean.setSuccess(isEditor);
                EventBus.getDefault().post(eventBusBean);
            }
        });

    }
}