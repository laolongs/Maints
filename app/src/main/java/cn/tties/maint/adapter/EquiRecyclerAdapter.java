package cn.tties.maint.adapter;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.secondLv.BaseViewHolder;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.view.EquipmentDetailsDialog;
import cn.tties.maint.view.MyEquipmentDetailsDialog;

/**
 * Created by li on 2018/5/11
 * description：
 * author：guojlli
 */

public class EquiRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "EquiRecyclerAdapter";
    private MyEquipmentDetailsDialog dialog;
    private Context context;
    private List<DataBean> dataBeanList;
    private LayoutInflater mInflater;
    DataBean data;
    //用于编辑
    boolean flag;
    //用于编辑
    DataBean databean;
    //用于记录id位置便于回传。
    int position;
    private ParentViewHolder parentViewHolder;
    /**
     * 标记展开的item
     */
    private int opened = -1;
    OnClickTextViewData listener;
    public EquiRecyclerAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }
    public void setDataBeanList(List<DataBean> dataBeanList){
        this.dataBeanList = dataBeanList;
    }
    public void setOnClickTextViewData(OnClickTextViewData listener){
        this.listener=listener;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view = mInflater.inflate(R.layout.recycleview_item_parent, parent, false);
        return new ParentViewHolder(view);

    }

    /**
     * 根据不同的类型绑定View
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        parentViewHolder = (ParentViewHolder) holder;
        parentViewHolder.bindView(position,dataBeanList.get(position));

    }
    @Override
    public int getItemCount() {
        return dataBeanList==null?0:dataBeanList.size();
    }

    public class ParentViewHolder extends BaseViewHolder implements View.OnClickListener {
        private View view;
        private RelativeLayout containerLayout;
        private TextView parentLeftView;
        private TextView parentRightView;
        public ImageView expand;
        public LinearLayout parentDashedView;
        private RecyclerView recy;
        public LinearLayout child_compile;
        public LinearLayout child_delete;
        public LinearLayout child_new;


        public ParentViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
        public void bindView(int pos,DataBean dataBean){
            position=pos;
            data=dataBean;
            containerLayout = (RelativeLayout) view.findViewById(R.id.container);
            parentLeftView = (TextView) view.findViewById(R.id.parent_left_text);
            parentRightView = (TextView) view.findViewById(R.id.parent_right_text);
            expand = (ImageView) view.findViewById(R.id.expend);
            parentDashedView = view.findViewById(R.id.parent_dashed_view);
            parentDashedView = (LinearLayout)view.findViewById(R.id.parent_dashed_view);
            recy = (RecyclerView) view.findViewById(R.id.child_recy);
            //编辑
            child_compile = (LinearLayout) view.findViewById(R.id.re_item_child_compile);
            //删除
            child_delete = (LinearLayout) view.findViewById(R.id.re_item_child_delete);
            //新建
            child_new = (LinearLayout) view.findViewById(R.id.re_item_child_new);
            recy.setLayoutManager(new GridLayoutManager(context,2));
            MyEquipmentLayout_EquiRecyAdapter adapter=new MyEquipmentLayout_EquiRecyAdapter(context,dataBean.getBean());
            recy.setAdapter(adapter);
            containerLayout.setOnClickListener(this);
            child_compile.setOnClickListener(this);
            child_delete.setOnClickListener(this);
            child_new.setOnClickListener(this);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) expand
                    .getLayoutParams();
            expand.setLayoutParams(params);
            parentLeftView.setText(dataBean.getParentLeftTxt());
            parentRightView.setText("详情");
            if (pos == opened){
                parentDashedView.setVisibility(View.VISIBLE);
                parentRightView.setText("收起");
                rotationExpandIcon(0, 90);
            } else{
                parentDashedView.setVisibility(View.GONE);
                parentRightView.setText("详情");
                rotationExpandIcon(90, 0);
            }

        }
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.container:
                    if (opened == getAdapterPosition()) {
                        //当点击的item已经被展开了, 就关闭.
                        opened = -1;
                        notifyItemChanged(getAdapterPosition());
                    } else {
                        int oldOpened = opened;
                        opened = getAdapterPosition();
                        notifyItemChanged(oldOpened);
                        notifyItemChanged(opened);
                    }
                    break;
                    //编辑
                case R.id.re_item_child_compile:
                    dialog = new MyEquipmentDetailsDialog(data.getItemid(),position,data,true,context, data.getParentLeftTxt(), data.getBean(),new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    dialog.show();
                    //接口回调传值回来
                    dialog.setOnClick(new MyEquipmentDetailsDialog.OnClick() {
                        @Override
                        public void OnClickListener(int pos, DataBean dataBean) {
                            dataBeanList.set(pos,dataBean);
                            notifyDataSetChanged();
                        }
                    });
                    break;
                //删除
                case R.id.re_item_child_delete:
                    dataBeanList.remove(getAdapterPosition());
                    opened = -1;
                    notifyDataSetChanged();
                    break;
                //新建
                case R.id.re_item_child_new:
                    DataBean dataBean = dataBeanList.get(getAdapterPosition());
                    String parentLeftTxt = dataBean.getParentLeftTxt();
                    List<EquipmentLayoutBean> bean = dataBean.getBean();
                    int itemid = dataBean.getItemid();
                    DataBean dataBean1=new DataBean();
                    if(parentLeftTxt.contains("复制")){
                        dataBean1.setParentLeftTxt(parentLeftTxt);
                    }else{
                        dataBean1.setParentLeftTxt(parentLeftTxt+"复制");
                    }
                    dataBean1.setBean(bean);
                    dataBean1.setItemid(itemid);
                    dataBeanList.add(dataBean1);
                    Collections.sort(dataBeanList, new Comparator<DataBean>() {
                        @Override
                        public int compare(DataBean dataBean, DataBean t1) {
                            return  Integer.valueOf(dataBean.getItemid()).compareTo(Integer.valueOf(t1.getItemid()));
                        }
                    });
                    listener.OnClickTextViewDataListener(dataBeanList.size());
                    notifyDataSetChanged();
                    break;
            }
        }
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void rotationExpandIcon(float from, float to) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);//属性动画
                valueAnimator.setDuration(1000);
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        expand.setRotation((Float) valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        }
    }
    public interface  OnClickTextViewData{
        public void OnClickTextViewDataListener(int text);
    }
}