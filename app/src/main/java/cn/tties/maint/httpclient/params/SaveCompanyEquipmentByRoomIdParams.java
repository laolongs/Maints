package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 分配设备.
 */

public class SaveCompanyEquipmentByRoomIdParams  extends ClinetRequestParams {
private  String companyEquipmentId;
private  Integer RoomId;

    public void setCompanyEquipmentId(String companyEquipmentId) {
        this.companyEquipmentId = companyEquipmentId;
    }

    public void setRoomId(Integer roomId) {
        RoomId = roomId;
    }

    public String getCompanyEquipmentId() {
        return companyEquipmentId;
    }

    public Integer getRoomId() {
        return RoomId;
    }

    public static final String INTERFACE = "saveCompanyEquipmentByRoomId.do";
}
