package cn.tties.maint.bean;


import java.io.Serializable;
import java.util.List;

import cn.tties.maint.enums.RoleType;

/**
 * 登录
 * author chensi
 */
public class UserInfoBean implements Serializable {

    private Integer staffId;
    private String staffName;
    private String staffNo;
    private String staffTel;
    private String loginPwd;
    private Integer status;
    private String createTime;
    private Integer roleId;
    private List<String> menuList;

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
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

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public Integer getStatus() {
        return status;
    }

    public RoleType getRoleType() {
        return RoleType.getTpye(roleId);
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<String> menuList) {
        this.menuList = menuList;
    }
}
