package cn.tties.maint.httpclient.result;

import cn.tties.maint.bean.CommonListViewInterface;

/**
 * Created by fultrust on 2018/1/7.
 */

public class ContractResult implements CommonListViewInterface {

    private Integer contractId;

    private Integer companyId;

    private String contractName;

    private Integer contractType;

    private String contractPath;

    private Integer contractYear;

    private String createTime;

    private boolean isChecked;

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public void setContractPath(String contractPath) {
        this.contractPath = contractPath;
    }

    public void setContractYear(Integer contractYear) {
        this.contractYear = contractYear;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getContractId() {
        return contractId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getContractName() {
        return contractName;
    }

    public Integer getContractType() {
        return contractType;
    }

    public String getContractPath() {
        return contractPath;
    }

    public Integer getContractYear() {
        return contractYear;
    }

    public String getCreateTime() {
        return createTime;
    }

    @Override
    public Integer getItemId() {
        return this.contractId;
    }

    @Override
    public String getItemName() {
        return this.contractName;
    }

    @Override
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override
    public void setChecked(boolean checkedItem) {
        this.isChecked = checkedItem;
    }
}
