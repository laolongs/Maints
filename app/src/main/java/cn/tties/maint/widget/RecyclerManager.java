package cn.tties.maint.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import cn.tties.maint.R;

/**
 * Created by guider on 2018/1/6.
 */

public class RecyclerManager {

    public static void initConfig(SwipeMenuRecyclerView mRecyclerView, Context context) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(context, R.color.dialog_title), 1, 1, -1));
        mRecyclerView.setSwipeMenuCreator(createSwipeMenuCreator(context));
    }

    public static void initParrolConfig(SwipeMenuRecyclerView mRecyclerView, Context context) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(context, R.color.dialog_title), 1, 1, -1));
        mRecyclerView.setSwipeMenuCreator(createPatrolSwipeMenuCreator(context));
    }



    private static SwipeMenuCreator createSwipeMenuCreator(final Context context) {
        return new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                int width = context.getResources().getDimensionPixelSize(R.dimen.dp_40);

                int height = ViewGroup.LayoutParams.MATCH_PARENT;

                {

                    SwipeMenuItem closeItem = new SwipeMenuItem(context)
                            .setBackground(R.drawable.selector_red)
                            .setImage(R.mipmap.ic_action_close)
                            .setWidth(width)
                            .setHeight(height);
                    swipeLeftMenu.addMenuItem(closeItem); // 添加菜单到左侧。
                }

                {


                    SwipeMenuItem addItem = new SwipeMenuItem(context)
                            .setBackground(R.drawable.selector_green)
                            .setText("修改")
                            .setTextColor(Color.WHITE)
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
                }
            }
        };

    }

    private static SwipeMenuCreator createPatrolSwipeMenuCreator(final Context context) {
        return new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                int width = context.getResources().getDimensionPixelSize(R.dimen.dp_40);

                int height = ViewGroup.LayoutParams.MATCH_PARENT;

                {


                    SwipeMenuItem addItem = new SwipeMenuItem(context)
                            .setBackground(R.drawable.selector_green)
                            .setText("巡视")
                            .setTextColor(Color.WHITE)
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
                }
            }
        };

    }
}
