package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 查询工单列表请求参数.
 */
public class QueryOverhaulItemParams extends ClinetRequestParams {

    public static final String INTERFACE = "query0verHaulItem.do";
    private Integer itemType;

    private Integer eleAccountId;

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

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
