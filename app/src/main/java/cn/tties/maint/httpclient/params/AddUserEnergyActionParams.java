package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 创建用户.
 */

public class AddUserEnergyActionParams extends ClinetRequestParams {

    public static final String INTERFACE ="addUserEnergyAction.do";

    private Integer userId;
    private Integer companyId;
    private String realUserName;
    private String userPwd;

    public Integer getCompanyId() {
        return companyId;
    }

    public String getRealUserName() {
        return realUserName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void setRealUserName(String realUserName) {
        this.realUserName = realUserName;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
