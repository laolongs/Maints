package cn.tties.maint.httpclient.result;

import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.common.Constants;

/**
 * Created by fultrust on 2018/1/7.
 */

public class DescriptionResult {

    private Integer questionDescriptionId;

    private Integer questionId;

    private Integer staffId;

    private String content;

    private String createTime;

    private List<DescriptionImageResult> imageList;

    private List<ImageItem> imageBeanList;

    public Integer getQuestionDescriptionId() {
        return questionDescriptionId;
    }

    public void setQuestionDescriptionId(Integer questionDescriptionId) {
        this.questionDescriptionId = questionDescriptionId;
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

    public List<ImageItem> getImageBeanList() {
        if (imageBeanList == null && imageList != null) {
            this.imageBeanList = new ArrayList<>();
            for (DescriptionImageResult result : imageList) {
                ImageItem imageItem = new ImageItem();
                imageItem.path = Constants.BASE_URL + result.getImagePath();
                this.imageBeanList.add(imageItem);
            }
        }
        return imageBeanList;
    }

    public void setImageBeanList(List<ImageItem> imageBeanList) {
        this.imageBeanList = imageBeanList;
    }

    public List<DescriptionImageResult> getImageList() {
        return imageList;
    }

    public void setImageList(List<DescriptionImageResult> imageList) {
        this.imageList = imageList;
    }
}
