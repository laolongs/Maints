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

public class TableCompanyAdapter extends BaseAdapter {

    public List list;
    public LayoutInflater inflater;

    public TableCompanyAdapter() {
    }

    public TableCompanyAdapter(Context context, List<Object> list) {
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
            convertView = inflater.inflate(R.layout.table_company, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.layout_table);

            viewHolder.text1 = (TextView) convertView.findViewById(R.id.text_1);
            viewHolder.text2 = (TextView) convertView.findViewById(R.id.text_2);

            viewHolder.btn1 = (Button) convertView.findViewById(R.id.btn_1);
            viewHolder.btn2 = (Button) convertView.findViewById(R.id.btn_2);
            viewHolder.btn3 = (Button) convertView.findViewById(R.id.btn_3);
            viewHolder.btn4 = (Button) convertView.findViewById(R.id.btn_4);
            convertView.setTag(viewHolder);
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView text1;
        public TextView text2;

        public LinearLayout layout;

        public Button btn1;
        public Button btn2;
        public Button btn3;
        public Button btn4;
    }

}