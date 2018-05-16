package cn.tties.maint.entity;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.enums.OverhaulItemType;

/**
 * Created by Justin on 2018/1/9.
 */

public class OverhaulItemTypeEntity implements CommonListViewInterface {
    private int type;
    private String name;
    private boolean isChecked;

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getItemId() {
        return this.type;
    }

    @Override
    public String getItemName() {
        return this.name;
    }

    @Override
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override
    public void setChecked(boolean checkedItem) {
        this.isChecked = checkedItem;
    }

    private static List<OverhaulItemTypeEntity> overhaulItemTypeList;
    public static List<OverhaulItemTypeEntity> getOverhaulItemTypeList() {
        if (null == overhaulItemTypeList) {
            overhaulItemTypeList = new ArrayList<>();
        }
        if (overhaulItemTypeList.size() > 0) {
            return overhaulItemTypeList;
        }
        OverhaulItemTypeEntity cabinet = new OverhaulItemTypeEntity();
        cabinet.setType(OverhaulItemType.CABINET.getType());
        cabinet.setName(OverhaulItemType.CABINET.getInfo());
        overhaulItemTypeList.add(cabinet);
        OverhaulItemTypeEntity transfrmer = new OverhaulItemTypeEntity();
        transfrmer.setType(OverhaulItemType.TRANSFORMER.getType());
        transfrmer.setName(OverhaulItemType.TRANSFORMER.getInfo());
        overhaulItemTypeList.add(transfrmer);
        return overhaulItemTypeList;
    }
}
