package cn.tties.maint.bean;

import java.util.List;

/**
 * Created by Justin on 2018/1/11.
 */

public class UpdateEquipParam {
    private Integer comEquId;
    private String newComEquName;
    private List<UpdateItemParam> updateEquItemInfo;
    private Integer pid;
    private Integer equipId;

    public Integer getComEquId() {
        return comEquId;
    }

    public void setComEquId(Integer comEquId) {
        this.comEquId = comEquId;
    }

    public String getNewComEquName() {
        return newComEquName;
    }

    public void setNewComEquName(String newComEquName) {
        this.newComEquName = newComEquName;
    }

    public List<UpdateItemParam> getUpdateEquItemInfo() {
        return updateEquItemInfo;
    }

    public void setUpdateEquItemInfo(List<UpdateItemParam> updateEquItemInfo) {
        this.updateEquItemInfo = updateEquItemInfo;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getEquipId() {
        return equipId;
    }

    public void setEquipId(Integer equipId) {
        this.equipId = equipId;
    }
}
