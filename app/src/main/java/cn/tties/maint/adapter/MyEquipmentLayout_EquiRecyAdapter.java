package cn.tties.maint.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.widget.CustomDatePicker;

public class MyEquipmentLayout_EquiRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int EDITTEXT = 0;
    public static final int LEAF = 3;
    private Context context;
    private CustomDatePicker cuys;
    public LayoutInflater inflater;
    private List<EquipmentLayoutBean> beanList;

    public MyEquipmentLayout_EquiRecyAdapter(Context context, List<EquipmentLayoutBean> beanList) {
        this.beanList = beanList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==EDITTEXT){
            return new MyOtherViewHolders(inflater.inflate(R.layout.activity_newequipment_details_items, parent, false));
        }
        if(viewType==LEAF){
            return new MyViewHolder(inflater.inflate(R.layout.activity_newequipment_details_item, parent, false));
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyOtherViewHolders) {
            for (final EquipmentLayoutBean layoutBean : beanList.get(position).getChildrenList()) {
                View layout = View.inflate(x.app(), R.layout.activity_newequipment_details_item_other, null);
                MyOtherViewHolder otherholder = new MyOtherViewHolder(layout);
                otherholder.othername.setText(layoutBean.getTextName());
                otherholder.othervalue.setText(layoutBean.getValue());
                ((MyOtherViewHolders) holder).infos.addView(layout);
            }

//           //设置数据
//            ((MyOtherViewHolder) holder).othername.setText(TextUtils.isEmpty(beanList.get(position).getTextName()) ? "" : beanList.get(position).getTextName());
//            ((MyOtherViewHolder) holder).othervalue.setText(TextUtils.isEmpty(beanList.get(position).getValue()) ? "" : beanList.get(position).getValue());


        }
        if (holder instanceof MyViewHolder) {
            //设置数据
            ((MyViewHolder) holder).name.setText(TextUtils.isEmpty(beanList.get(position).getTextName()) ? "" : beanList.get(position).getTextName());
            ((MyViewHolder) holder).info.removeAllViews();
            for (final EquipmentLayoutBean layoutBean : beanList.get(position).getChildrenList()) {
                View layout = View.inflate(x.app(), R.layout.activity_newequipment_details_item_other, null);
                MyOtherViewHolder otherholder = new MyOtherViewHolder(layout);
                otherholder.othername.setText(layoutBean.getTextName());
                otherholder.othervalue.setText(layoutBean.getValue());
                ((MyViewHolder) holder).info.addView(layout);
            }
        }
    }
    @Override
    public int getItemViewType(int position) {
        if(beanList.get(position).getType()==EDITTEXT){
            return EDITTEXT;
        }
        if(beanList.get(position).getType()==LEAF){
            return LEAF;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return beanList==null?0:beanList.size();
    }

    public class MyOtherViewHolder extends RecyclerView.ViewHolder{
        TextView othername;
        TextView othervalue;
        public MyOtherViewHolder(View itemView) {
            super(itemView);
            othername= itemView.findViewById(R.id.details_other_text_name);
            othervalue= itemView.findViewById(R.id.details_other_edit_value);
        }
    }
    public class MyOtherViewHolders extends RecyclerView.ViewHolder{
        LinearLayout infos;

        public MyOtherViewHolders(View itemView) {
            super(itemView);
            infos= itemView.findViewById(R.id.details_layout_infos);
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        LinearLayout info;
        public MyViewHolder(View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.details_text_name);
            info= itemView.findViewById(R.id.details_layout_info);
        }
    }

}