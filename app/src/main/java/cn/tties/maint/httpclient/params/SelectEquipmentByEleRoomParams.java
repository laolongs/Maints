package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 查询电房所有企业设备.
 */

public class SelectEquipmentByEleRoomParams extends ClinetRequestParams {
    private Integer roomId;

    private Integer eleAccountId;

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public static final String INTERFACE = "selectEquipmentByEleRoom.do";
}
