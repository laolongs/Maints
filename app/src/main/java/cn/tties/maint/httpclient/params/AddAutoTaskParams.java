package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 配置任务
 * author chensi
 */
public class AddAutoTaskParams extends ClinetRequestParams {

    public static final String INTERFACE = "addAutoTask.do";

    private Long terminalId;
    private long taskId;

    public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

}
