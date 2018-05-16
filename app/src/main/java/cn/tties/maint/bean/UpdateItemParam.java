package cn.tties.maint.bean;

/**
 * Created by Justin on 2018/1/11.
 */

public class UpdateItemParam {
    private Integer equipmentItemId;
    private Integer equipmentInfoId;
    private String newEquipmentInfo;

    public Integer getEquipmentInfoId() {
        return equipmentInfoId;
    }

    public void setEquipmentInfoId(Integer equipmentInfoId) {
        this.equipmentInfoId = equipmentInfoId;
    }

    public String getNewEquipmentInfo() {
        return newEquipmentInfo;
    }

    public void setNewEquipmentInfo(String newEquipmentInfo) {
        this.newEquipmentInfo = newEquipmentInfo;
    }

    public Integer getEquipmentItemId() {
        return equipmentItemId;
    }

    public void setEquipmentItemId(Integer equipmentItemId) {
        this.equipmentItemId = equipmentItemId;
    }
}
