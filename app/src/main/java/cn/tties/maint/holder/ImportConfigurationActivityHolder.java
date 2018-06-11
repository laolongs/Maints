package cn.tties.maint.holder;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import cn.tties.maint.R;

public class ImportConfigurationActivityHolder extends BaseLayoutHolder {

    public  TextView import_con_tv;
    public Spinner import_con_company;
    public Spinner import_con_ele;
    public Spinner import_con_equipment;
    public LinearLayout import_con_cancel;
    public TextView eq_de_name;
    public TextView import_con_name;
    public Button import_con_btn_import;
    public TextView import_con_iscabinet;
    public TextView import_con_type;
    public TextView import_con_num;
    public LinearLayout import_con_hite;
    public LinearLayout import_con_all;
    public ListView import_con_leaf_list;
    public SwipeMenuRecyclerView import_con_details;

    public ImportConfigurationActivityHolder(View view) {
        this.import_con_tv = (TextView) view.findViewById(R.id.import_con_tv);
        this.import_con_company = (Spinner) view.findViewById(R.id.import_con_company);
        this.import_con_ele = (Spinner) view.findViewById(R.id.import_con_ele);
        this.import_con_equipment = (Spinner) view.findViewById(R.id.import_con_equipment);
        this.import_con_cancel = (LinearLayout) view.findViewById(R.id.import_con_cancel);
        this.eq_de_name = (TextView) view.findViewById(R.id.eq_de_name);
        this.import_con_name = (TextView) view.findViewById(R.id.import_con_name);
        this.import_con_btn_import = (Button) view.findViewById(R.id.import_con_btn_import);
        this.import_con_iscabinet = (TextView) view.findViewById(R.id.import_con_iscabinet);
        this.import_con_type = (TextView) view.findViewById(R.id.import_con_type);
        this.import_con_num = (TextView) view.findViewById(R.id.import_con_num);
        this.import_con_hite = (LinearLayout) view.findViewById(R.id.import_con_hite);
        this.import_con_all = (LinearLayout) view.findViewById(R.id.import_con_all);
        this.import_con_leaf_list = (ListView) view.findViewById(R.id.import_con_leaf_list);
        this.import_con_details = (SwipeMenuRecyclerView) view.findViewById(R.id.import_con_details);


    }
}