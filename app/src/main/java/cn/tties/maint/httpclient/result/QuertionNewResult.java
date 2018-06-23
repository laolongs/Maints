package cn.tties.maint.httpclient.result;

import java.util.List;

/**
 * Created by fultrust on 2018/1/7.
 */

public class QuertionNewResult {

    /**
     * errorCode : 0
     * errorMessage : 成功
     * result : [{"companyId":7,"companyName":"测试企业3","eleAccountList":[],"quertionList":[{"companyId":7,"createTime":"2018-01-16 16:38:56","dealTime":"2018-01-16 16:38:56","description":"[低压开关柜柜体, 低压开关柜, 低压]","patrolRecordId":184,"questionDescriptionList":[],"questionId":104,"questionIndex":"Q1000184","questionScheduleList":[],"status":0,"type":1},{"companyId":7,"createTime":"2018-01-16 16:48:09","dealTime":"2018-01-16 16:48:09","description":"[附属环境]","patrolRecordId":124,"questionDescriptionList":[],"questionId":107,"questionIndex":"Q6000124","questionScheduleList":[],"status":0,"type":6},{"companyId":7,"createTime":"2018-01-16 16:52:09","dealTime":"2018-01-16 16:52:09","description":"[清洁卫生]","patrolRecordId":210,"questionDescriptionList":[],"questionId":108,"questionIndex":"Q7000210","questionScheduleList":[],"status":0,"type":7},{"companyId":7,"createTime":"2018-01-16 16:52:21","dealTime":"2018-01-16 16:52:21","description":"[附属环境]","patrolRecordId":125,"questionDescriptionList":[],"questionId":109,"questionIndex":"Q6000125","questionScheduleList":[],"status":0,"type":6},{"companyId":7,"createTime":"2018-01-16 16:52:39","dealTime":"2018-01-16 16:52:39","description":"[低压开关柜柜体, 低压开关柜, 低压]","patrolRecordId":225,"questionDescriptionList":[],"questionId":110,"questionIndex":"Q1000225","questionScheduleList":[],"status":0,"type":1},{"companyId":7,"createTime":"2018-01-16 17:05:46","dealTime":"2018-01-16 17:05:46","description":"[低压开关柜柜体, 低压开关柜, 低压]","patrolRecordId":236,"questionDescriptionList":[],"questionId":111,"questionIndex":"Q1000236","questionScheduleList":[],"questionType":1,"status":0,"type":1}]}]
     */

    private int errorCode;
    private String errorMessage;
    private List<ResultBean> result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * companyId : 7
         * companyName : 测试企业3
         * eleAccountList : []
         * quertionList : [{"companyId":7,"createTime":"2018-01-16 16:38:56","dealTime":"2018-01-16 16:38:56","description":"[低压开关柜柜体, 低压开关柜, 低压]","patrolRecordId":184,"questionDescriptionList":[],"questionId":104,"questionIndex":"Q1000184","questionScheduleList":[],"status":0,"type":1},{"companyId":7,"createTime":"2018-01-16 16:48:09","dealTime":"2018-01-16 16:48:09","description":"[附属环境]","patrolRecordId":124,"questionDescriptionList":[],"questionId":107,"questionIndex":"Q6000124","questionScheduleList":[],"status":0,"type":6},{"companyId":7,"createTime":"2018-01-16 16:52:09","dealTime":"2018-01-16 16:52:09","description":"[清洁卫生]","patrolRecordId":210,"questionDescriptionList":[],"questionId":108,"questionIndex":"Q7000210","questionScheduleList":[],"status":0,"type":7},{"companyId":7,"createTime":"2018-01-16 16:52:21","dealTime":"2018-01-16 16:52:21","description":"[附属环境]","patrolRecordId":125,"questionDescriptionList":[],"questionId":109,"questionIndex":"Q6000125","questionScheduleList":[],"status":0,"type":6},{"companyId":7,"createTime":"2018-01-16 16:52:39","dealTime":"2018-01-16 16:52:39","description":"[低压开关柜柜体, 低压开关柜, 低压]","patrolRecordId":225,"questionDescriptionList":[],"questionId":110,"questionIndex":"Q1000225","questionScheduleList":[],"status":0,"type":1},{"companyId":7,"createTime":"2018-01-16 17:05:46","dealTime":"2018-01-16 17:05:46","description":"[低压开关柜柜体, 低压开关柜, 低压]","patrolRecordId":236,"questionDescriptionList":[],"questionId":111,"questionIndex":"Q1000236","questionScheduleList":[],"questionType":1,"status":0,"type":1}]
         */

