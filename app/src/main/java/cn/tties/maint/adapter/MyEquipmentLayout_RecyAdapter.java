package cn.tties.maint.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.tties.maint.R;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.enums.ItemInputType;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.widget.CustomDatePicker;

public class MyEquipmentLayout_RecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int EDITTEXT = 0;
    public static final int LEAF = 3;
    private Context context;
    private CustomDatePicker cuys;
    public LayoutInflater inflater;
    private List<EquipmentLayoutBean> beanList;

    public MyEquipmentLayout_RecyAdapter(Context context, List<EquipmentLayoutBean> beanList) {
        this.beanList = beanList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==EDITTEXT){
            return new MyOtherViewHolder(inflater.inflate(R.layout.activity_equipment_details_item_other, parent, false));
        }
        if(viewType==LEAF){
            return new MyViewHolder(inflater.inflate(R.layout.activity_equipment_details_item, parent, false));
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyOtherViewHolder) {

            //清除焦点
            ((MyOtherViewHolder) holder).othervalue.clearFocus();
            ((MyOtherViewHolder) holder).othervalue.setFocusable(false);
            //先清除之前的文本改变监听
            if (((MyOtherViewHolder) holder).othervalue.getTag() instanceof TextWatcher) {
                ((MyOtherViewHolder) holder).othervalue.removeTextChangedListener((TextWatcher) (((MyOtherViewHolder) holder).othervalue.getTag()));
            }
            ((MyOtherViewHolder) holder).othervalue.setOnClickListener(null);
            ((MyOtherViewHolder) holder).othervalue.setOnFocusChangeListener(null);
            //设置数据
            ((MyOtherViewHolder) holder).othername.setText(TextUtils.isEmpty(beanList.get(position).getTextName()) ? "" : beanList.get(position).getTextName());
            ((MyOtherViewHolder) holder).othervalue.setText(TextUtils.isEmpty(beanList.get(position).getValue()) ? "" : beanList.get(position).getValue());


        }
        if (holder instanceof MyViewHolder) {
            //设置数据
            ((MyViewHolder) holder).name.setText(TextUtils.isEmpty(beanList.get(position).getTextName()) ? "" : beanList.get(position).getTextName());
            ((MyViewHolder) holder).info.removeAllViews();
            ((MyViewHolder) holder).info.clearFocus();
            for (final EquipmentLayoutBean layoutBean : beanList.get(position).getChildrenList()) {
                View layout = View.inflate(x.app(), R.layout.activity_equipment_details_item_other, null);
                MyOtherViewHolder otherholder = new MyOtherViewHolder(layout);
                otherholder.othername.setText(layoutBean.getTextName());
                otherholder.othervalue.setFocusable(false);
                otherholder.othervalue.setText(layoutBean.getValue());
                otherholder.othervalue.setOnClickListener(null);
                otherholder.othervalue.setOnFocusChangeListener(null);
                otherholder.othervalue.clearFocus();
                if (otherholder.othervalue.getTag() instanceof TextWatcher) {
                    otherholder.othervalue.removeTextChangedListener((TextWatcher) (otherholder.othervalue.getTag()));
                }
                ((MyViewHolder) holder).info.clearFocus();
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
        EditText othervalue;
        public MyOtherViewHolder(View itemView) {
            super(itemView);
            othername= itemView.findViewById(R.id.details_other_text_name);
            othervalue= itemView.findViewById(R.id.details_other_edit_value);
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