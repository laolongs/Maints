package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 添加检修记录.
 */

public class Add0verHaulRecordParams extends ClinetRequestParams {

    public static final String INTERFACE ="add0verHaulRecord.do";

    private Integer overhaulItemId;
    private Integer eleAccountId;
    private Integer workOrderId;
    private Boolean isdeal;
    private Integer companyEquipmentId;
    private Integer roomId;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getCompanyEquipmentId() {
        return companyEquipmentId;
    }

    public void setCompanyEquipmentId(Integer companyEquipmentId) {
        this.companyEquipmentId = companyEquipmentId;
    }

    public Integer getOverhaulItemId() {
        return overhaulItemId;
    }

    public void setOverhaulItemId(Integer overhaulItemId) {
        this.overhaulItemId = overhaulItemId;
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

    public Boolean getIsdeal() {
        return isdeal;
    }

    public void setIsdeal(Boolean isdeal) {
        this.isdeal = isdeal;
    }
}
