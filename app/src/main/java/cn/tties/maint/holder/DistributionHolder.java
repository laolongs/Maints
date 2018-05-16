package cn.tties.maint.holder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonListViewAdapter;
import cn.tties.maint.bean.CommonListViewInterface;

public class DistributionHolder {

    public TextView textLeft;

    public ListView listViewLeft;

    public TextView textRight;

    public ListView listViewRight;

    public ImageView leftIv;

    public ImageView rightIv;

    public CommonListViewAdapter leftAdapter;

    public CommonListViewAdapter rightAdapter;


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
        leftAdapter = new CommonListViewAdapter();
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
        rightAdapter = new CommonListViewAdapter();
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
                leftAdapter.notifyDataSetChanged();
                rightAdapter.notifyDataSetChanged();
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
                rightAdapter.notifyDataSetChanged();
                leftAdapter.notifyDataSetChanged();

            }
        });
    }
}