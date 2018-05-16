package cn.tties.maint.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.enums.EquipmentType;

/**
 *
 */
@Table(name = "equipment",
        onCreated = "CREATE INDEX equipment_search ON equipment(pid)")
public class EquipmentEntity extends AbstractEntity implements CommonListViewInterface {

    @Column(name = "id", isId = true, autoGen = true)
    private Integer id;

    @Column(name = "equipmentId")
    private Integer equipmentId;

    @Column(name = "equipmentName")
    private String equipmentName;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "type")
    private Integer type;

    @Column(name = "equipmentLevel")
    private Integer equipmentLevel;

    @Column(name = "equipmentOrder")
    private Integer equipmentOrder;

    @Column(name = "isLeafNode")
    private boolean isLeafNode;

    @Column(name = "hasPatrol")
    private boolean hasPatrol;

    @Column(name = "unitName")
    private String unitName;

    private boolean isChecked;

    private List<EquipmentItemEntity> itemList;

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getType() {
        return type;
    }

    public EquipmentType getEquipmentType() {
        return EquipmentType.getTpye(type);
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getEquipmentLevel() {
        return equipmentLevel;
    }

    public void setEquipmentLevel(Integer equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }

    public Integer getEquipmentOrder() {
        return equipmentOrder;
    }

    public void setEquipmentOrder(Integer equipmentOrder) {
        this.equipmentOrder = equipmentOrder;
    }

    public boolean getIsLeafNode() {
        return !isLeafNode;
    }

    public void setIsLeafNode(boolean isLeafNode) {
        this.isLeafNode = isLeafNode;
    }

    public boolean getHasPatrol() {
        return hasPatrol;
    }

    public void setHasPatrol(boolean hasPatrol) {
        this.hasPatrol = hasPatrol;
    }

    public List<EquipmentItemEntity> getItemList() {
        return  itemList == null ? new ArrayList<EquipmentItemEntity>() : itemList;
    }

    public void setItemList(List<EquipmentItemEntity> itemList) {
        this.itemList = itemList;
    }
    public String getUnitName() {
        return unitName;
    }
    public void setUnitName(String unitName) {
        this.unitName = unitName;
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
        return this.getEquipmentId();
    }

    @Override
    public String getItemName() {
        return this.getEquipmentName();
    }
}
