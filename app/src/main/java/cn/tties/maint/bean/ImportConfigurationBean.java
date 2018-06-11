package cn.tties.maint.bean;

import java.util.List;

/**
 * Created by li on 2018/5/28
 * description：
 * author：guojlli
 */

public class ImportConfigurationBean {

    /**
     * result : [{"flag":0,"createTime":"Wed Jan 17 16:54:08 CST 2018","name":"高压开关柜1","pid":-1,"infoList":[],"companyEquipmentId":357,"equipmentId":5,"eleAccountId":54,"roomId":40},{"flag":0,"createTime":"Wed Jan 17 16:54:08 CST 2018","name":"高压开关柜2","pid":-1,"infoList":[],"companyEquipmentId":382,"equipmentId":5,"eleAccountId":54},{"flag":0,"createTime":"Wed Jan 17 16:54:08 CST 2018","name":"高压开关柜3","pid":-1,"infoList":[],"companyEquipmentId":421,"equipmentId":5,"eleAccountId":54},{"flag":0,"createTime":"Wed Jan 17 16:54:08 CST 2018","name":"高压开关柜4","pid":-1,"infoList":[],"companyEquipmentId":444,"equipmentId":5,"eleAccountId":54},{"flag":0,"createTime":"Wed Jan 24 19:36:17 CST 2018","name":"高压开关柜","pid":444,"infoList":[],"companyEquipmentId":490,"equipmentId":5,"eleAccountId":54},{"flag":0,"createTime":"Wed Jan 24 19:36:58 CST 2018","name":"高压开关柜1213","pid":-1,"infoList":[],"companyEquipmentId":495,"equipmentId":5,"eleAccountId":54},{"flag":0,"createTime":"Wed Jun 06 16:04:27 CST 2018","name":"高压来来来","pid":-1,"infoList":[],"companyEquipmentId":501,"equipmentId":5,"eleAccountId":54},{"flag":0,"createTime":"Wed Jun 06 16:34:26 CST 2018","name":"我的","pid":-1,"infoList":[],"companyEquipmentId":995,"equipmentId":5,"eleAccountId":54},{"flag":0,"createTime":"Wed Jun 06 17:48:57 CST 2018","name":"偷偷","pid":-1,"infoList":[],"companyEquipmentId":1001,"equipmentId":5,"eleAccountId":54},{"flag":0,"createTime":"Wed Jun 06 17:59:58 CST 2018","name":"高压开关柜","pid":-1,"infoList":[],"companyEquipmentId":1006,"equipmentId":5,"eleAccountId":54},{"flag":0,"createTime":"Wed Jun 06 18:26:05 CST 2018","name":"高压开关柜","pid":-1,"infoList":[],"companyEquipmentId":1023,"equipmentId":5,"eleAccountId":54},{"flag":0,"createTime":"Wed Jun 06 18:26:57 CST 2018","name":"高压开关柜图腾街","pid":-1,"infoList":[],"companyEquipmentId":1027,"equipmentId":5,"eleAccountId":54}]
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
         * createTime : Wed Jan 17 16:54:08 CST 2018
         * name : 高压开关柜1
         * pid : -1
         * infoList : []
         * companyEquipmentId : 357
         * equipmentId : 5
         * eleAccountId : 54
         * roomId : 40
         */

        private int flag;
        private String createTime;
        private String name;
        private int pid;
        private int companyEquipmentId;
        private int equipmentId;
        private int eleAccountId;
        private int roomId;
        private List<?> infoList;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getCompanyEquipmentId() {
            return companyEquipmentId;
        }

        public void setCompanyEquipmentId(int companyEquipmentId) {
            this.companyEquipmentId = companyEquipmentId;
        }

        public int getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(int equipmentId) {
            this.equipmentId = equipmentId;
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

        public List<?> getInfoList() {
            return infoList;
        }

        public void setInfoList(List<?> infoList) {
            this.infoList = infoList;
        }
    }
}
