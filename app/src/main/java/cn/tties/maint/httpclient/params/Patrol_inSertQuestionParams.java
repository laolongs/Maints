package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 * 无问题  单个提交
 */

public class Patrol_inSertQuestionParams extends ClinetRequestParams {
    
    public static final String INTERFACE = "insertPatrolRecore.do";

    private Integer eleAccountId;
    private Boolean hasquestion;
    private Integer patrolItemId;
    private Integer companyEquipmentId;
    private Integer workOrderId;
    private Integer roomId;

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public Boolean getHasquestion() {
        return hasquestion;
    }

    public void setHasquestion(Boolean hasquestion) {
        this.hasquestion = hasquestion;
    }

    public Integer getPatrolItemId() {
        return patrolItemId;
    }

    public void setPatrolItemId(Integer patrolItemId) {
        this.patrolItemId = patrolItemId;
    }

    public Integer getCompanyEquipmentId() {
        return companyEquipmentId;
    }

    public void setCompanyEquipmentId(Integer companyEquipmentId) {
        this.companyEquipmentId = companyEquipmentId;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
