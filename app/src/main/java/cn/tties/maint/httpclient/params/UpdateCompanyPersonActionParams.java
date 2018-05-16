package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 */

public class UpdateCompanyPersonActionParams extends ClinetRequestParams {

    public static final String INTERFACE = "updateCompanyPersonAction.do";

    private Integer companyId;
    private  String businessName;
    private  String businessTel;
    private  String financeName;
    private  String financeTel;
    private  String techName;
    private  String techTel;

    public Integer getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessTel() {
        return businessTel;
    }

    public void setBusinessTel(String businessTel) {
        this.businessTel = businessTel;
    }

    public String getFinanceName() {
        return financeName;
    }

    public void setFinanceName(String financeName) {
        this.financeName = financeName;
    }

    public String getFinanceTel() {
        return financeTel;
    }

    public void setFinanceTel(String financeTel) {
        this.financeTel = financeTel;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public String getTechTel() {
        return techTel;
    }

    public void setTechTel(String techTel) {
        this.techTel = techTel;
    }
}
