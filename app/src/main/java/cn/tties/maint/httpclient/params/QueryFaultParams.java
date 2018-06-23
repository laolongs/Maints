package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/7.
 * 查询消缺列表
 */

public class QueryFaultParams extends ClinetRequestParams {

    public static final String INTERFACE ="queryAllQuestionDescriptionAndQuestionSchedule.do";

    private int workOrderId;

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }
}
