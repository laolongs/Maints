package cn.tties.maint.httpclient.result;

import org.xutils.http.annotation.HttpResponse;

import cn.tties.maint.widget.JsonResponseParser;

/**
 * 根据企业设备id查询设备项信息
 * author Lizhen
 */
@HttpResponse(parser = JsonResponseParser.class)
public class PatrolResult {

    private Integer patrolRecordId;

    private Integer patrolItemId;

    private Integer eleAccountId;

    private Integer workOrderId;

    private Integer companyEquipmentId;

    private Integer roomId;

    private Boolean hasquestion;

    private String value1;

    private String value2;

    private String value3;

    private String value4;

    private String value5;

    private String value6;

//    private String createTime;

    public Integer getPatrolRecordId() {
        return patrolRecordId;
    }

    public void setPatrolRecordId(Integer patrolRecordId) {
        this.patrolRecordId = patrolRecordId;
    }

    public Integer getPatrolItemId() {
        return patrolItemId;
    }

    public void setPatrolItemId(Integer patrolItemId) {
        this.patrolItemId = patrolItemId;
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

    public Boolean getHasquestion() {
        return hasquestion;
    }

    public void setHasquestion(Boolean hasquestion) {
        this.hasquestion = hasquestion;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getValue4() {
        return value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }

    public String getValue5() {
        return value5;
    }

    public void setValue5(String value5) {
        this.value5 = value5;
    }

    public String getValue6() {
        return value6;
    }

    public void setValue6(String value6) {
        this.value6 = value6;
    }

    public Integer getCompanyEquipmentId() {
        return companyEquipmentId;
    }

    public void setCompanyEquipmentId(Integer companyEquipmentId) {
        this.companyEquipmentId = companyEquipmentId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    //    public String getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(String createTime) {
//        this.createTime = createTime;
//    }
}
