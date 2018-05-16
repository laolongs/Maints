package cn.tties.maint.httpclient.result;

/**
 * Created by fultrust on 2018/1/7.
 */

public class AdviceResult {

    private Integer questionAdviceId;

    private Integer questionId;

    private Integer staffId;

    private String content;

    private String createTime;

    public Integer getQuestionAdviceId() {
        return questionAdviceId;
    }

    public void setQuestionAdviceId(Integer questionAdviceId) {
        this.questionAdviceId = questionAdviceId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
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
}
