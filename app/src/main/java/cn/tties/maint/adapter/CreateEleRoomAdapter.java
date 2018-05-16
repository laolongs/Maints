package cn.tties.maint.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;

/**
 * Created by Justin on 2018/1/8.
 */

public class CreateEleRoomAdapter extends RecyclerView.Adapter<CreateEleRoomAdapter.ViewHolder> {
    private List<EleRoom> mDataList;

    public CreateEleRoomAdapter() {
        mDataList = new ArrayList<>();
        EleRoom eleRoom = new EleRoom();
        eleRoom.setName("电房");
        mDataList.add(eleRoom);
    }


    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public CreateEleRoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CreateEleRoomAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_equipment_info, parent, false));
    }

    @Override
    public void onBindViewHolder(CreateEleRoomAdapter.ViewHolder holder, int position) {
        holder.setData(mDataList.get(position));
    }

    public EleRoom getItem(int position) {
        if (mDataList == null || mDataList.size() <= position) {
            return null;
        }
        return mDataList.get(position);
    }

    class EleRoom {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;

        public ViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.text_name);
        }

        public void setData(EleRoom title) {
            this.textName.setText(title.getName());
        }
    }
}
