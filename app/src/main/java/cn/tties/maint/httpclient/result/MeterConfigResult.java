package cn.tties.maint.httpclient.result;

/**
 * Created by fultrust on 2018/1/6.
 */

public class MeterConfigResult {

    private Long taskId;

    private Long ret;

    private String errMsg;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getRet() {
        return ret;
    }

    public void setRet(Long ret) {
        this.ret = ret;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
