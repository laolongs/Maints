package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 *
 * author chensi
 */
public class QueryTotalElectricityMeterParams extends ClinetRequestParams {

    public static final String INTERFACE = "queryTotalElectricityMeter.do";

    private Integer companyId;

    private Integer eleAccountId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }
}
