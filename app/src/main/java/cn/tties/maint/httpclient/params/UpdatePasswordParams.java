package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 */

public class UpdatePasswordParams  extends ClinetRequestParams {
    public static final String INTERFACE = "updatePassword.do";
    private  Integer staffId;
    private    String oldPwd;
    private   String  newPwd;

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }
}
