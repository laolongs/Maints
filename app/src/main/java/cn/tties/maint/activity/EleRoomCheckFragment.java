package cn.tties.maint.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.bean.CommonListViewBean;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.holder.DistributionHolder;
import cn.tties.maint.holder.InfoHolder;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.CreateEleRoomParams;
import cn.tties.maint.httpclient.params.DeleteEleRoomParams;
import cn.tties.maint.httpclient.params.SaveCompanyEquipmentByRoomIdParams;
import cn.tties.maint.httpclient.params.SelectCompanyEquipmentByAccountParams;
import cn.tties.maint.httpclient.params.SelectEleRoomParams;
import cn.tties.maint.httpclient.params.SelectEquipmentByEleRoomParams;
import cn.tties.maint.httpclient.params.UpdateEleRoomParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.EleRoomResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.view.ConfirmDialog;
import cn.tties.maint.view.CreateEleRoomDialog;
import cn.tties.maint.view.UpdateEleRoomDialog;
import cn.tties.maint.widget.RecyclerManager;

/**
 * 电房管理.
 */
@ContentView(R.layout.fragment_ele_room_check)
public class EleRoomCheckFragment extends BaseEleAccountFragment {

    private static final String TAG = "EleRoomCheckFragment";

    @ViewInject(R.id.layout_info)
    private LinearLayout infoLayout;

    @ViewInject(R.id.layout_distribution)
    private LinearLayout distributionLayout;

    protected InfoHolder curHolder;

    protected DistributionHolder distributionHolder;

    public static EleRoomCheckFragment eleRoomCheckFragmentInstance;

