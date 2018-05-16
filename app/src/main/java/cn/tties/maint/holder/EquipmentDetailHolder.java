package cn.tties.maint.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import cn.tties.maint.R;

public class EquipmentDetailHolder extends BaseLayoutHolder {

    public SwipeMenuRecyclerView listview_lv1_2;

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

    public RecyclerView recy_detail;

    public EquipmentDetailHolder(View view) {
        this.listview_lv1_2 = (SwipeMenuRecyclerView) view.findViewById(R.id.listview_lv1_2);
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
        this.recy_detail = (RecyclerView) view.findViewById(R.id.list_detail);
    }
}