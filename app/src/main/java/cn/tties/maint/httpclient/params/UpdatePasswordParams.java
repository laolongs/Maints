package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 */

public class UpdatePasswordParams  extends ClinetRequestParams {
    public static final String INTERFACE = "updateMaintPassword.do";
    private  Integer maintStaffId;
    private    String oldPwd;
    private   String  newPwd;

    public Integer getMaintStaffId() {
        return maintStaffId;
    }

    public void setMaintStaffId(Integer maintStaffId) {
        this.maintStaffId = maintStaffId;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
