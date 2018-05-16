package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/8.
 */

public class SavePatrolRecordParams  extends ClinetRequestParams {
    private Integer patrolItemId;
    private Integer eleAccountId;
    private Integer workOrderId;
    private Boolean hasquestion;
    private String createTime;

    public void setPatrolItemId(Integer patrolItemId) {
        this.patrolItemId = patrolItemId;
    }

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public void setHasquestion(Boolean hasquestion) {
        this.hasquestion = hasquestion;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getPatrolItemId() {
        return patrolItemId;
    }

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public Boolean getHasquestion() {
        return hasquestion;
    }

    public String getCreateTime() {
        return createTime;
    }

    public static final String INTERFACE = "savePatrolRecord.do";
}
