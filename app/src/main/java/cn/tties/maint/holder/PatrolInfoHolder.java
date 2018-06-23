package cn.tties.maint.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import cn.tties.maint.R;

public class PatrolInfoHolder extends BaseLayoutHolder {

    public SwipeMenuRecyclerView listview_root;
    public SwipeMenuRecyclerView listview_lv2;
    public LinearLayout layout_lv1_all;
    public LinearLayout layout_lv1_hite;
    public LinearLayout layout_lv2_all;
    public LinearLayout layout_lv2_hite;
    public LinearLayout layout_lv3_all;
    public TextView text_title;
    public ListView listview_lv3;
    public LinearLayout patrol_addquestion;
    public LinearLayout layout_lv3_hite;


    public PatrolInfoHolder(View view) {
        this.listview_root = (SwipeMenuRecyclerView) view.findViewById(R.id.listview_root);
        this.listview_lv2 = (SwipeMenuRecyclerView) view.findViewById(R.id.listview_lv2);
        this.layout_lv3_all = (LinearLayout) view.findViewById(R.id.layout_lv3_all);
        this.text_title = (TextView) view.findViewById(R.id.text_title);
        this.listview_lv3 = (ListView) view.findViewById(R.id.list_patrol_item);
        this.patrol_addquestion = (LinearLayout) view.findViewById(R.id.patrol_addquestion);
        this.layout_lv3_hite = (LinearLayout) view.findViewById(R.id.layout_lv3_hite);
        this.layout_lv1_all = (LinearLayout) view.findViewById(R.id.layout_lv1_all);
        this.layout_lv1_hite = (LinearLayout) view.findViewById(R.id.layout_lv1_hite);
        this.layout_lv2_all = (LinearLayout) view.findViewById(R.id.layout_lv2_all);
        this.layout_lv2_hite = (LinearLayout) view.findViewById(R.id.layout_lv2_hite);

    }
}