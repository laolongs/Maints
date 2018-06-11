package cn.tties.maint.holder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonListViewAdapter;
import cn.tties.maint.bean.CommonListViewInterface;

public class DistributionMessageHolder {

    public ListView ammeter_allele_list;

    public DistributionMessageHolder(View contentView) {
        this.ammeter_allele_list = (ListView) contentView.findViewById(R.id.ammeter_allele_list);

    }

}