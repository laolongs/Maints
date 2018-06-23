package cn.tties.maint.httpclient.result;

import java.util.List;

/**
 * Created by li on 2018/6/21
 * description：美化安规巡视项列表
 * author：guojlli
 */

public class PrettiftResult {

    /**
     * result : [{"flag":0,"itemOrder":1,"faultType":1,"faultHarm":"缺陷危害","prettifyDustType":1,"title":"标题","prettifyDustItemId":1,"dealDesc":"处理描述"}]
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
         * flag : 0
         * itemOrder : 1
         * faultType : 1
         * faultHarm : 缺陷危害
         * prettifyDustType : 1
         * title : 标题
         * prettifyDustItemId : 1
         * dealDesc : 处理描述
         */

        private int flag;
        private int itemOrder;
        private int faultType;
        private String faultHarm;
        private int prettifyDustType;
        private String title;
        private int prettifyDustItemId;
        private String dealDesc;

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getItemOrder() {
            return itemOrder;
        }

        public void setItemOrder(int itemOrder) {
            this.itemOrder = itemOrder;
        }

        public int getFaultType() {
            return faultType;
        }

        public void setFaultType(int faultType) {
            this.faultType = faultType;
        }

        public String getFaultHarm() {
            return faultHarm;
        }

        public void setFaultHarm(String faultHarm) {
            this.faultHarm = faultHarm;
        }

        public int getPrettifyDustType() {
            return prettifyDustType;
        }

        public void setPrettifyDustType(int prettifyDustType) {
            this.prettifyDustType = prettifyDustType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPrettifyDustItemId() {
            return prettifyDustItemId;
        }

        public void setPrettifyDustItemId(int prettifyDustItemId) {
            this.prettifyDustItemId = prettifyDustItemId;
        }

        public String getDealDesc() {
            return dealDesc;
        }

        public void setDealDesc(String dealDesc) {
            this.dealDesc = dealDesc;
        }
    }
}
