package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 * 电房巡视  新建问题  有问题  单个提交
 */

public class Patrol_QuestionParams extends ClinetRequestParams {
    
    public static final String INTERFACE = "insertPatrolRecoreAndQuestion.do";

    private Integer eleAccountId;
    private boolean hasquestion;
    private Integer patrolItemId;
    private Integer companyEquipmentId;
    private String value1;
    private String value2;
    private String value3;
    private Integer workOrderId;
    private Integer roomId;
    private Integer staffId;
    private String content;
    private Integer questionFaultType;//级别类型（枚举）

    public Integer getQuestionFaultType() {
        return questionFaultType;
    }

    public void setQuestionFaultType(Integer questionFaultType) {
        this.questionFaultType = questionFaultType;
    }

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public boolean isHasquestion() {
        return hasquestion;
    }

    public void setHasquestion(boolean hasquestion) {
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

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
