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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.activity.EleRoomCheckFragment;
import cn.tties.maint.activity.EquipmentCheckDetailsActivity;
import cn.tties.maint.activity.EquipmentCheckFragment;
import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.bean.UpdateEquipParam;
import cn.tties.maint.bean.UpdateItemParam;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.CopyCompanyEquipmentParams;
import cn.tties.maint.httpclient.params.DeleteComEquParams;
import cn.tties.maint.httpclient.params.UpdateComEquParams;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.secondLv.BaseViewHolder;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.view.AllCancelDialog;
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
    OnClickDeleteData   deleteData;
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
    public void setOnClickDeleteData(OnClickDeleteData   deleteData){
        this.deleteData=deleteData;
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
            GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
            recy.setLayoutManager(gridLayoutManager);
//            recy.addItemDecoration(new GridSpacingItemDecoration(30));
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
//                rotationExpandIcon(0, 90);
            } else{
                parentDashedView.setVisibility(View.GONE);
                parentRightView.setText("详情");
                expand.setImageResource(R.mipmap.details_up);
//                rotationExpandIcon(90, 0);
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
                            updateCompanyEquip(dataBean);
//                            notifyDataSetChanged();

//                            dataBeanList.set(pos,dataBean);
//                            notifyDataSetChanged();

                        }
                    });
                    break;
                //删除
                case R.id.re_item_child_delete:
                    final AllCancelDialog dialog1=new AllCancelDialog();
                    dialog1.loadDialog("您确定要删除该设备？", new AllCancelDialog.OnClickIsConfirm() {
                        @Override
                        public void OnClickIsConfirmListener() {
                            //发起请求
                            sendDelInfo(dataBeanList.get(getAdapterPosition()).getCompanyEquipmentId()+"");
                        }
                    }, new AllCancelDialog.OnClickIsCancel() {
                        @Override
                        public void OnClickIsCancelListener() {

                        }
                    });
                    break;
                //新建   接口未调
                case R.id.re_item_child_new:
                    String name=null;
                    DataBean dataBean = dataBeanList.get(getAdapterPosition());
                    String parentLeftTxt = dataBean.getParentLeftTxt();
//                    DataBean dataBean1=new DataBean();
                    if(parentLeftTxt.contains("复制")){
                        name=parentLeftTxt;
                    }else{
                        name=parentLeftTxt+"复制";
                    }
                    sendCopyAddInfo(name,dataBean.getCompanyEquipmentId());
                    break;
            }
        }
    }
    public interface  OnClickTextViewData{
        public void OnClickTextViewDataListener(int text);
    }
    public interface  OnClickDeleteData{
        public void OnClickDeleteDataListener();
    }
    //删除企业设备
    private void sendDelInfo(final String cEqupIds) {
        DeleteComEquParams params = new DeleteComEquParams();
        params.setComEquIds(cEqupIds);
        HttpClientSend.getInstance().send(params, new BaseAlertCallback("删除设备成功","删除设备失败") {
            @Override
            public void onSuccessed(String result) {
                try {
                    EquipmentCheckFragment.equipmentCheckFragmentInstance.showlv3refresh();
//                    deleteData.OnClickDeleteDataListener();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }
    //编辑企业设备下得三四级
    private void updateCompanyEquip(DataBean dataBean) {
        List<EquipmentLayoutBean> bean = dataBean.getBean();
        List<UpdateEquipParam> updateList = new ArrayList<>();
        UpdateEquipParam updateEquipParam = new UpdateEquipParam();
        updateEquipParam.setComEquId(dataBean.getCompanyEquipmentId());
        updateEquipParam.setEquipId(dataBean.getEquipmentId());
        updateEquipParam.setNewComEquName(dataBean.getParentLeftTxt());
        updateEquipParam.setPid(dataBean.getPid());
        updateList.add(updateEquipParam);
        EquipmentEntity equipmentEntity = EquipmentDao.getInstance().queryById(dataBean.getEquipmentId());
        //四级
        if(!equipmentEntity.getIsLeafNode()){
            //企业设备下级设备
            for (int i = 0; i < bean.size(); i++) {
                EquipmentLayoutBean leafBean = bean.get(i);
                UpdateEquipParam param = new UpdateEquipParam();
                param.setComEquId(leafBean.getCompanyEquipmentId());
                param.setNewComEquName(leafBean.getTextName());
                param.setEquipId(dataBean.getEquipmentId());
                //? 为什么是三级得设备iD  因为三级得企业设备id就是四级得pid
                param.setPid(dataBean.getCompanyEquipmentId());
                List<UpdateItemParam> itemList = new ArrayList<>();
                for (EquipmentLayoutBean itemBean : leafBean.getChildrenList()) {
                    UpdateItemParam itemParam = new UpdateItemParam();
                    itemParam.setEquipmentItemId(itemBean.getItemId());
                    itemParam.setEquipmentInfoId(itemBean.getEquipmentInfoId());
                    itemParam.setNewEquipmentInfo(itemBean.getValue());
                    itemList.add(itemParam);
                }
                param.setUpdateEquItemInfo(itemList);
                updateList.add(param);
            }

        }else{//三级
            //企业设备下级设备
            List<UpdateItemParam> itemList = new ArrayList<>();
            for (int i = 0; i < bean.size(); i++) {
                EquipmentLayoutBean bean1 = bean.get(i);
                for (EquipmentLayoutBean itemBean : bean1.getChildrenList()) {
                    UpdateItemParam itemParam = new UpdateItemParam();
                    itemParam.setEquipmentItemId(itemBean.getItemId());
                    itemParam.setEquipmentInfoId(itemBean.getEquipmentInfoId());
                    itemParam.setNewEquipmentInfo(itemBean.getValue());
                    itemList.add(itemParam);
                }
            }
            updateEquipParam.setUpdateEquItemInfo(itemList);
        }

        sendUpdateInfo(updateList);
    }
    private void sendUpdateInfo(List<UpdateEquipParam> updateList) {
        UpdateComEquParams params = new UpdateComEquParams();
        params.setParamJson(JsonUtils.getJsonArrayStr(updateList));
        Log.i(TAG, "sendUpdateInfo: "+params.getParamJson());
        HttpClientSend.getInstance().send(params, new BaseAlertCallback("更新设备成功","更新设备失败") {
            @Override
            public void onSuccessed(String result) {
                try {
//                    deleteData.OnClickDeleteDataListener();
//                    showMuiltListView(curPidResult);
//                    EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleAccountComEqu();
//                    EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleRoomComEqu();
                    EquipmentCheckFragment.equipmentCheckFragmentInstance.showlv3refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }
    //新建相同设备
    private void sendCopyAddInfo(String name, Integer cEquipId) {
        CopyCompanyEquipmentParams params = new CopyCompanyEquipmentParams();
        params.setCompanyEquipmentName(name);
        params.setCompanyEquipmentId(cEquipId);
        HttpClientSend.getInstance().send(params, new BaseAlertCallback("新增设备成功","新增设备失败") {
            @Override
            public void onSuccessed(String result) {
//                deleteData.OnClickDeleteDataListener();
//                showMuiltListView(curPidResult, adapter, adapter2, false);
//                // 更新电房管理中的未分配设备
//                EleRoomCheckFragment.eleRoomCheckFragmentInstance.getEleAccountComEqu();
//                //重置新增界面
//                showAddCEquipLayout(EquipmentDao.getInstance().queryById(curHolder.id), adapter, adapter2);
                EquipmentCheckFragment.equipmentCheckFragmentInstance.showlv3refresh();
            }
        });
    }

}