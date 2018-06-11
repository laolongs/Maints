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
import java.util.Map;

import cn.tties.maint.R;
import cn.tties.maint.bean.CommonListViewInterface;

/**
 * Created by chensi on 2018/1/9.
 */
public class EquipmentRootListViewAdapter<T extends CommonListViewInterface> extends RecyclerView.Adapter<EquipmentRootListViewAdapter.ViewHolder> {

    private List<T> mDataList;

    private int resourceId;

    private Map<Integer, List<CommonListViewInterface>> orgMap;

    public EquipmentRootListViewAdapter() {
        mDataList = new ArrayList<>();
        resourceId = R.layout.listview_equipment_root_tiem;
    }

    public EquipmentRootListViewAdapter(int resourceId) {
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
    public void onBindViewHolder(EquipmentRootListViewAdapter.ViewHolder holder, int position) {
        holder.setData(mDataList.get(position));
        int unselect = Color.parseColor("#BDC8D8");
        int select = Color.parseColor("#ffffff");
        int textselect = Color.parseColor("#1B92EE");
        if (mDataList.get(position).isChecked()) {
            holder.eq_root_ll.setBackgroundColor(select);
            holder.nameText.setTextColor(textselect);
        } else {
            holder.eq_root_ll.setBackgroundColor(unselect);
            holder.nameText.setTextColor(select);
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
        LinearLayout eq_root_ll;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.text_name);
            eq_root_ll = (LinearLayout) itemView.findViewById(R.id.eq_root_ll);
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
