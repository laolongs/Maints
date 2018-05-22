package cn.tties.maint.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.httpclient.result.EquipmentInfoResult;

/**
 *
 */
@Table(name = "equipment_item",
        onCreated = "CREATE INDEX equipment_item_search ON equipment_item(itemName)")
public class EquipmentItemEntity extends AbstractEntity implements CommonListViewInterface {

    @Column(name = "id", isId = true, autoGen = true)
    private int id;

    @Column(name = "equipmentItemId")
    private int equipmentItemId;

    @Column(name = "equipmentId")
    private int equipmentId;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "itemOrder")
    private int itemOrder;

    @Column(name = "inputType")
    private int inputType;

    @Column(name = "defaultValue")
    private String defaultValue;

    @Column(name = "unitName")
    private String unitName;

    private EquipmentInfoResult equipmentInfoEntity;

    public int getEquipmentItemId() {
        return equipmentItemId;
    }

    public void setEquipmentItemId(int equipmentItemId) {
        this.equipmentItemId = equipmentItemId;
    }
    //实现接口的三个方法-----------
    @Override
    public Integer getItemId() {
        return null;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void setChecked(boolean checked) {

    }
    //------------
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(int itemOrder) {
        this.itemOrder = itemOrder;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getUnitName() {
        return unitName;
    }
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public EquipmentInfoResult getEquipmentInfoEntity() {
        return equipmentInfoEntity;
    }

    public void setEquipmentInfoEntity(EquipmentInfoResult equipmentInfoEntity) {
        this.equipmentInfoEntity = equipmentInfoEntity;
    }
}
