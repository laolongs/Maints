package cn.tties.maint.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by li on 2018/5/28
 * description：
 * author：guojlli
 */

public class EquipmentcheckBean  {

        private TwoCompanyEquipmentBean twoCompanyEquipment;
        private List<TwoCompanyEquipmentItemBean> twoCompanyEquipmentItem;
        private List<ThreeCompanyEquipmentListBean> threeCompanyEquipmentList;

        public TwoCompanyEquipmentBean getTwoCompanyEquipment() {
            return twoCompanyEquipment;
        }

        public void setTwoCompanyEquipment(TwoCompanyEquipmentBean twoCompanyEquipment) {
            this.twoCompanyEquipment = twoCompanyEquipment;
        }

        public List<TwoCompanyEquipmentItemBean> getTwoCompanyEquipmentItem() {
            return twoCompanyEquipmentItem;
        }

        public void setTwoCompanyEquipmentItem(List<TwoCompanyEquipmentItemBean> twoCompanyEquipmentItem) {
            this.twoCompanyEquipmentItem = twoCompanyEquipmentItem;
        }

        public List<ThreeCompanyEquipmentListBean> getThreeCompanyEquipmentList() {
            return threeCompanyEquipmentList;
        }

        public void setThreeCompanyEquipmentList(List<ThreeCompanyEquipmentListBean> threeCompanyEquipmentList) {
            this.threeCompanyEquipmentList = threeCompanyEquipmentList;
        }



    public  class TwoCompanyEquipmentItemBean  {
        /**
         * equipmentItem : {"equipmentId":2,"equipmentItemId":1,"inputType":0,"itemName":"变电站名称","itemOrder":1}
         */
        private String equipmentInfo;


        private EquipmentItemBean equipmentItem;

        public String getEquipmentInfo() {
            return equipmentInfo;
        }

        public void setEquipmentInfo(String equipmentInfo) {
            this.equipmentInfo = equipmentInfo;
        }

        public EquipmentItemBean getEquipmentItem() {
            return equipmentItem;
        }

        public void setEquipmentItem(EquipmentItemBean equipmentItem) {
            this.equipmentItem = equipmentItem;
        }

        public  class EquipmentItemBean {
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


        }
    }
        public static class TwoCompanyEquipmentBean {
            /**
             * flag : 0
             * createTime : 2018-02-01 16:56:10
             * name : 2#高压开关柜
             * pid : -1
             * companyEquipmentId : 531
             * eleAccountId : 80
             * equipmentId : 5
             * roomId : 37
             */

            private int flag;
            private String createTime;
            private String name;
            private int pid;
            private int companyEquipmentId;
            private int eleAccountId;
            private int equipmentId;
            private int roomId;

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

            public int getRoomId() {
                return roomId;
            }

            public void setRoomId(int roomId) {
                this.roomId = roomId;
            }


        }

        public static class ThreeCompanyEquipmentListBean {
            private ThreeCompanyEquipmentBean threeCompanyEquipment;
            private List<ThreeCompanyEquipmentItemBean> threeCompanyEquipmentItem;
            private List<FourCompanyEquipmentListBean> fourCompanyEquipmentList;

            public ThreeCompanyEquipmentBean getThreeCompanyEquipment() {
                return threeCompanyEquipment;
            }

            public void setThreeCompanyEquipment(ThreeCompanyEquipmentBean threeCompanyEquipment) {
                this.threeCompanyEquipment = threeCompanyEquipment;
            }

            public List<ThreeCompanyEquipmentItemBean> getThreeCompanyEquipmentItem() {
                return threeCompanyEquipmentItem;
            }

            public void setThreeCompanyEquipmentItem(List<ThreeCompanyEquipmentItemBean> threeCompanyEquipmentItem) {
                this.threeCompanyEquipmentItem = threeCompanyEquipmentItem;
            }

            public List<FourCompanyEquipmentListBean> getFourCompanyEquipmentList() {
                return fourCompanyEquipmentList;
            }

            public void setFourCompanyEquipmentList(List<FourCompanyEquipmentListBean> fourCompanyEquipmentList) {
                this.fourCompanyEquipmentList = fourCompanyEquipmentList;
            }



            public static class ThreeCompanyEquipmentBean  {
                /**
                 * flag : 0
                 * createTime : 2018-02-01 16:56:10
                 * name : 1#高压开关柜柜体
                 * pid : 531
                 * companyEquipmentId : 532
                 * eleAccountId : 80
                 * equipmentId : 6
                 */

                private int flag;
                private String createTime;
                private String name;
                private int pid;
                private int companyEquipmentId;
                private int eleAccountId;
                private int equipmentId;

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


            }

