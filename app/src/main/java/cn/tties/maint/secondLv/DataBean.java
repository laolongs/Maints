package cn.tties.maint.secondLv;

import java.util.List;

import cn.tties.maint.bean.EquipmentLayoutBean;

/**
 * Created by li on 2018/5/11
 * description：
 * author：guojlli
 */

public class DataBean {
    public static final int PARENT_ITEM = 0;//父布局
    public static final int CHILD_ITEM = 1;//子布局

    private int type;// 显示类型
    private boolean isExpand;// 是否展开
    private DataBean childBean;

    private String ID;
    private String parentLeftTxt;
    private String parentRightTxt;
    private List<EquipmentLayoutBean> bean;
    private int itemid;
    private int companyEquipmentId;
    private int equipmentId;
    private int pid;
    //记录是否叶结点
    private boolean isLeaf;
    //是否要删除来自企业设备得信息
    private boolean flag;
    //记录企业设备信息
    private Integer record;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    public int getCompanyEquipmentId() {
        return companyEquipmentId;
    }

    public void setCompanyEquipmentId(int companyEquipmentId) {
        this.companyEquipmentId = companyEquipmentId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public List<EquipmentLayoutBean> getBean() {
        return bean;
    }

    public void setBean(List<EquipmentLayoutBean> bean) {
        this.bean = bean;
    }

    public String getParentLeftTxt() {
        return parentLeftTxt;
    }

    public void setParentLeftTxt(String parentLeftTxt) {
        this.parentLeftTxt = parentLeftTxt;
    }

    public String getParentRightTxt() {
        return parentRightTxt;
    }

    public void setParentRightTxt(String parentRightTxt) {
        this.parentRightTxt = parentRightTxt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public DataBean getChildBean() {
        return childBean;
    }

    public void setChildBean(DataBean childBean) {
        this.childBean = childBean;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
