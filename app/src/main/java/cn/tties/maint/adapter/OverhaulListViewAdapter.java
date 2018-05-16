package cn.tties.maint.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.x;

import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.activity.OverhaulFragment;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.Add0verHaulRecordParams;
import cn.tties.maint.httpclient.result.OverhaulResult;

/**
 *
 * Created by lizhen on 2018/1/9.
 */

public class OverhaulListViewAdapter<T extends OverhaulResult> extends BaseAdapter {

    private OverhaulFragment mOverhaulFragment;
    private List<T> mDataList;

    public OverhaulListViewAdapter(OverhaulFragment overhaulFragment) {
        this.mOverhaulFragment = overhaulFragment;
    }
    @Override
    public int getCount() {
        if (null == this.mDataList) {
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final T item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_overhaul, parent, false);
        }
        TextView textOverhaul = (TextView) convertView.findViewById(R.id.text_overhaul);
        Button botton = convertView.findViewById(R.id.but_button);
        textOverhaul.setText(item.getItemContent());
        if(item.getFinish()){
            botton.setVisibility(View.GONE);
        }else{
            botton.setVisibility(View.VISIBLE);
            botton.setText("处理完成");
            botton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OverhaulResult overhaulResult = item;
                    if(overhaulResult.getWorkOrderId()==null){
                        Toast.makeText(x.app(), "暂未开始检修", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Add0verHaulRecordParams add0verHaulRecordParams = new Add0verHaulRecordParams();
                    add0verHaulRecordParams.setEleAccountId(overhaulResult.getEleAccountId());
                    add0verHaulRecordParams.setIsdeal(true);
                    add0verHaulRecordParams.setOverhaulItemId(overhaulResult.getOverhaulItemId());
                    add0verHaulRecordParams.setWorkOrderId(overhaulResult.getWorkOrderId());
                    if(item.getIsRoom()){
                        add0verHaulRecordParams.setRoomId(overhaulResult.getComEquOrRoomId());
                    }else{
                        add0verHaulRecordParams.setCompanyEquipmentId(overhaulResult.getComEquOrRoomId());
                    }
                    HttpClientSend.getInstance().send(add0verHaulRecordParams, new BaseAlertCallback("处理成功","处理失败"){
                        @Override
                        public void onSuccessed(String result) {
                            mOverhaulFragment.getOverhaulItemList();
                        }
                    });
                }
            });
        }
        return convertView;
    }

    public void setmDataList(List<T> mDataList) {
        this.mDataList = mDataList;
    }
}
