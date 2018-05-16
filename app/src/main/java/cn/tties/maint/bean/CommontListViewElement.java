package cn.tties.maint.bean;

import android.widget.LinearLayout;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import cn.tties.maint.adapter.CommonSwipListViewAdapter;

/**
 * Created by Justin on 2018/1/11.
 */

public class CommontListViewElement {

    private SwipeItemClickListener mClickListener;

    private CommonSwipListViewAdapter adapter1;

    private SwipeMenuRecyclerView listView1;

    private CommonSwipListViewAdapter adapter2;

    private int equipPid;

    private int cequipPid;

    private LinearLayout layout;

    private int level;

    public CommontListViewElement(CommonSwipListViewAdapter adapter1, int level) {
        this.adapter1 = adapter1;
        this.level = level;
    }

    public CommontListViewElement(LinearLayout layout, CommonSwipListViewAdapter adapter1, SwipeMenuRecyclerView listView1, CommonSwipListViewAdapter adapter2, int level) {
        this.adapter1 = adapter1;
        this.adapter2 = adapter2;
        this.layout = layout;
        this.listView1 = listView1;
        this.level = level;
    }

    public void setPid(int equipPid, int cequipPid) {
        this.equipPid = equipPid;
        this.cequipPid = cequipPid;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public int getEquipPid() {
        return equipPid;
    }

    public int getCequipPid() {
        return cequipPid;
    }

    public CommonSwipListViewAdapter getAdapter1() {
        return adapter1;
    }

    public CommonSwipListViewAdapter getAdapter2() {
        return adapter2;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public SwipeItemClickListener getmClickListener() {
        return mClickListener;
    }

    public void setmClickListener(SwipeItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public void setClick(int pos) {
        mClickListener.onItemClick(listView1, pos);
    }

}
