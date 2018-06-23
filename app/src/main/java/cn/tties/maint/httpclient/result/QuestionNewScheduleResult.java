package cn.tties.maint.httpclient.result;

import com.lzy.imagepicker.bean.ImageItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.common.Constants;

/**
 * Created by fultrust on 2018/1/5.
 */

public class QuestionNewScheduleResult implements Serializable{
    /**
     * questionScheduleId : 1
     * imageList : [{"imagePath":"\\energyBgi.png","questionScheduleImageId":2}]
     * content : 工程师chensi_maint正在处理
     */

    private int questionScheduleId;
    private String content;
    private List<QueryFaultResult.ResultBean.QuestionScheduleListBean.ImageListBeanX> imageList;
    private List<ImageItem> imageBeanList;

    public int getQuestionScheduleId() {
        return questionScheduleId;
    }

    public void setQuestionScheduleId(int questionScheduleId) {
        this.questionScheduleId = questionScheduleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ImageItem> getImageBeanList() {
        if (imageBeanList == null && imageList != null) {
            this.imageBeanList = new ArrayList<>();
            for (QueryFaultResult.ResultBean.QuestionScheduleListBean.ImageListBeanX result : imageList) {
                ImageItem imageItem = new ImageItem();
                imageItem.path = Constants.BASE_URL + result.getImagePath();
                this.imageBeanList.add(imageItem);
            }
        }
        return imageBeanList;
    }

    public void setImageList(List<QueryFaultResult.ResultBean.QuestionScheduleListBean.ImageListBeanX> imageList) {
        this.imageList = imageList;
    }

    public static class ImageListBeanX {
        /**
         * imagePath : \energyBgi.png
         * questionScheduleImageId : 2
         */

        private String imagePath;
        private int questionScheduleImageId;

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public int getQuestionScheduleImageId() {
            return questionScheduleImageId;
        }

        public void setQuestionScheduleImageId(int questionScheduleImageId) {
            this.questionScheduleImageId = questionScheduleImageId;
        }
    }
}
