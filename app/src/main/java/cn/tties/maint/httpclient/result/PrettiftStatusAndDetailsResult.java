package cn.tties.maint.httpclient.result;

import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.common.Constants;

/**
 * Created by li on 2018/6/21
 * description：美化安规状态及详情
 * author：guojlli
 */

public class PrettiftStatusAndDetailsResult {

    /**
     * result : [{"companyId":23,"flag":0,"createTime":"Thu Jun 21 19:39:11 CST 2018","descriptionType":1,"workOrderId":114,"prettifyDustType":1,"listImg":[{"flag":0,"createTime":"Thu Jun 21 19:39:11 CST 2018","imagePath":"prettifyDustDescription\\1529581151174.png","prettifyDustDescriptionImageId":11,"prettifyDustDescriptionId":16}],"prettifyDustDescriptionId":16},{"companyId":23,"flag":0,"createTime":"Thu Jun 21 19:50:38 CST 2018","descriptionType":2,"maintStaffId":160,"workOrderId":114,"prettifyDustType":1,"listImg":[{"flag":0,"createTime":"Thu Jun 21 19:50:38 CST 2018","imagePath":"prettifyDustDescription\\1529581838365.png","prettifyDustDescriptionImageId":16,"prettifyDustDescriptionId":21}],"prettifyDustDescriptionId":21,"content":"古巨基"}]
     * errorMessage : 成功
     * errorCode : 0
     */

    private String errorMessage;
    private int errorCode;
    private List<ResultBean> result;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * companyId : 23
         * flag : 0
         * createTime : Thu Jun 21 19:39:11 CST 2018
         * descriptionType : 1
         * workOrderId : 114
         * prettifyDustType : 1
         * listImg : [{"flag":0,"createTime":"Thu Jun 21 19:39:11 CST 2018","imagePath":"prettifyDustDescription\\1529581151174.png","prettifyDustDescriptionImageId":11,"prettifyDustDescriptionId":16}]
         * prettifyDustDescriptionId : 16
         * maintStaffId : 160
         * content : 古巨基
         */

        private int companyId;
        private int flag;
        private String createTime;
        private int descriptionType;
        private int workOrderId;
        private int prettifyDustType;
        private int prettifyDustDescriptionId;
        private int maintStaffId;
        private String content;
        private List<ListImgBean> listImg;
        private List<ImageItem> imageBeanList;
        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDescriptionType() {
            return descriptionType;
        }

        public void setDescriptionType(int descriptionType) {
            this.descriptionType = descriptionType;
        }

        public int getWorkOrderId() {
            return workOrderId;
        }

        public void setWorkOrderId(int workOrderId) {
            this.workOrderId = workOrderId;
        }

        public int getPrettifyDustType() {
            return prettifyDustType;
        }

        public void setPrettifyDustType(int prettifyDustType) {
            this.prettifyDustType = prettifyDustType;
        }

        public int getPrettifyDustDescriptionId() {
            return prettifyDustDescriptionId;
        }

        public void setPrettifyDustDescriptionId(int prettifyDustDescriptionId) {
            this.prettifyDustDescriptionId = prettifyDustDescriptionId;
        }

        public int getMaintStaffId() {
            return maintStaffId;
        }

        public void setMaintStaffId(int maintStaffId) {
            this.maintStaffId = maintStaffId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<ImageItem> getListImg() {
            if (imageBeanList== null &&listImg != null) {
                this.imageBeanList = new ArrayList<>();
                for (ListImgBean result : listImg) {
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = Constants.BASE_URL + result.getImagePath();
                    this.imageBeanList.add(imageItem);
                }
            }
            return imageBeanList;
        }

        public void setListImg(List<ListImgBean> listImg) {
            this.listImg = listImg;
        }

        public static class ListImgBean {
            /**
             * flag : 0
             * createTime : Thu Jun 21 19:39:11 CST 2018
             * imagePath : prettifyDustDescription\1529581151174.png
             * prettifyDustDescriptionImageId : 11
             * prettifyDustDescriptionId : 16
             */

            private int flag;
            private String createTime;
            private String imagePath;
            private int prettifyDustDescriptionImageId;
            private int prettifyDustDescriptionId;

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public int getPrettifyDustDescriptionImageId() {
                return prettifyDustDescriptionImageId;
            }

            public void setPrettifyDustDescriptionImageId(int prettifyDustDescriptionImageId) {
                this.prettifyDustDescriptionImageId = prettifyDustDescriptionImageId;
            }

            public int getPrettifyDustDescriptionId() {
                return prettifyDustDescriptionId;
            }

            public void setPrettifyDustDescriptionId(int prettifyDustDescriptionId) {
                this.prettifyDustDescriptionId = prettifyDustDescriptionId;
            }
        }
    }
}
