package cn.tties.maint.httpclient.result;

import java.io.Serializable;


/**
 * Created by lizhen on 2018/1/6.
 */

public class OverhaulResult implements Serializable {

    private Integer overhaulItemId;

    private String itemContent;

    private Integer itemType;

    private boolean finish;

    private Integer eleAccountId;

    private Integer workOrderId;

    private Integer companyId;

    private Integer comEquOrRoomId;

    private boolean isRoom;

    public Integer getComEquOrRoomId() {
        return comEquOrRoomId;
    }

    public void setComEquOrRoomId(Integer comEquOrRoomId) {
        this.comEquOrRoomId = comEquOrRoomId;
    }

    public boolean getIsRoom() {
        return isRoom;
    }

    public void setIsRoom(boolean room) {
        isRoom = room;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getOverhaulItemId() {
        return overhaulItemId;
    }

    public void setOverhaulItemId(Integer overhaulItemId) {
        this.overhaulItemId = overhaulItemId;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public boolean getFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }
}
