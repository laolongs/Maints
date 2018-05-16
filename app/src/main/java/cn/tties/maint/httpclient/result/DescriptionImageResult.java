package cn.tties.maint.httpclient.result;

/**
 * Created by fultrust on 2018/1/7.
 */

public class DescriptionImageResult {

    private Integer questionDescriptionImageId;

    private Integer questionDescriptionId;

    private String imagePath;

    private String createTime;

    public Integer getQuestionDescriptionImageId() {
        return questionDescriptionImageId;
    }

    public void setQuestionDescriptionImageId(Integer questionDescriptionImageId) {
        this.questionDescriptionImageId = questionDescriptionImageId;
    }

    public Integer getQuestionDescriptionId() {
        return questionDescriptionId;
    }

    public void setQuestionDescriptionId(Integer questionDescriptionId) {
        this.questionDescriptionId = questionDescriptionId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
