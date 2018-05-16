package cn.tties.maint.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import cn.tties.maint.R;
import cn.tties.maint.activity.AmmeterFragment;

/**
 * Created by Justin on 2018/1/12.
 */

public class MeterInfoDialog extends BaseCustomDialog {

    public AmmeterFragment ammeterFragment;
    private  List<JsonElement> list2;
    MyMeterAdapter  myMeterAdapter;
    public ListView meterListView;

    public MeterInfoDialog(AmmeterFragment ammeterFragment, List<JsonElement> list2, View.OnClickListener clickListener) {
        super(ammeterFragment.getActivity(), clickListener);
        this.ammeterFragment = ammeterFragment;
        this.list2=list2;
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_meter_info);
        meterListView = findViewById(R.id.listView_meter);
        MyMeterAdapter AdapterD = new MyMeterAdapter(ammeterFragment.getActivity(),list2);
        meterListView.setAdapter(AdapterD);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeterInfoDialog.this.dismiss();
            }
        });
    }

    public void refreshMDataList(List<JsonElement> DataList) {
        myMeterAdapter.setMList(DataList);
        myMeterAdapter.notifyDataSetChanged();
    }

    public class MyMeterAdapter extends BaseAdapter {

        public List<JsonElement> list;

        public LayoutInflater inflater;

        public MyMeterAdapter(Context context, List<JsonElement> list) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        public void setMList(List<JsonElement> list) {
            this.list = list;
        }

        public List<JsonElement> getMList() {
            return this.list;
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
        public long getItemId(int position) {
            return position;
        }

        @Override
        public JsonElement getItem(int position) {
            return (JsonElement) list.get(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_meter, null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.textDate = (TextView) convertView.findViewById(R.id.text_date);
                viewHolder.textValue = (TextView) convertView.findViewById(R.id.text_value);
                convertView.setTag(viewHolder);
            }
            ViewHolder viewHolder = (ViewHolder)convertView.getTag();
            JsonElement jsonElement = list.get(position);
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> map = gson.fromJson(jsonElement.getAsJsonObject(),type);
            for (Map.Entry<String, Object> entry : map.entrySet()){
                Object value = entry.getValue();
                if (null == value || "null".equals(value)) {
                    continue;
                }
                viewHolder.textDate.setText(map.get("description").toString());
                viewHolder.textValue.setText(map.get("dataValue").toString());
            }

            return convertView;
        }

        public class ViewHolder {
            public TextView textDate;
            public TextView textValue;
        }
    }
}