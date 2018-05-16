package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 根据企业id查询户号参数
 * author lizhen
 */
public class QueryEleAccountByCompanyIdParams extends ClinetRequestParams {

    public static final String INTERFACE = "selectListEleAccounts.do";

    private int companyId;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
