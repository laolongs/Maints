package cn.tties.maint.secondLv;

import android.content.Context;
import android.graphics.Paint;
import android.print.PrintJob;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.EquipmentLayoutAdapter;
import cn.tties.maint.adapter.MyEquipmentLayout_RecyAdapter;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.view.EquipmentDetailsDialog;


/**
 * Created by li on 2018/5/11
 * description：
 * author：guojlli
 */

public class ChildViewHolder extends BaseViewHolder implements View.OnClickListener {
    private static final String TAG = "ChildViewHolder";
    private Context mContext;
    private View view;
    private RecyclerView recy;
    public LinearLayout child_compile;
    public LinearLayout child_delete;
    public LinearLayout child_new;
    int position=0;
    RecyclerAdapter recyclerAdapter;
    boolean falg;
    DataBean dataBean;
    public ChildViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final DataBean dataBean, final int pos,RecyclerAdapter recyclerAdapter){
        this.position=pos;
        this.recyclerAdapter=recyclerAdapter;
        this.dataBean=dataBean;
        recy = (RecyclerView) view.findViewById(R.id.child_recy);
        //编辑
        child_compile = (LinearLayout) view.findViewById(R.id.re_item_child_compile);
        //删除
        child_delete = (LinearLayout) view.findViewById(R.id.re_item_child_delete);
        //新建
        child_new = (LinearLayout) view.findViewById(R.id.re_item_child_new);
        recy.setLayoutManager(new GridLayoutManager(mContext,2));
        MyEquipmentLayout_RecyAdapter adapter=new MyEquipmentLayout_RecyAdapter(mContext,dataBean.getBean());
        recy.setAdapter(adapter);
        child_compile.setOnClickListener(this);
        child_delete.setOnClickListener(this);
        child_new.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //编辑
            case R.id.re_item_child_compile:
                EquipmentDetailsDialog dialog = new EquipmentDetailsDialog(true,mContext, dataBean.getBean().get(0).getTitleName(), dataBean.getBean(),new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                dialog.show();
                break;
            //删除
            case R.id.re_item_child_delete:
                ToastUtil.showShort(mContext,""+position);
                recyclerAdapter.removeparent(position);
                recyclerAdapter.remove(position-1);
                recyclerAdapter.notifyDataSetChanged();
                falg=true;
                EventBusBean busBean = new EventBusBean();
                busBean.setKind(EventKind.EVENT_COMPANY_DATAILS);
                busBean.setSuccess(falg);
                EventBus.getDefault().post(busBean);
                break;
            //新建 就是复制当前条目
            case R.id.re_item_child_new:

                break;
        }
    }
}
