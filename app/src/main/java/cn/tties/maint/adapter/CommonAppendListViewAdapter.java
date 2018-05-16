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

/**
 * Created by chensi on 2018/1/9.
 */
public class CommonAppendListViewAdapter<T extends CommonListViewInterface> extends RecyclerView.Adapter<CommonAppendListViewAdapter.ViewHolder> {

    private List<CommonListViewInterface> mDataList;

    public CommonAppendListViewAdapter() {
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
    public CommonAppendListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonAppendListViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_common_append, parent, false));
    }

    @Override
    public void onBindViewHolder(CommonAppendListViewAdapter.ViewHolder holder, int position) {
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

        public ViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_name);
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
