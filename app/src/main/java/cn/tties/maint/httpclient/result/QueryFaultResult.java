package cn.tties.maint.httpclient.result;

import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.common.Constants;

/**
 * Created by fultrust on 2018/1/7.
 * 查询消缺列表
 */

public class QueryFaultResult {


    /**
     * result : [{"questionId":98,"description":"[电房]","patrolRecordId":98,"questionDescriptionList":[{"imageList":[{"imagePath":"images\\energyBgi.png","questionDescriptionImageId":66}],"questionDescriptionId":143,"content":"哈哈哈哈端午放三天"}],"companyShortName":"测试简称3","type":5,"questionIndex":"Q5000098","patrolItemId":21,"companyId":7,"dealTime":"Tue Jan 16 15:50:17 CST 2018","createTime":"Tue Jan 16 15:50:17 CST 2018","faultType":1,"questionScheduleList":[{"questionScheduleId":1,"imageList":[{"imagePath":"\\energyBgi.png","questionScheduleImageId":2}],"content":"工程师chensi_maint正在处理"}],"status":2},{"questionId":238,"description":"[rew, 联络柜柜体, 联络柜]","patrolRecordId":1218,"questionDescriptionList":[{"imageList":[{"imagePath":"images\\energyBgi.png","questionDescriptionImageId":58}],"questionDescriptionId":141,"content":"测试是否漏点   \n\n貌似漏了"}],"companyShortName":"testshortname","type":1,"questionIndex":"Q1001218","patrolItemId":77,"companyId":23,"dealTime":"Mon May 28 09:58:24 CST 2018","createTime":"Mon May 28 09:58:24 CST 2018","faultType":2,"questionScheduleList":[{"questionScheduleId":141,"imageList":[],"content":"工程师chengsi_maint正在处理"}],"questionType":3,"status":2}]
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
         * questionId : 98
         * description : [电房]
         * patrolRecordId : 98
         * questionDescriptionList : [{"imageList":[{"imagePath":"images\\energyBgi.png","questionDescriptionImageId":66}],"questionDescriptionId":143,"content":"哈哈哈哈端午放三天"}]
         * companyShortName : 测试简称3
         * type : 5
         * questionIndex : Q5000098
         * patrolItemId : 21
         * companyId : 7
         * dealTime : Tue Jan 16 15:50:17 CST 2018
         * createTime : Tue Jan 16 15:50:17 CST 2018
         * faultType : 1
         * questionScheduleList : [{"questionScheduleId":1,"imageList":[{"imagePath":"\\energyBgi.png","questionScheduleImageId":2}],"content":"工程师chensi_maint正在处理"}]
         * status : 2
         * questionType : 3
         */

        private int questionId;
        private String description;
        private int patrolRecordId;
        private String companyShortName;
        private int type;
        private String questionIndex;
        private int patrolItemId;
        private int companyId;
        private String dealTime;
        private String createTime;
        private int faultType;
        private int status;
        private int questionType;
        private List<QuestionDescriptionListBean> questionDescriptionList;
        private List<QuestionScheduleListBean> questionScheduleList;

        public int getQuestionId() {
            return questionId;
        }

        public void setQuestionId(int questionId) {
            this.questionId = questionId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getPatrolRecordId() {
            return patrolRecordId;
        }

        public void setPatrolRecordId(int patrolRecordId) {
            this.patrolRecordId = patrolRecordId;
        }

        public String getCompanyShortName() {
            return companyShortName;
        }

        public void setCompanyShortName(String companyShortName) {
            this.companyShortName = companyShortName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getQuestionIndex() {
            return questionIndex;
        }

        public void setQuestionIndex(String questionIndex) {
            this.questionIndex = questionIndex;
        }

        public int getPatrolItemId() {
            return patrolItemId;
        }

        public void setPatrolItemId(int patrolItemId) {
            this.patrolItemId = patrolItemId;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getDealTime() {
            return dealTime;
        }

        public void setDealTime(String dealTime) {
            this.dealTime = dealTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getFaultType() {
            return faultType;
        }

        public void setFaultType(int faultType) {
            this.faultType = faultType;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getQuestionType() {
            return questionType;
        }

        public void setQuestionType(int questionType) {
            this.questionType = questionType;
        }

        public List<QuestionDescriptionListBean> getQuestionDescriptionList() {
            return questionDescriptionList;
        }

        public void setQuestionDescriptionList(List<QuestionDescriptionListBean> questionDescriptionList) {
            this.questionDescriptionList = questionDescriptionList;
        }

        public List<QuestionScheduleListBean> getQuestionScheduleList() {
            return questionScheduleList;
        }

        public void setQuestionScheduleList(List<QuestionScheduleListBean> questionScheduleList) {
            this.questionScheduleList = questionScheduleList;
        }

        public static class QuestionDescriptionListBean {
            /**
             * imageList : [{"imagePath":"images\\energyBgi.png","questionDescriptionImageId":66}]
             * questionDescriptionId : 143
             * content : 哈哈哈哈端午放三天
             */

            private int questionDescriptionId;
            private String content;
            private List<ImageListBean> imageList;
            private List<ImageItem> imageBeanList;

            public int getQuestionDescriptionId() {
                return questionDescriptionId;
            }

            public void setQuestionDescriptionId(int questionDescriptionId) {
                this.questionDescriptionId = questionDescriptionId;
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
                    for (ImageListBean result : imageList) {
                        ImageItem imageItem = new ImageItem();
                        imageItem.path = Constants.BASE_URL + result.getImagePath();
                        this.imageBeanList.add(imageItem);
                    }
                }
                return imageBeanList;
            }

            public void setImageList(List<ImageListBean> imageList) {
                this.imageList = imageList;
            }

            public static class ImageListBean {
                /**
                 * imagePath : images\energyBgi.png
                 * questionDescriptionImageId : 66
                 */

                private String imagePath;
                private int questionDescriptionImageId;

                public String getImagePath() {
                    return imagePath;
                }

                public void setImagePath(String imagePath) {
                    this.imagePath = imagePath;
                }

                public int getQuestionDescriptionImageId() {
                    return questionDescriptionImageId;
                }

                public void setQuestionDescriptionImageId(int questionDescriptionImageId) {
                    this.questionDescriptionImageId = questionDescriptionImageId;
                }
            }
        }

        public static class QuestionScheduleListBean {
            /**
             * questionScheduleId : 1
             * imageList : [{"imagePath":"\\energyBgi.png","questionScheduleImageId":2}]
             * content : 工程师chensi_maint正在处理
             */

            private int questionScheduleId;
            private String content;
            private List<ImageListBeanX> imageList;
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
                    for (ImageListBeanX result : imageList) {
                        ImageItem imageItem = new ImageItem();
                        imageItem.path = Constants.BASE_URL + result.getImagePath();
                        this.imageBeanList.add(imageItem);
                    }
                }
                return imageBeanList;
            }

            public void setImageList(List<ImageListBeanX> imageList) {
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
    }
}
