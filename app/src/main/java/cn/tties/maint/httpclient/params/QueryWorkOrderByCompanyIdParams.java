package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/11.
 */


public class QueryWorkOrderByCompanyIdParams extends ClinetRequestParams {
    public static final String INTERFACE = "QueryWorkOrderByCompanyIdParams.do";

    private Integer companyId;

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCompanyId() {
        return companyId;
    }
}
