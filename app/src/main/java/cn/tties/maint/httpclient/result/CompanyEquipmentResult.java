package cn.tties.maint.httpclient.result;

import java.io.Serializable;

import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.entity.EquipmentEntity;

/**
 *
 */
public class CompanyEquipmentResult implements CommonListViewInterface,Serializable {

    private Integer companyEquipmentId;

    private Integer equipmentId;

    private Integer eleAccountId;

    private Integer pid;

    private Integer roomId;

    private String name;

    private String createTime;
    private String position;//地址

    private Integer flag;

    private EquipmentEntity equipmentEntity;

    private boolean isChecked;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getCompanyEquipmentId() {
        return companyEquipmentId;
    }

    public void setCompanyEquipmentId(Integer companyEquipmentId) {
        this.companyEquipmentId = companyEquipmentId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPid() {
        return pid;
    }

    public EquipmentEntity getEquipmentEntity() {
        return equipmentEntity;
    }

    public void setEquipmentEntity(EquipmentEntity equipmentEntity) {
        this.equipmentEntity = equipmentEntity;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public Integer getItemId() {
        return this.companyEquipmentId;
    }

    @Override
    public String getItemName() {
        return this.name;
    }
}
