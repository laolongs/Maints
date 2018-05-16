package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/7.
 */

public class UploadContractParams extends ClinetRequestParams {

    public static final String INTERFACE ="uploadContract.do";

    private Integer companyId;

    private String contractName;

    private Integer contractType;

    private Integer contractYear;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Integer getContractYear() {
        return contractYear;
    }

    public void setContractYear(Integer contractYear) {
        this.contractYear = contractYear;
    }
}