            public static class ThreeCompanyEquipmentItemBean {
                /**
                 * equipmentItem : {"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6}
                 * equipmentInfo :
                 */

                private EquipmentItemBean equipmentItem;
                private String equipmentInfo;

                public EquipmentItemBean getEquipmentItem() {
                    return equipmentItem;
                }

                public void setEquipmentItem(EquipmentItemBean equipmentItem) {
                    this.equipmentItem = equipmentItem;
                }

                public String getEquipmentInfo() {
                    return equipmentInfo;
                }

                public void setEquipmentInfo(String equipmentInfo) {
                    this.equipmentInfo = equipmentInfo;
                }


                public static class EquipmentItemBean {
                    /**
                     * itemName : 型号规格
                     * itemOrder : 1
                     * inputType : 0
                     * equipmentItemId : 7
                     * equipmentId : 6
                     */

                    private String itemName;
                    private int itemOrder;
                    private int inputType;
                    private int equipmentItemId;
                    private int equipmentId;


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

                    public int getInputType() {
                        return inputType;
                    }

                    public void setInputType(int inputType) {
                        this.inputType = inputType;
                    }

                    public int getEquipmentItemId() {
                        return equipmentItemId;
                    }

                    public void setEquipmentItemId(int equipmentItemId) {
                        this.equipmentItemId = equipmentItemId;
                    }

                    public int getEquipmentId() {
                        return equipmentId;
                    }

                    public void setEquipmentId(int equipmentId) {
                        this.equipmentId = equipmentId;
                    }
                }
            }

            public static class FourCompanyEquipmentListBean {


                private FourCompanyEquipmentBean fourCompanyEquipment;
                private List<FourCompanyEquipmentItemBean> fourCompanyEquipmentItem;

                public FourCompanyEquipmentBean getFourCompanyEquipment() {
                    return fourCompanyEquipment;
                }

                public void setFourCompanyEquipment(FourCompanyEquipmentBean fourCompanyEquipment) {
                    this.fourCompanyEquipment = fourCompanyEquipment;
                }

                public List<FourCompanyEquipmentItemBean> getFourCompanyEquipmentItem() {
                    return fourCompanyEquipmentItem;
                }

                public void setFourCompanyEquipmentItem(List<FourCompanyEquipmentItemBean> fourCompanyEquipmentItem) {
                    this.fourCompanyEquipmentItem = fourCompanyEquipmentItem;
                }


                public static class FourCompanyEquipmentBean  {
                    /**
                     * flag : 0
                     * createTime : 2018-02-01 16:56:10
                     * name : 1#高压开关柜柜体
                     * pid : 531
                     * companyEquipmentId : 532
                     * eleAccountId : 80
                     * equipmentId : 6
                     */

                    private int flag;
                    private String createTime;
                    private String name;
                    private int pid;
                    private int companyEquipmentId;
                    private int eleAccountId;
                    private int equipmentId;

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


                }

                public static class FourCompanyEquipmentItemBean {
                    /**
                     * equipmentItem : {"itemName":"型号规格","itemOrder":1,"inputType":0,"equipmentItemId":7,"equipmentId":6}
                     * equipmentInfo :
                     */

                    private EquipmentItemBeanX equipmentItem;
                    private String equipmentInfo;

                    public EquipmentItemBeanX getEquipmentItem() {
                        return equipmentItem;
                    }

                    public void setEquipmentItem(EquipmentItemBeanX equipmentItem) {
                        this.equipmentItem = equipmentItem;
                    }

                    public String getEquipmentInfo() {
                        return equipmentInfo;
                    }

                    public void setEquipmentInfo(String equipmentInfo) {
                        this.equipmentInfo = equipmentInfo;
                    }



                    public static class EquipmentItemBeanX {
                        /**
                         * itemName : 型号规格
                         * itemOrder : 1
                         * inputType : 0
                         * equipmentItemId : 7
                         * equipmentId : 6
                         */

                        private String itemName;
                        private int itemOrder;
                        private int inputType;
                        private int equipmentItemId;
                        private int equipmentId;


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

                        public int getInputType() {
                            return inputType;
                        }

                        public void setInputType(int inputType) {
                            this.inputType = inputType;
                        }

                        public int getEquipmentItemId() {
                            return equipmentItemId;
                        }

                        public void setEquipmentItemId(int equipmentItemId) {
                            this.equipmentItemId = equipmentItemId;
                        }

                        public int getEquipmentId() {
                            return equipmentId;
                        }

                        public void setEquipmentId(int equipmentId) {
                            this.equipmentId = equipmentId;
                        }
                    }
                }
            }
        }

}
