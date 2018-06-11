package cn.tties.maint.bean;

/**
 * Created by Justin on 2018/1/11.
 */

import java.util.List;

public class CreateEquipFiveParam {
    private String equItemContent;
    private Integer equItemId;
    private Integer equipmentInfoId;

    public String getEquItemContent() {
        return equItemContent;
    }

    public void setEquItemContent(String equItemContent) {
        this.equItemContent = equItemContent;
    }

    public Integer getEquItemId() {
        return equItemId;
    }

    public void setEquItemId(Integer equItemId) {
        this.equItemId = equItemId;
    }

    public Integer getEquipmentInfoId() {
        return equipmentInfoId;
    }

    public void setEquipmentInfoId(Integer equipmentInfoId) {
        this.equipmentInfoId = equipmentInfoId;
    }
}
