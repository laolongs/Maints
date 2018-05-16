package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 添加员工.
 */

public class AddStaffParams extends ClinetRequestParams {
    public static final String INTERFACE = "addStaff.do";

    private String staffTel;
    private Integer staffRoleId;
    private String staffName;
    private String staffPwd;
    private String staffNo;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getStaffTel() {
        return staffTel;
    }

    public void setStaffTel(String staffTel) {
        this.staffTel = staffTel;
    }

    public void setStaffRoleId(Integer staffRoleId) {
        this.staffRoleId = staffRoleId;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setStaffPwd(String staffPwd) {
        this.staffPwd = staffPwd;
    }

    public Integer getStaffRoleId() {
        return staffRoleId;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStaffPwd() {
        return staffPwd;
    }
}
