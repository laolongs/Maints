package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 通过户号和设备号查询下级企业设备参数
 * author lizhen
 */
public class QueryAllMbFunctionParams extends ClinetRequestParams {

    public static final String INTERFACE = "queryAllMbFunctionByStaffIdAction.do";

    /**
     * 用户id
     */
    private int staffId;

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
}
