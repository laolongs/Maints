package cn.tties.maint.httpclient.result;

import java.io.Serializable;

/**
 * Created by fultrust on 2018/1/5.
 */

public class QuestionScheduleResult implements Serializable{

    private Integer questionScheduleId;

    private Integer questionId;

    private Integer staffId;

    private String staffName;

    private String content;

    private String createTime;

    private boolean isHead;

    private boolean isEnd;

    private boolean isUndo;

    public Integer getQuestionScheduleId() {
        return questionScheduleId;
    }

    public void setQuestionScheduleId(Integer questionScheduleId) {
        this.questionScheduleId = questionScheduleId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean head) {
        isHead = head;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public boolean isUndo() {
        return isUndo;
    }

    public void setUndo(boolean undo) {
        isUndo = undo;
    }
}
