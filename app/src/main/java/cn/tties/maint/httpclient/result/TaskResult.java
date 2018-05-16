package cn.tties.maint.httpclient.result;

/**
 * Created by fultrust on 2018/1/6.
 */

public class TaskResult extends FrameResult {

    private Long taskId;

    private String taskName;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

}
