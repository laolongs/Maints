package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 查询问题列表请求参数.
 * Created by fultrust on 2018/1/7.
 */

public class QueryQuertionParams  extends ClinetRequestParams {

    public static final String INTERFACE = "queryQuertionAction.do";

    private int staffId;

    private Integer companyId;

    private Integer status;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
}
