package cn.tties.maint.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;

/**
 * Created by chensi on 2018/1/9.
 */
public class MyEquipmentCheckEleAdapter<T extends CompanyEquipmentResult> extends RecyclerView.Adapter<MyEquipmentCheckEleAdapter.ViewHolder> {

    private List<T> mDataList;

    public MyEquipmentCheckEleAdapter() {
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
    public MyEquipmentCheckEleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyEquipmentCheckEleAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_equipmentcheckele_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyEquipmentCheckEleAdapter.ViewHolder holder, int position) {
        final T item = mDataList.get(position);
        holder.setData(item);
    }

    public T getItem(int position) {
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

        public void setData(T title) {
            this.textName.setText(title.getItemName());
        }
    }
    public void remove(){
        mDataList.clear();
        notifyDataSetChanged();
    }
}
