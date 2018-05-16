package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 */

public class UpdateTotalElectricityMeterParams extends ClinetRequestParams {
    
    public static final String INTERFACE = "updateTotalElectricityMeter.do";


    private Integer companyId;

    private Integer eleAccountId;

    private String meterIds;

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

    public String getMeterIds() {
        return meterIds;
    }

    public void setMeterIds(String meterIds) {
        this.meterIds = meterIds;
    }
}
