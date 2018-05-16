package cn.tties.maint.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.xutils.x;

import cn.tties.maint.R;

public class MSwipeMenuRecyclerView extends SwipeMenuRecyclerView {

    public MSwipeMenuRecyclerView(Context context) {
        super(context);
        this.setLayoutManager(new LinearLayoutManager(x.app()));
        this.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
    }

    public MSwipeMenuRecyclerView(Context context, boolean showDevice) {
        super(context);
        this.setLayoutManager(new LinearLayoutManager(x.app()));
        if (showDevice) {
            this.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(x.app(), R.color.dialog_title), 1, 1, -1));
        }
    }
}