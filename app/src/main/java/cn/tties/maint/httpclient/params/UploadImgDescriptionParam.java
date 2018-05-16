package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 【问题处理】添加问题描述的功能.
 */

public class UploadImgDescriptionParam  extends ClinetRequestParams {

    public static final String INTERFACE = "UploadImgDescriptionParam.do";

    private Integer questionId;
    private Integer staffId;
    private  String content;

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public String getContent() {
        return content;
    }
}


