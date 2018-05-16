package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 上报任务配置
 * author chensi
 */
public class GetAutoBatchStatusParams extends ClinetRequestParams {

    public static final String INTERFACE = "getAutoBatchStatus.do";

    private Long frameId;

    private Long terminalId;

    private Long taskId;


    public Long getFrameId() {
        return frameId;
    }

    public void setFrameId(Long frameId) {
        this.frameId = frameId;
    }

    public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
