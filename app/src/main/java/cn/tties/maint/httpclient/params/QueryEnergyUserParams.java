package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/29.
 */

public class QueryEnergyUserParams extends ClinetRequestParams {
    private  Integer companyId;

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }
    public static final String INTERFACE ="QueryEnergyUserList.do";
}
