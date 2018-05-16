package cn.tties.maint.httpclient.result;

import java.util.List;

import cn.tties.maint.enums.FaultType;

/**
 * Created by fultrust on 2018/1/7.
 */

public class QuertionResult {

    private Integer questionId;

    private Integer patrolRecordId;

    private Integer companyId;

    private Integer type;

    private String description;

    private Integer status;

    private String createTime;

    private Integer patrolItemId;

    private String title;

    private Integer faultType;

    private String faultHarm;

    private String dealDesc;

    private String companyName;

    private List<AdviceResult> adviceList;

    private List<DescriptionResult> descriptionList;

    private List<QuestionScheduleResult> scheduleList;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getPatrolRecordId() {
        return patrolRecordId;
    }

    public void setPatrolRecordId(Integer patrolRecordId) {
        this.patrolRecordId = patrolRecordId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getPatrolItemId() {
        return patrolItemId;
    }

    public void setPatrolItemId(Integer patrolItemId) {
        this.patrolItemId = patrolItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFaultType() {
        return faultType;
    }

    public String getFaultTypeType() {
        return FaultType.getInfo(faultType);
    }

    public void setFaultType(Integer faultType) {
        this.faultType = faultType;
    }

    public String getFaultHarm() {
        return faultHarm;
    }

    public void setFaultHarm(String faultHarm) {
        this.faultHarm = faultHarm;
    }

    public String getDealDesc() {
        return dealDesc;
    }

    public void setDealDesc(String dealDesc) {
        this.dealDesc = dealDesc;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<AdviceResult> getAdviceList() {
        return adviceList;
    }

    public void setAdviceList(List<AdviceResult> adviceList) {
        this.adviceList = adviceList;
    }

    public List<DescriptionResult> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(List<DescriptionResult> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public List<QuestionScheduleResult> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<QuestionScheduleResult> scheduleList) {
        this.scheduleList = scheduleList;
    }
}
