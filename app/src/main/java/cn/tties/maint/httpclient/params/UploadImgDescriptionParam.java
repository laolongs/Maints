package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 【问题处理】添加问题描述的功能.
 */

public class UploadImgDescriptionParam  extends ClinetRequestParams {

    public static final String INTERFACE = "createQuestionDescriptionParam.do";

    private Integer questionId;
    private Integer maintStaffId;
    private  String content;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getMaintStaffId() {
        return maintStaffId;
    }

    public void setMaintStaffId(Integer maintStaffId) {
        this.maintStaffId = maintStaffId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


