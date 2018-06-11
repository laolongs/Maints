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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.MyEquipmentLayout_RecyAdapter;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.secondLv.BaseViewHolder;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.view.AllCancelDialog;
import cn.tties.maint.view.EquipmentDetailsDialog;
import cn.tties.maint.view.MyEquipmentDetailsDialog;

/**
 * Created by li on 2018/5/11
 * description：
 * author：guojlli
 */

public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "RecyclerAdapter";
    private Context context;
    private List<DataBean> dataBeanList;
    private List<DataBean> recorddataBeanList;
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
    public RecyclerAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }
    public void setOnClickTextViewData(OnClickTextViewData listener){
        this.listener=listener;
    }
    public void setDataBeanList(List<DataBean> dataBeanList){
        this.dataBeanList = dataBeanList;
    }
    public void setRecordDataBeanList(List<DataBean> recorddataBeanList){
        this.recorddataBeanList = recorddataBeanList;
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
                expand.setImageResource(R.mipmap.details_down);
            } else{
                parentDashedView.setVisibility(View.GONE);
                parentRightView.setText("详情");
                expand.setImageResource(R.mipmap.details_up);
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
                    final EquipmentDetailsDialog dialog = new EquipmentDetailsDialog(data.getItemid(),position,data,true,context, data.getParentLeftTxt(), data.getBean(),new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    dialog.show();
                    //接口回调传值回来
                    dialog.setOnClick(new EquipmentDetailsDialog.OnClick() {
                        @Override
                        public void OnClickListener(int pos, DataBean dataBean) {
                            dataBeanList.set(pos,dataBean);
                            recorddataBeanList.set(pos,dataBean);
                            notifyDataSetChanged();
                        }
                    });
                    break;
                //删除
                case R.id.re_item_child_delete:
                    AllCancelDialog dialog1=new AllCancelDialog();
                    dialog1.loadDialog("您确定要删除该设备？", new AllCancelDialog.OnClickIsConfirm() {
                        @Override
                        public void OnClickIsConfirmListener() {
                            if(dataBeanList.get(getAdapterPosition()).getRecord()==1){//等于1得时候说明是来自企业得设备
                                recorddataBeanList.get(getAdapterPosition()).setFlag(true);
//                                data.setFlag(true);
                            }else{
                                recorddataBeanList.remove(getAdapterPosition());
                            }
                            dataBeanList.remove(getAdapterPosition());
                            opened = -1;
                            listener.OnClickTextViewDataListener(dataBeanList.size());
                            notifyDataSetChanged();
                        }
                    }, new AllCancelDialog.OnClickIsCancel() {
                        @Override
                        public void OnClickIsCancelListener() {

                        }
                    });
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
                    recorddataBeanList.add(dataBean1);
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
    }
    public interface  OnClickTextViewData{
        public void OnClickTextViewDataListener(int text);
    }
}