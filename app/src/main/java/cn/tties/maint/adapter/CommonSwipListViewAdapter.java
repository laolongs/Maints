package cn.tties.maint.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.tties.maint.R;
import cn.tties.maint.bean.CommonListViewInterface;

/**
 * Created by chensi on 2018/1/9.
 */
public class CommonSwipListViewAdapter<T extends CommonListViewInterface> extends RecyclerView.Adapter<CommonSwipListViewAdapter.ViewHolder> {

    private List<T> mDataList;

    private int resourceId;

    private Map<Integer, List<CommonListViewInterface>> orgMap;

    public CommonSwipListViewAdapter() {
        mDataList = new ArrayList<>();
        resourceId = R.layout.listview_common_arrow;
    }

    public CommonSwipListViewAdapter(int resourceId) {
        this.resourceId = resourceId;
    }

    public void setMap(Map<Integer, List<CommonListViewInterface>> orgMap) {
        this.orgMap = orgMap;
    }


    public Map<Integer, List<CommonListViewInterface>> getMap() {
        return orgMap;
    }

    public List<T> getMList() {
        return mDataList;
    }

    public void clearCheck() {
        for (T entity : mDataList) {
            entity.setChecked(false);
        }
        notifyDataSetChanged();
    }

    public void setmDataList(List<T> newDataList) {
        if (mDataList != null) {
            for (CommonListViewInterface bean : mDataList) {
                for (CommonListViewInterface newBean : newDataList) {
                    if (bean.getItemId() == newBean.getItemId() && bean.isChecked()) {
                        newBean.setChecked(true);
                        break;
                    }
                }
            }
        }
        mDataList = newDataList;
    }

    public T getChecked() {
        if (mDataList == null) {
            return null;
        }
        for (T entity : mDataList) {
            if (entity.isChecked()) {
                return entity;
            }
        }
        return null;
    }

    public void setChecked(T checkEntity) {
        for (T entity : mDataList) {
            entity.setChecked(false);
        }
        checkEntity.setChecked(true);
    }


    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false));
    }

    @Override
    public void onBindViewHolder(CommonSwipListViewAdapter.ViewHolder holder, int position) {
        holder.setData(mDataList.get(position));
        if (mDataList.get(position).isChecked()) {
            holder.selView.setVisibility(View.VISIBLE);
        } else {
            holder.selView.setVisibility(View.INVISIBLE);
        }
    }

    public T getItem(int position) {
        if (mDataList == null || mDataList.size() <= position) {
            return null;
        }
        return mDataList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        View selView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.text_name);
            selView = (View) itemView.findViewById(R.id.view_sel);
        }

        public void setData(T title) {
            this.nameText.setText(title.getItemName());
        }
    }
    public void remove(){
        mDataList.clear();
        notifyDataSetChanged();
    }
}
