package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 */

public class CompanyContractPostponeParams extends ClinetRequestParams {

    public static final String INTERFACE = "companyContractPostpone.do";


    private Integer companyId;
    private Integer endDate;
    private Double monthMoney;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getEndDate() {
        return endDate;
    }

    public void setEndDate(Integer endDate) {
        this.endDate = endDate;
    }

    public Double getMonthMoney() {
        return monthMoney;
    }

    public void setMonthMoney(Double monthMoney) {
        this.monthMoney = monthMoney;
    }
}
