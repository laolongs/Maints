package cn.tties.maint.bean;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.enums.ItemInputType;

/**
 * Created by Justin on 2018/1/11.
 */

public class EquipmentLayoutBean {
    private int type;
    private int inputType;
    private String textName;
    private String value;
    private Integer itemId;
    private Integer dbId;
    private Boolean check;
    private String ID;
    private boolean isLeaf;
    private List<EquipmentLayoutBean> childrenList;
    //用于存父级的name
    private String titleName;
    private Integer equipmentInfoId;
    private Integer companyEquipmentId;
    private Integer pid;
    public EquipmentLayoutBean(int type, Integer itemId) {
        this.type = type;
        this.itemId = itemId;
        childrenList = new ArrayList<>();
        inputType = ItemInputType.STRING.getType();

    }

    public EquipmentLayoutBean(int type, Integer itemId, Integer dbId) {
        this.type = type;
        this.itemId = itemId;
        this.dbId = dbId;
        childrenList = new ArrayList<>();
        inputType = ItemInputType.STRING.getType();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCompanyEquipmentId() {
        return companyEquipmentId;
    }

    public void setCompanyEquipmentId(Integer companyEquipmentId) {
        this.companyEquipmentId = companyEquipmentId;
    }

    public Integer getEquipmentInfoId() {
        return equipmentInfoId;
    }

    public void setEquipmentInfoId(Integer equipmentInfoId) {
        this.equipmentInfoId = equipmentInfoId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public List<EquipmentLayoutBean> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<EquipmentLayoutBean> childrenList) {
        this.childrenList = childrenList;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }
}
