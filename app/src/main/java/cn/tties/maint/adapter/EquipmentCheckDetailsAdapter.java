package cn.tties.maint.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.entity.EquipmentEntity;

/**
 * Created by chensi on 2018/1/9.
 */
public class EquipmentCheckDetailsAdapter<T extends EquipmentLayoutBean> extends RecyclerView.Adapter<EquipmentCheckDetailsAdapter.ViewHolder> {

    private List<EquipmentLayoutBean> mDataList;

    public EquipmentCheckDetailsAdapter() {
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
    public EquipmentCheckDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EquipmentCheckDetailsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_equipmentcheckdetails_item, parent, false));
    }

    @Override
    public void onBindViewHolder(EquipmentCheckDetailsAdapter.ViewHolder holder, int position) {
        holder.setData(mDataList.get(position));
    }

    public EquipmentLayoutBean getItem(int position) {
        if (mDataList == null || mDataList.size() <= position) {
            return null;
        }
        return mDataList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;

        public ViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_name);
        }

        public void setData(EquipmentLayoutBean title) {
            this.textName.setText(title.getTextName());
        }
    }
    public void remove(){
        mDataList.clear();
        notifyDataSetChanged();
    }
}
