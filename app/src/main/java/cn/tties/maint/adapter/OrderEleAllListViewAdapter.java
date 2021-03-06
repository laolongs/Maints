package cn.tties.maint.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.bean.CommonListViewInterface;

/**
 *
 * guojl
 */

public class OrderEleAllListViewAdapter<T extends CommonListViewInterface> extends BaseAdapter {

    private List<T> mDataList;

    @Override
    public int getCount() {
        if (null == this.mDataList) {
            return 0;
        }
        return mDataList.size();
    }
    public void setChecked(T checkEntity) {
        for (T entity : mDataList) {
            entity.setChecked(false);
        }
        checkEntity.setChecked(true);
    }

    public void clearCheck() {
        for (T entity : mDataList) {
            entity.setChecked(false);
        }
        notifyDataSetChanged();
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

    public List<T> getAllChecked() {
        if (mDataList == null) {
            return null;
        }
        List<T> newList = new ArrayList<>();
        for (T entity : mDataList) {
            if (entity.isChecked()) {
                newList.add(entity);
            }
        }
        return newList;
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    public void removeItem(int position){
        mDataList.remove(position);
    }

    public void removeItem(T bean){
        mDataList.remove(bean);
    }

    public void addItem(T position){
        mDataList.add(position);
    }


    public String getItemIds(){
        String itemIds = "";
        for(T item:mDataList){
            itemIds += item.getItemId() + ",";
        }
        return itemIds;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final T item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_equipmentcheckele_item, parent, false);
        }
        TextView textOverhaul = (TextView) convertView.findViewById(R.id.text_name);
        textOverhaul.setText(item.getItemName());
        return convertView;
    }

    public void setmDataList(List<T> mDataList) {
        this.mDataList = mDataList;
    }
}
