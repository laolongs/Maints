package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 修改工单状态请求参数.
 */
public class UpdateOrderStatusParams extends ClinetRequestParams {
    public static final String INTERFACE = "updateWorkOrderStatus.do";
    private int workOrderId;
    private int status;
    private int isSend;

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }
}
