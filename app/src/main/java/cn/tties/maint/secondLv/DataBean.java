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
