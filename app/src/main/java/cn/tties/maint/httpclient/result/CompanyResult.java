package cn.tties.maint.httpclient.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fultrust on 2018/1/5.
 */

public class CompanyResult implements Serializable{

    private Integer companyId;

    private String companyName;

    private String companyShortName;

    private String companyAddr;

    private String smsTel;

    private String agentName;

    private Integer transformerCapacity;

    private Integer transformerCount;

    private Integer startDate;

    private Integer endDate;

    private Double monthMoney;

    private String businessName;

    private String businessTel;

    private String financeName;

    private String financeTel;

    private String techName;

    private String techTel;

    private Integer maintStaffId;

    private Integer status;

    private Integer creatorId;

    private String createTime;

    private String areaId;

    private Integer industryId;

    private Integer rateId;

    private Integer energyCompanyId;

    private Double powerFactor;

    private List<EleAccountResult> eleAccountList;

    public Integer getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getSmsTel() {
        return smsTel;
    }

    public void setSmsTel(String smsTel) {
        this.smsTel = smsTel;
    }

    public String getAgentName() {
        return agentName ;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getTransformerCapacity() {
        return transformerCapacity;
    }

    public void setTransformerCapacity(Integer transformerCapacity) {
        this.transformerCapacity = transformerCapacity;
    }

    public Integer getTransformerCount() {
        return transformerCount;
    }

    public void setTransformerCount(Integer transformerCount) {
        this.transformerCount = transformerCount;
    }

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
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

    public Integer getMaintStaffId() {
        return maintStaffId;
    }

    public void setMaintStaffId(Integer maintStaffId) {
        this.maintStaffId = maintStaffId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<EleAccountResult> getEleAccountList() {
        return eleAccountList;
    }

    public void setEleAccountList(List<EleAccountResult> eleAccountList) {
        this.eleAccountList = eleAccountList;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public Integer getRateId() {
        return rateId;
    }

    public void setRateId(Integer rateId) {
        this.rateId = rateId;
    }

    public Integer getEnergyCompanyId() {
        return energyCompanyId;
    }

    public void setEnergyCompanyId(Integer energyCompanyId) {
        this.energyCompanyId = energyCompanyId;
    }

    public Double getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(Double powerFactor) {
        this.powerFactor = powerFactor;
    }
}
