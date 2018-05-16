package cn.tties.maint.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.OverhaulListViewAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.OverhaulItemTypeEntity;
import cn.tties.maint.enums.OverhaulItemType;
import cn.tties.maint.holder.InfoHolder;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.QueryOverhaulItemParams;
import cn.tties.maint.httpclient.params.SelectCompanyEquipmentByEquipmentTypeParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.OverhaulResult;
import cn.tties.maint.util.JsonUtils;

/**
 * 检修管理.
 * Created by Justin on 2018/1/9.
 */

@ContentView(R.layout.fragment_overhaul)
public class OverhaulFragment extends BaseEleAccountFragment {

    // 检修项list
    @ViewInject(R.id.layout_info)
    private LinearLayout infoLayout;

    // 检修项list
    @ViewInject(R.id.list_overhaul_item)
    private ListView overhaulItemListView;

    public static OverhaulFragment overhaulFragmentInstance;

    private List<OverhaulItemTypeEntity> overhaulItemTypeList;

    private OverhaulListViewAdapter overhaulListAdapter;

    private InfoHolder infoHolder;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        overhaulFragmentInstance = this;
        initOverhaulItemListView();
        infoHolder = new InfoHolder(infoLayout);
        hideLayout(-1);
    }

    /**
     * 初始化检修项list.
     */
    private void initOverhaulItemListView() {
        overhaulListAdapter = new OverhaulListViewAdapter(this);
        overhaulItemListView.setAdapter(overhaulListAdapter);
        overhaulItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                OverhaulResult overhaulResult = overhaulListAdapter.getItem(position);
            }
        });
        overhaulListAdapter.notifyDataSetChanged();
    }

    @Override
    public void changeEleAccountNextStep() {
        hideLayout(0);
        // 根据当前用电户号更新电房列表
        overhaulItemTypeList = OverhaulItemTypeEntity.getOverhaulItemTypeList();
        rootAdapter.setmDataList(overhaulItemTypeList);
        rootAdapter.notifyDataSetChanged();
    }

    protected void rootListViewClick(CommonListViewInterface bean) {
        hideLayout(1);
        lv2Adapter1.clearCheck();
        getCompanyEquipmentList();
    }

    protected void lv2UpListViewClick(CommonListViewInterface bean) {
        getOverhaulItemList();
    }

    /**
     * 查询企业设备列表.
     */
    public void getCompanyEquipmentList() {
        CommonListViewInterface overhaultItemType = rootAdapter.getChecked();
        if (null == overhaultItemType) {
            return;
        }
        int type = overhaultItemType.getItemId();
        if (type == OverhaulItemType.CABINET.getType()
                || type == OverhaulItemType.TRANSFORMER.getType()) {  // 柜体\变压器
            SelectCompanyEquipmentByEquipmentTypeParams requestParams = new SelectCompanyEquipmentByEquipmentTypeParams();
            requestParams.setType(type);
            requestParams.setEleAccountId(curEleId);
            HttpClientSend.getInstance().send(requestParams, new BaseStringCallback() {
                @Override
                public void onSuccess(String result) {
                    BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                    if (ret.getErrorCode() != 0) {
                        Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<CompanyEquipmentResult> lst = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<CompanyEquipmentResult>>() {
                    }.getType());
                    lv2Adapter1.setmDataList(lst);
                    lv2Adapter1.notifyDataSetChanged();
                    // 默认点击第一行
                    if (null != lst && lst.size() > 0) {
                        setLv2Click(0);
                    }
                }
            });
        }
    }

    /**
     * 查询设备检修项.
     */
    public void getOverhaulItemList() {
        infoLayout.setVisibility(View.VISIBLE);
        CommonListViewInterface companyEquipment = lv2Adapter1.getChecked();
        if (companyEquipment instanceof CompanyEquipmentResult) {
            CompanyEquipmentResult scebar = (CompanyEquipmentResult) companyEquipment;
            if (null == scebar) {
                return;
            }
            EquipmentDao equipmentDao = new EquipmentDao();
            EquipmentEntity equipmentEntity = equipmentDao.queryById(scebar.getEquipmentId());

            QueryOverhaulItemParams queryOverhaulItemParams = new QueryOverhaulItemParams();
            queryOverhaulItemParams.setEleAccountId(curEleId);
            queryOverhaulItemParams.setItemType(equipmentEntity.getType());
            queryOverhaulItemParams.setCompanyId(curCompany.getCompanyId());
            queryOverhaulItemParams.setComEquOrRoomId(scebar.getCompanyEquipmentId());
            queryOverhaulItemParams.setIsRoom(false);
            HttpClientSend.getInstance().send(queryOverhaulItemParams, new BaseStringCallback() {
                @Override
                public void onSuccess(String result) {
                    BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                    if (ret.getErrorCode() != 0) {
                        Toast.makeText(getActivity(), "获取巡检项失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<OverhaulResult> overhaulResultList = JsonUtils.deserialize(ret.getResult(),
                            new TypeToken<List<OverhaulResult>>() {}.getType());
                    overhaulListAdapter.setmDataList(overhaulResultList);
                    overhaulListAdapter.notifyDataSetChanged();
                }
            });
            infoHolder.nameText.setText(scebar.getName());
        }
    }
}
