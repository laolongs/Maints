package cn.tties.maint.bean;


import java.io.Serializable;

/**
 * 登录
 * author chensi
 */
public class UserInfoBean implements Serializable {


            /**
             * maintStaffName : 托勒密
             * districtId : 63
             * loginPwd : $shiro1$SHA-256$500000$Tp/FmsT9S1m4JaGh9CfpJQ==$58NGTcevKqHlt4QLPjS85g6ERI+YA6+tald4UfYtH90=
             * createTime : 2018-06-26 10:42:18
             * maintStaffId : 161
             * maintStaffTel : 13701125154
             * status : 0
             */

            private String maintStaffName;
            private int districtId;
            private String loginPwd;
            private String createTime;
            private int maintStaffId;
            private String maintStaffTel;
            private int status;

            public String getMaintStaffName() {
                return maintStaffName;
            }

            public void setMaintStaffName(String maintStaffName) {
                this.maintStaffName = maintStaffName;
            }

            public int getDistrictId() {
                return districtId;
            }

            public void setDistrictId(int districtId) {
                this.districtId = districtId;
            }

            public String getLoginPwd() {
                return loginPwd;
            }

            public void setLoginPwd(String loginPwd) {
                this.loginPwd = loginPwd;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getMaintStaffId() {
                return maintStaffId;
            }

            public void setMaintStaffId(int maintStaffId) {
                this.maintStaffId = maintStaffId;
            }

            public String getMaintStaffTel() {
                return maintStaffTel;
            }

            public void setMaintStaffTel(String maintStaffTel) {
                this.maintStaffTel = maintStaffTel;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }


}
