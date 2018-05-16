package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 查询巡视工单
 * author chensi
 */
public class SelectPatrolOrderParams extends ClinetRequestParams {

    public static final String INTERFACE = "selectPatrolOrder.do";

    private int staffId;

    private int companyId;

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
