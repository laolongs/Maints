package cn.tties.maint.holder;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import cn.tties.maint.R;

public class EquipmentDetailsActivityHolder extends BaseLayoutHolder {


    public LinearLayout eq_de_start;
    public LinearLayout eq_de_old;
    public LinearLayout eq_de_cancel;
    public LinearLayout eq_de_confirm;

    public  TextView eq_de_name;
    public  TextView eq_de_CabinetTV;
    public RadioGroup eq_de_radiogroup;
    public RadioButton eq_de_yes;
    public RadioButton eq_de_no;

    public EditText edit_value;

    public TextView eq_de_num;

    public ListView eq_de_list_feal_list;
    public SwipeMenuRecyclerView eq_de_list_lv4;
    public SwipeMenuRecyclerView eq_de_list_lv4_details;
    public Boolean isCabinet=false;
    public EquipmentDetailsActivityHolder(View view) {
        this.eq_de_start = (LinearLayout) view.findViewById(R.id.eq_de_start);
        this.eq_de_old = (LinearLayout) view.findViewById(R.id.eq_de_old);
        this.eq_de_cancel = (LinearLayout) view.findViewById(R.id.eq_de_cancel);
        this.eq_de_confirm = (LinearLayout) view.findViewById(R.id.eq_de_confirm);
        this.eq_de_name = (TextView) view.findViewById(R.id.eq_de_name);
        this.eq_de_CabinetTV = (TextView) view.findViewById(R.id.eq_de_CabinetTV);
        this.eq_de_radiogroup = (RadioGroup) view.findViewById(R.id.eq_de_radiogroup);
        this.eq_de_yes = (RadioButton) view.findViewById(R.id.eq_de_yes);
        this.eq_de_no = (RadioButton) view.findViewById(R.id.eq_de_no);
        this.edit_value = (EditText) view.findViewById(R.id.edit_value);
        this.eq_de_num = (TextView) view.findViewById(R.id.eq_de_num);
        this.eq_de_list_feal_list = (ListView) view.findViewById(R.id.eq_de_list_feal_list);
        this.eq_de_list_lv4 = (SwipeMenuRecyclerView) view.findViewById(R.id.eq_de_list_lv4);
        this.eq_de_list_lv4_details = (SwipeMenuRecyclerView) view.findViewById(R.id.eq_de_list_lv4_details);
        isCabinet = false;
//        是否有柜体
        this.eq_de_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton button = radioGroup.findViewById(i);
                if (button.getText().equals("是")) {
                    isCabinet = true;
                } else {
                    isCabinet = false;
                }
            }
        });
    }
}