        private int companyId;
        private String companyName;
        private String companyShortName;
        private List<?> eleAccountList;
        private List<QuertionListBean> quertionList;

        public String getCompanyShortName() {
            return companyShortName;
        }

        public void setCompanyShortName(String companyShortName) {
            this.companyShortName = companyShortName;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public List<?> getEleAccountList() {
            return eleAccountList;
        }

        public void setEleAccountList(List<?> eleAccountList) {
            this.eleAccountList = eleAccountList;
        }

        public List<QuertionListBean> getQuertionList() {
            return quertionList;
        }

        public void setQuertionList(List<QuertionListBean> quertionList) {
            this.quertionList = quertionList;
        }

        public static class QuertionListBean {
            /**
             * companyId : 7
             * createTime : 2018-01-16 16:38:56
             * dealTime : 2018-01-16 16:38:56
             * description : [低压开关柜柜体, 低压开关柜, 低压]
             * patrolRecordId : 184
             * questionDescriptionList : []
             * questionId : 104
             * questionIndex : Q1000184
             * questionScheduleList : []
             * status : 0
             * type : 1
             * questionType : 1
             */
            private String companyName;
            private int companyId;
            private String createTime;
            private String dealTime;
            private String description;
            private int patrolRecordId;
            private int questionId;
            private String questionIndex;
            private int status;
            private int type;
            private int fault_type;
            private int questionType;
            private String companyShortName;
            private  int patrolItemId;
            private List<DescriptionResult> questionDescriptionList;
            private List<QuestionNewScheduleResult> questionScheduleList;

            public int getPatrolItemId() {
                return patrolItemId;
            }

            public void setPatrolItemId(int patrolItemId) {
                this.patrolItemId = patrolItemId;
            }

            public String getCompanyShortName() {
                return companyShortName;
            }

            public void setCompanyShortName(String companyShortName) {
                this.companyShortName = companyShortName;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }
            public List<DescriptionResult> getQuestionDescriptionList() {
                return questionDescriptionList;
            }

            public void setQuestionDescriptionList(List<DescriptionResult> questionDescriptionList) {
                this.questionDescriptionList = questionDescriptionList;
            }

            public List<QuestionNewScheduleResult> getQuestionScheduleList() {
                return questionScheduleList;
            }

            public void setQuestionScheduleList(List<QuestionNewScheduleResult> questionScheduleList) {
                this.questionScheduleList = questionScheduleList;
            }

            public int getFault_type() {
                return fault_type;
            }

            public void setFault_type(int fault_type) {
                this.fault_type = fault_type;
            }

            public int getCompanyId() {
                return companyId;
            }

            public void setCompanyId(int companyId) {
                this.companyId = companyId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDealTime() {
                return dealTime;
            }

            public void setDealTime(String dealTime) {
                this.dealTime = dealTime;
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

            public int getQuestionId() {
                return questionId;
            }

            public void setQuestionId(int questionId) {
                this.questionId = questionId;
            }

            public String getQuestionIndex() {
                return questionIndex;
            }

            public void setQuestionIndex(String questionIndex) {
                this.questionIndex = questionIndex;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getQuestionType() {
                return questionType;
            }

            public void setQuestionType(int questionType) {
                this.questionType = questionType;
            }



        }
    }
}
