package cn.tties.maint.bean;

/**
 * Created by Justin on 2018/1/11.
 */

public class CreateEquipTwoParam {

    private String equItemContent;
    private Integer equItemId;
    private Integer equmentInfoId;

    public Integer getEqumentInfoId() {
        return equmentInfoId;
    }

    public void setEqumentInfoId(Integer equmentInfoId) {
        this.equmentInfoId = equmentInfoId;
    }

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
}
