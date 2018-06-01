package cn.tties.maint.bean;

import java.util.List;

/**
 * Created by li on 2018/5/29
 * description：
 * author：guojlli
 */

public class bean {
    /**
     * errorCode : 0
     * errorMessage : 成功
     * result : {"threeCompanyEquipmentList":[],"twoCompanyEquipment":{"companyEquipmentId":940,"createTime":"2018-05-25 15:54:51","eleAccountId":54,"equipmentId":2,"flag":0,"name":"架空线","pid":-1},"twoCompanyEquipmentItem":[{"equipmentItem":{"equipmentId":2,"equipmentItemId":1,"inputType":0,"itemName":"变电站名称","itemOrder":1}},{"equipmentItem":{"equipmentId":2,"equipmentItemId":2,"inputType":0,"itemName":"线路名称","itemOrder":2}}]}
     */

    private int errorCode;
    private String errorMessage;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * threeCompanyEquipmentList : []
         * twoCompanyEquipment : {"companyEquipmentId":940,"createTime":"2018-05-25 15:54:51","eleAccountId":54,"equipmentId":2,"flag":0,"name":"架空线","pid":-1}
         * twoCompanyEquipmentItem : [{"equipmentItem":{"equipmentId":2,"equipmentItemId":1,"inputType":0,"itemName":"变电站名称","itemOrder":1}},{"equipmentItem":{"equipmentId":2,"equipmentItemId":2,"inputType":0,"itemName":"线路名称","itemOrder":2}}]
         */

        private TwoCompanyEquipmentBean twoCompanyEquipment;
        private List<?> threeCompanyEquipmentList;
        private List<TwoCompanyEquipmentItemBean> twoCompanyEquipmentItem;

        public TwoCompanyEquipmentBean getTwoCompanyEquipment() {
            return twoCompanyEquipment;
        }

        public void setTwoCompanyEquipment(TwoCompanyEquipmentBean twoCompanyEquipment) {
            this.twoCompanyEquipment = twoCompanyEquipment;
        }

        public List<?> getThreeCompanyEquipmentList() {
            return threeCompanyEquipmentList;
        }

        public void setThreeCompanyEquipmentList(List<?> threeCompanyEquipmentList) {
            this.threeCompanyEquipmentList = threeCompanyEquipmentList;
        }

        public List<TwoCompanyEquipmentItemBean> getTwoCompanyEquipmentItem() {
            return twoCompanyEquipmentItem;
        }

        public void setTwoCompanyEquipmentItem(List<TwoCompanyEquipmentItemBean> twoCompanyEquipmentItem) {
            this.twoCompanyEquipmentItem = twoCompanyEquipmentItem;
        }

        public static class TwoCompanyEquipmentBean {
            /**
             * companyEquipmentId : 940
             * createTime : 2018-05-25 15:54:51
             * eleAccountId : 54
             * equipmentId : 2
             * flag : 0
             * name : 架空线
             * pid : -1
             */

            private int companyEquipmentId;
            private String createTime;
            private int eleAccountId;
            private int equipmentId;
            private int flag;
            private String name;
            private int pid;

            public int getCompanyEquipmentId() {
                return companyEquipmentId;
            }

            public void setCompanyEquipmentId(int companyEquipmentId) {
                this.companyEquipmentId = companyEquipmentId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getEleAccountId() {
                return eleAccountId;
            }

            public void setEleAccountId(int eleAccountId) {
                this.eleAccountId = eleAccountId;
            }

            public int getEquipmentId() {
                return equipmentId;
            }

            public void setEquipmentId(int equipmentId) {
                this.equipmentId = equipmentId;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
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
        }

        public static class TwoCompanyEquipmentItemBean {
            /**
             * equipmentItem : {"equipmentId":2,"equipmentItemId":1,"inputType":0,"itemName":"变电站名称","itemOrder":1}
             */

            private EquipmentItemBean equipmentItem;

            public EquipmentItemBean getEquipmentItem() {
                return equipmentItem;
            }

            public void setEquipmentItem(EquipmentItemBean equipmentItem) {
                this.equipmentItem = equipmentItem;
            }

            public static class EquipmentItemBean {
                /**
                 * equipmentId : 2
                 * equipmentItemId : 1
                 * inputType : 0
                 * itemName : 变电站名称
                 * itemOrder : 1
                 */

                private int equipmentId;
                private int equipmentItemId;
                private int inputType;
                private String itemName;
                private int itemOrder;

                public int getEquipmentId() {
                    return equipmentId;
                }

                public void setEquipmentId(int equipmentId) {
                    this.equipmentId = equipmentId;
                }

                public int getEquipmentItemId() {
                    return equipmentItemId;
                }

                public void setEquipmentItemId(int equipmentItemId) {
                    this.equipmentItemId = equipmentItemId;
                }

                public int getInputType() {
                    return inputType;
                }

                public void setInputType(int inputType) {
                    this.inputType = inputType;
                }

                public String getItemName() {
                    return itemName;
                }

                public void setItemName(String itemName) {
                    this.itemName = itemName;
                }

                public int getItemOrder() {
                    return itemOrder;
                }

                public void setItemOrder(int itemOrder) {
                    this.itemOrder = itemOrder;
                }
            }
        }
    }
}
