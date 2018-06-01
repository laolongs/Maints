package cn.tties.maint.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import cn.tties.maint.R;

public class EquipmentDetailHolder extends BaseLayoutHolder {
    //管理档案
    public LinearLayout layout_equipment_info;

    public SwipeMenuRecyclerView listview_lv1_2;

    public LinearLayout equipment_companyMessage;

    public LinearLayout equipment_eleConfiguration;

    public ImageView lv2_img;

    public TextView lv2_tv;

    public LinearLayout layout_lv2_all;

    public SwipeMenuRecyclerView listview_lv2;

    public SwipeMenuRecyclerView listview_lv2_2;

    public LinearLayout layout_lv2_hite;

    public LinearLayout layout_lv3_hite;

    public LinearLayout layout_lv3_all;

    public  TextView text_name;

    public Button btn_editor;

    public Button btn_del;

    public Button btn_new;

    public TextView lv2_details_iscabinet;

    public TextView lv2_details_name;

    public TextView lv2_details_num;

    public ListView lv3_leaf_list;
    public RecyclerView recy_detail;
    //电房-----------------------------------------------------------
    public LinearLayout layout_ele_info;

    public LinearLayout layout_ele_lv2;

    public ImageView lv2_ele_img;

    public TextView lv2_ele_tv;

    public LinearLayout layout_lv2_ele_all;

    public SwipeMenuRecyclerView listview_ele_lv2;

    public LinearLayout layout_lv2_ele_hite;

    public LinearLayout layout_lv3_ele_hite;

    public LinearLayout layout_lv3_ele_all;

    public  TextView text_ele_name;

    public Button btn_ele_del;

    public Button btn_ele_update;

    public Button btn_ele_save;

    public Button btn_ele_editor;

    public LinearLayout layout_lv3_ele_message;

    public TextView ele_message_name;

    public TextView ele_message_type;

    public RecyclerView lv3_ele_message_smrecy;

    public LinearLayout layout_lv3_ele_configuration;

    public EquipmentDetailHolder(View view) {
        this.layout_equipment_info = (LinearLayout) view.findViewById(R.id.layout_equipment_info);
        this.listview_lv1_2 = (SwipeMenuRecyclerView) view.findViewById(R.id.listview_lv1_2);
        this.equipment_companyMessage = (LinearLayout) view.findViewById(R.id.equipment_companyMessage);
        this.equipment_eleConfiguration = (LinearLayout) view.findViewById(R.id.equipment_eleConfiguration);
        this.lv2_img = (ImageView) view.findViewById(R.id.lv2_img);
        this.lv2_tv = (TextView) view.findViewById(R.id.lv2_tv);
        this.layout_lv2_all = (LinearLayout) view.findViewById(R.id.layout_lv2_all);
        this.listview_lv2 = (SwipeMenuRecyclerView) view.findViewById(R.id.listview_lv2);
        this.listview_lv2_2 = (SwipeMenuRecyclerView) view.findViewById(R.id.listview_lv2_2);
        this.layout_lv2_hite = (LinearLayout) view.findViewById(R.id.layout_lv2_hite);
        this.layout_lv3_hite = (LinearLayout) view.findViewById(R.id.layout_lv3_hite);
        this.layout_lv3_all = (LinearLayout) view.findViewById(R.id.layout_lv3_all);
        this.text_name = (TextView) view.findViewById(R.id.text_name);
        this.btn_editor = (Button) view.findViewById(R.id.btn_editor);
        this.btn_del = (Button) view.findViewById(R.id.btn_del);
        this.btn_new = (Button) view.findViewById(R.id.btn_new);
        this.lv2_details_iscabinet = (TextView) view.findViewById(R.id.lv2_details_iscabinet);
        this.lv2_details_name = (TextView) view.findViewById(R.id.lv2_details_name);
        this.lv2_details_num = (TextView) view.findViewById(R.id.lv2_details_num);
        this.lv3_leaf_list = (ListView) view.findViewById(R.id.lv3_leaf_list);
        this.recy_detail = (RecyclerView) view.findViewById(R.id.list_detail);
        //电房-----------------------------------------------------------------
        this.layout_ele_info = (LinearLayout) view.findViewById(R.id.layout_ele_info);
        this.layout_ele_lv2 = (LinearLayout) view.findViewById(R.id.layout_ele_lv2);
        this.lv2_ele_img = (ImageView) view.findViewById(R.id.lv2_ele_img);
        this.lv2_ele_tv = (TextView) view.findViewById(R.id.lv2_ele_tv);
        this.layout_lv2_ele_all = (LinearLayout) view.findViewById(R.id.layout_lv2_ele_all);
        this.listview_ele_lv2 = (SwipeMenuRecyclerView) view.findViewById(R.id.listview_ele_lv2);
        this.layout_lv2_ele_hite = (LinearLayout) view.findViewById(R.id.layout_lv2_ele_hite);
        this.layout_lv3_ele_hite = (LinearLayout) view.findViewById(R.id.layout_lv3_ele_hite);
        this.layout_lv3_ele_all = (LinearLayout) view.findViewById(R.id.layout_lv3_ele_all);
        this.text_ele_name = (TextView) view.findViewById(R.id.text_ele_name);
        this.btn_ele_del = (Button) view.findViewById(R.id.btn_ele_del);
        this.btn_ele_update = (Button) view.findViewById(R.id.btn_ele_update);
        this.btn_ele_save = (Button) view.findViewById(R.id.btn_ele_save);
        this.btn_ele_editor = (Button) view.findViewById(R.id.btn_ele_editor);
        this.layout_lv3_ele_message = (LinearLayout) view.findViewById(R.id.layout_lv3_ele_message);
        this.ele_message_name = (TextView) view.findViewById(R.id.ele_message_name);
        this.ele_message_type = (TextView) view.findViewById(R.id.ele_message_type);
        this.lv3_ele_message_smrecy = (RecyclerView) view.findViewById(R.id.lv3_ele_message_smrecy);
        this.layout_lv3_ele_configuration = (LinearLayout) view.findViewById(R.id.layout_lv3_ele_configuration);
    }
}