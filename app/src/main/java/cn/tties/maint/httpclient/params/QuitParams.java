package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 离职交接参数
 * author Lizhen
 */
public class QuitParams extends ClinetRequestParams {

    public static final String INTERFACE = "updateCompanyOnQuit.do";

    /**
     * 离职人员id
     */
    private int oldStaffId;
    /**
     * 新员工id
     */
    private int newStaffId;
    /**
     * 离职人角色id
     */
    private int roldId;

    public int getOldStaffId() {
        return oldStaffId;
    }

    public void setOldStaffId(int oldStaffId) {
        this.oldStaffId = oldStaffId;
    }

    public int getNewStaffId() {
        return newStaffId;
    }

    public void setNewStaffId(int newStaffId) {
        this.newStaffId = newStaffId;
    }

    public int getRoldId() {
        return roldId;
    }

    public void setRoldId(int roldId) {
        this.roldId = roldId;
    }
}
