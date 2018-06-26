package cn.tties.maint.httpclient.result;

import com.lzy.imagepicker.bean.ImageItem;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.common.Constants;
import cn.tties.maint.widget.JsonResponseParser;

/**
 * 根据企业设备id查询设备项信息
 * author Lizhen
 */
@HttpResponse(parser = JsonResponseParser.class)
public class PatrolResult {

        /**
         * quertion : {"questionId":271,"fault_type":1,"description":"[test, 架空线]","patrolRecordId":1262,"questionDescriptionList":[{"questionDescriptionId":172,"imageList":[{"imagePath":"questionDescription\\1529833519904.png","questionDescriptionImageId":94}],"content":"北机路口"}],"type":3,"questionIndex":"Q3001262","companyId":23,"dealTime":"2018-06-24 17:45:19","createTime":"2018-06-24 17:45:19","questionType":3,"questionScheduleList":[{"questionScheduleId":170,"imageList":[],"content":"工程师康大师正在处理"}],"status":0}
         * createTime : 2018-06-24 17:45:19
         * value1 :
         * patrolRecordId : 1262
         * companyEquipmentId : 940
         * workOrderId : 12
         * hasquestion : true
         * eleAccountId : 54
         * roomId : 40
         */

        private QuertionBean quertion;
        private String createTime;
        private String value1;
        private Integer patrolRecordId;
        private int companyEquipmentId;
        private int workOrderId;
        private boolean hasquestion;
        private int eleAccountId;
        private int roomId;
        private Integer patrolItemId;

        public Integer getPatrolItemId() {
            return patrolItemId;
        }

        public void setPatrolItemId(Integer patrolItemId) {
            this.patrolItemId = patrolItemId;
        }

        public QuertionBean getQuertion() {
            return quertion;
        }

        public void setQuertion(QuertionBean quertion) {
            this.quertion = quertion;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public Integer getPatrolRecordId() {
            return patrolRecordId;
        }

        public void setPatrolRecordId(int patrolRecordId) {
            this.patrolRecordId = patrolRecordId;
        }

        public int getCompanyEquipmentId() {
            return companyEquipmentId;
        }

        public void setCompanyEquipmentId(int companyEquipmentId) {
            this.companyEquipmentId = companyEquipmentId;
        }

        public int getWorkOrderId() {
            return workOrderId;
        }

        public void setWorkOrderId(int workOrderId) {
            this.workOrderId = workOrderId;
        }

        public boolean getHasquestion() {
            return hasquestion;
        }

        public void setHasquestion(boolean hasquestion) {
            this.hasquestion = hasquestion;
        }

        public int getEleAccountId() {
            return eleAccountId;
        }

        public void setEleAccountId(int eleAccountId) {
            this.eleAccountId = eleAccountId;
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }

        public static class QuertionBean {
            /**
             * questionId : 271
             * fault_type : 1
             * description : [test, 架空线]
             * patrolRecordId : 1262
             * questionDescriptionList : [{"questionDescriptionId":172,"imageList":[{"imagePath":"questionDescription\\1529833519904.png","questionDescriptionImageId":94}],"content":"北机路口"}]
             * type : 3
             * questionIndex : Q3001262
             * companyId : 23
             * dealTime : 2018-06-24 17:45:19
             * createTime : 2018-06-24 17:45:19
             * questionType : 3
             * questionScheduleList : [{"questionScheduleId":170,"imageList":[],"content":"工程师康大师正在处理"}]
             * status : 0
             */

            private int questionId;
            private int fault_type;
            private String description;
            private int patrolRecordId;
            private int type;
            private String questionIndex;
            private int companyId;
            private String dealTime;
            private String createTime;
            private int questionType;
            private int status;
            private List<QuestionDescriptionListBean> questionDescriptionList;
            private List<QuestionScheduleListBean> questionScheduleList;

            public int getQuestionId() {
                return questionId;
            }

            public void setQuestionId(int questionId) {
                this.questionId = questionId;
            }

            public int getFault_type() {
                return fault_type;
            }

            public void setFault_type(int fault_type) {
                this.fault_type = fault_type;
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

            public int getQuestionType() {
                return questionType;
            }

            public void setQuestionType(int questionType) {
                this.questionType = questionType;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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
                 * questionDescriptionId : 172
                 * imageList : [{"imagePath":"questionDescription\\1529833519904.png","questionDescriptionImageId":94}]
                 * content : 北机路口
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
                     * imagePath : questionDescription\1529833519904.png
                     * questionDescriptionImageId : 94
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
                 * questionScheduleId : 170
                 * imageList : []
                 * content : 工程师康大师正在处理
                 */

                private int questionScheduleId;
                private String content;
                private List<ImageListBean> imageList;
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
                     * imagePath : questionDescription\1529833519904.png
                     * questionDescriptionImageId : 94
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
        }
}
