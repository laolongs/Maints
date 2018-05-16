package cn.tties.maint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.tties.maint.R;

public class TableOrderAdapter extends BaseAdapter {

    public List list;
    public LayoutInflater inflater;

    public TableOrderAdapter() {
    }

    public TableOrderAdapter(Context context, List<Object> list)  {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (list != null) {
            ret = list.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        try {
            return list.get(position);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getConvertView(convertView);
        return convertView;
    }

    public View getConvertView(View convertView) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.table_order, null);
            TableOrderAdapter.ViewHolder viewHolder = new TableOrderAdapter.ViewHolder();
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.layout_table);

            viewHolder.textWorkType = (TextView) convertView.findViewById(R.id.text_work_type);
            viewHolder.textCompanyName = (TextView) convertView.findViewById(R.id.text_company_name);
            viewHolder.textCompanyAddr = (TextView) convertView.findViewById(R.id.text_company_addr);
            viewHolder.textStartDate = (TextView) convertView.findViewById(R.id.text_start_date);
            viewHolder.textBusinessName = (TextView) convertView.findViewById(R.id.text_business_name);
            viewHolder.textBusinessTel = (TextView) convertView.findViewById(R.id.text_business_tel);
            viewHolder.textFinanceName = (TextView) convertView.findViewById(R.id.text_finance_name);
            viewHolder.textFinanceTel = (TextView) convertView.findViewById(R.id.text_finance_tel);
            viewHolder.textTechName = (TextView) convertView.findViewById(R.id.text_tech_name);
            viewHolder.textTechTel = (TextView) convertView.findViewById(R.id.text_tech_tel);
            viewHolder.textXunshiCount = (TextView) convertView.findViewById(R.id.text_xunshi_count);
            viewHolder.textCount = (TextView) convertView.findViewById(R.id.text_count);

            viewHolder.btnSave = (Button) convertView.findViewById(R.id.btn_save);
            convertView.setTag(viewHolder);
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView textWorkType;
        public TextView textCompanyName;
        public TextView textCompanyAddr;
        public TextView textStartDate;
        public TextView textBusinessName;
        public TextView textBusinessTel;
        public TextView textFinanceName;
        public TextView textFinanceTel;
        public TextView textTechName;
        public TextView textTechTel;
        public TextView textXunshiCount;
        public TextView textCount;
        public LinearLayout layout;

        public Button btnSave;
    }

}