package cn.tties.maint.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.bean.CommonListViewInterface;

/**
 * Created by chensi on 2018/1/9.
 */
public class EquipmentAddressListViewAdapter<T extends CommonListViewInterface> extends RecyclerView.Adapter<EquipmentAddressListViewAdapter.ViewHolder> {

    private List<CommonListViewInterface> mDataList;

    public EquipmentAddressListViewAdapter() {
        mDataList = new ArrayList<>();
    }

    public void setmDataList(List list) {
        this.mDataList = list;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public EquipmentAddressListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EquipmentAddressListViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_equipment_address, parent, false));
    }

    @Override
    public void onBindViewHolder(EquipmentAddressListViewAdapter.ViewHolder holder, int position) {
        holder.setData(mDataList.get(position));
    }

    public CommonListViewInterface getItem(int position) {
        if (mDataList == null || mDataList.size() <= position) {
            return null;
        }
        return mDataList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        LinearLayout append_lv2_ll;

        public ViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_name);
            append_lv2_ll = (LinearLayout) itemView.findViewById(R.id.append_lv2_ll);
        }

        public void setData(CommonListViewInterface title) {
            this.textName.setText(title.getItemName());
        }
    }
    public void remove(){
        mDataList.clear();
        notifyDataSetChanged();
    }
}