    private UpdateEleRoomDialog updateEleRoomDialog;
    private CreateEleRoomDialog createEleRoomDialog;
    /**
     * 电房下设备数量
     */
    private int companyEquipmentCount = 0;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eleRoomCheckFragmentInstance = this;
        initView();
        //初始化按钮
        initButton();
        hideLayout(-1);
    }
    private void initView() {
        List<CommonListViewBean> list = new ArrayList<>();
        CommonListViewBean bean = new CommonListViewBean(0, "创建电房");
        list.add(bean);
        lv2Adapter2.setmDataList(list);
        lv2Adapter2.notifyDataSetChanged();
        curHolder = new InfoHolder(infoLayout);
        distributionHolder = new DistributionHolder(distributionLayout);
    }
    /**
     * 初始化分配设备功能.
     */


    /**
     * 按钮事件
     */
    private void initButton() {
        curHolder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                String companyEquipmentId = distributionHolder.leftAdapter.getItemIds();
                SaveCompanyEquipmentByRoomIdParams scebrip = new SaveCompanyEquipmentByRoomIdParams();
                scebrip.setCompanyEquipmentId(companyEquipmentId);
                scebrip.setRoomId(lv2Adapter1.getChecked().getItemId());
                HttpClientSend.getInstance().send(scebrip, new BaseAlertCallback("电房设备配置成功","电房设备配失败") {
                    @Override
                    public void onSuccessed(String result) {
                        // 更新巡视中的电房
                        PatrolFragment.patrolFragmentInstance.getPatrolTypeList();
                        getEleAccountComEqu();
                        getEleRoomComEqu();
                    }
                });
            }
        });
    }

    @Override
    protected void setLv2ListViewMenu() {
        RecyclerManager.initConfig(lv2ListView, getActivity());
        lv2ListView.setSwipeMenuItemClickListener(this.mMenuItemClickListener);
    }

    @Override
    public void changeEleAccountNextStep() {
        hideLayout(1);
        // 根据当前用电户号更新电房列表
        lv2Adapter1.clearCheck();
        this.getEleRoomList();
    }

    protected void lv2UpListViewClick(CommonListViewInterface bean) {
        EleRoomResult eleRoomResult = (EleRoomResult) bean;
        // 查询并显示分配设备
        curHolder.nameText.setText(eleRoomResult.getRoomName());
        distributionHolder.textRight.setText("未分配");
        distributionHolder.textLeft.setText(eleRoomResult.getRoomName());
        this.getEleAccountComEqu();
        this.getEleRoomComEqu();
    }

    protected void lv2DownListViewClick(CommonListViewInterface bean) {
//        createEleRoomDialog = new CreateEleRoomDialog(EleRoomCheckFragment.this, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createEleRoomDialog.validator.validate();
//            }
//        });
//        createEleRoomDialog.show();
    }

    private void intoUpdateEleRoom(EleRoomResult eleRoomResult) {
//        updateEleRoomDialog = new UpdateEleRoomDialog(EleRoomCheckFragment.this, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateEleRoomDialog.validator.validate();
//            }
//        }, eleRoomResult);
//        updateEleRoomDialog.show();
    }

    /**
     * 获取企业电房列表.
     */
    private void getEleRoomList() {
        SelectEleRoomParams params = new SelectEleRoomParams();
        params.setAccountId(curEleId);
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), "获取电房失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<EleRoomResult> eleRoomResultList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<EleRoomResult>>() {
                }.getType());
                lv2Adapter1.setmDataList(eleRoomResultList);
                lv2Adapter1.notifyDataSetChanged();

                // 默认点击第一行
                if (null != eleRoomResultList && eleRoomResultList.size() > 0) {
                    setLv2Click(0);
                }
            }
        });
    }

    /**
     * 查询电房下企业设备
     *
     * @author lizhen
     */
    public void getEleRoomComEqu() {
        SelectEquipmentByEleRoomParams selectEquipmentByEleRoomParams = new SelectEquipmentByEleRoomParams();
        selectEquipmentByEleRoomParams.setRoomId(lv2Adapter1.getChecked().getItemId());
        HttpClientSend.getInstance().send(selectEquipmentByEleRoomParams, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), "获取企业设备失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CompanyEquipmentResult> companyEquipmentResults = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<CompanyEquipmentResult>>() {
                        }.getType());
                companyEquipmentCount=companyEquipmentResults.size();
                distributionHolder.leftAdapter.setmDataList(companyEquipmentResults);
                distributionHolder.leftAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 查询该用电户号下的未在此电房下的设备
     *
     * @author lizhen
     */
    public void getEleAccountComEqu() {
        if (null == lv2Adapter1.getChecked()) {
            return;
        }
        infoLayout.setVisibility(View.VISIBLE);
        SelectCompanyEquipmentByAccountParams selectCompanyEquipmentByAccountParams = new SelectCompanyEquipmentByAccountParams();
        selectCompanyEquipmentByAccountParams.setEleAccountId(curEleId);
        HttpClientSend.getInstance().send(selectCompanyEquipmentByAccountParams, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(getActivity(), "获取企业设备失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CompanyEquipmentResult> companyEquipmentResults = JsonUtils.deserialize(ret.getResult(),
                        new TypeToken<List<CompanyEquipmentResult>>() {
                        }.getType());
                distributionHolder.rightAdapter.setmDataList(companyEquipmentResults);
                distributionHolder.rightAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 创建电房.
     */
    public void createEleRoom() {
        CreateEleRoomParams createEleRoomParams = new CreateEleRoomParams();
        createEleRoomParams.setEleAccountId(curEleId);
        createEleRoomParams.setHigh(createEleRoomDialog.highRoom.isChecked());
        createEleRoomParams.setLow(createEleRoomDialog.lowRoom.isChecked());
        createEleRoomParams.setRoomName(createEleRoomDialog.eleRoomName.getText().toString().trim());
        createEleRoomParams.setTransformer(createEleRoomDialog.transformer.isChecked());
        createEleRoomParams.setFlag(0);
        HttpClientSend.getInstance().send(createEleRoomParams, new BaseAlertCallback("创建电房成功","创建电房失败") {
            @Override
            public void onSuccessed(String result) {
                try {
                    getEleRoomList();
                    createEleRoomDialog.dismiss();
                    // 更新巡视中的电房
                    PatrolFragment.patrolFragmentInstance.getPatrolTypeList();
                    // 更新检修中的电房
                    OverhaulFragment.overhaulFragmentInstance.getCompanyEquipmentList();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }

    /**
     * 修改电房.
     */
    public void updateEleRoom(final EleRoomResult eleRoomResult) {
        UpdateEleRoomParams updateEleRoomParams = new UpdateEleRoomParams();
        updateEleRoomParams.setEleAccountId(curEleId);
        updateEleRoomParams.setRoomId(eleRoomResult.getRoomId());
        updateEleRoomParams.setHigh(updateEleRoomDialog.highRoom.isChecked());
        updateEleRoomParams.setLow(updateEleRoomDialog.lowRoom.isChecked());
        updateEleRoomParams.setRoomName(updateEleRoomDialog.eleRoomName.getText().toString().trim());
        updateEleRoomParams.setTransformer(updateEleRoomDialog.transformer.isChecked());
        updateEleRoomParams.setFlag(0);
        HttpClientSend.getInstance().send(updateEleRoomParams, new BaseAlertCallback("更新电房成功","更新电房失败") {
            @Override
            public void onSuccessed(String result) {
                try {
                    getEleRoomList();
                    EleRoomResult eleRoomResult2 = (EleRoomResult) lv2Adapter1.getChecked();
                    if (eleRoomResult.getRoomId() == eleRoomResult2.getRoomId()){
                        curHolder.nameText.setText(updateEleRoomDialog.eleRoomName.getText().toString().trim());
                        distributionHolder.textLeft.setText(updateEleRoomDialog.eleRoomName.getText().toString().trim());
                    }
                    PatrolFragment.patrolFragmentInstance.getPatrolTypeList();
                    updateEleRoomDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }


    private void deleteEleRoom(final EleRoomResult eleRoomResult) {
        if (companyEquipmentCount!=0){
            Toast.makeText(getActivity(), "请先移除该电房下设备！", Toast.LENGTH_SHORT).show();
            return;
        }
        ConfirmDialog dialog = new ConfirmDialog(EleRoomCheckFragment.this.getActivity());
        dialog.loadDialog("是否要删除此电房", null, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DeleteEleRoomParams DeleteEleRoomParams = new DeleteEleRoomParams();
                DeleteEleRoomParams.setRoomId(eleRoomResult.getRoomId());
                HttpClientSend.getInstance().send(DeleteEleRoomParams, new BaseAlertCallback("删除电房成功","删除电房失败") {
                    @Override
                    public void onSuccessed(String result) {
                        try {
                            getEleRoomList();
                            // 更新巡视中的电房
                            PatrolFragment.patrolFragmentInstance.getPatrolTypeList();
                            // 更新检修中的电房
                            OverhaulFragment.overhaulFragmentInstance.getCompanyEquipmentList();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                        }
                    }
                });
            }
        });
    }

    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。

            final EleRoomResult eleRoomResult = (EleRoomResult) lv2Adapter1.getItem(adapterPosition);
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {       // 修改电房
                intoUpdateEleRoom(eleRoomResult);
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) { // 删除电房
                deleteEleRoom(eleRoomResult);
            }
        }
    };
}
