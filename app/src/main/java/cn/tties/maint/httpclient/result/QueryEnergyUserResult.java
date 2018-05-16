package cn.tties.maint.httpclient.result;

import cn.tties.maint.bean.CommonListViewInterface;

/**
 * Created by fultrust on 2018/1/28.
 */

public class QueryEnergyUserResult implements CommonListViewInterface {
    private Integer userId;
    private String userName;
    private String realUserName;
    private String userPwd;
    private Long enereyUserId;
    private Integer companyId;

    private boolean isChecked;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRealUserName(String realUserName) {
        this.realUserName = realUserName;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public void setEnereyUserId(Long enereyUserId) {
        this.enereyUserId = enereyUserId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getRealUserName() {
        return realUserName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public Long getEnereyUserId() {
        return enereyUserId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getCreateTime() {
        return createTime;
    }

    private String  createTime;

    @Override
    public Integer getItemId() {
        return this.userId;
    }

    @Override
    public String getItemName() {
        return this.userName;
    }

    @Override
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override
    public void setChecked(boolean checkedItem) {
        this.isChecked = checkedItem;
    }
}
