package cn.tties.maint.httpclient.params;

import java.util.List;

import cn.tties.maint.httpclient.ClinetRequestParams;
import cn.tties.maint.httpclient.result.PatrolResult;
import cn.tties.maint.util.JsonUtils;

/**
 * Created by fultrust on 2018/1/11.
 */


public class AddPatrolParam  extends ClinetRequestParams {
    public static final String INTERFACE ="savePatrolRecord.do";

    private String patrolRecordList;

    public String getPatrolRecordList() {
        return patrolRecordList;
    }

    public void setPatrolRecordList(List<PatrolResult> patrolRecordList) {
        this.patrolRecordList = JsonUtils.getJsonArrayStr(patrolRecordList);
    }
}
