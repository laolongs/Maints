package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/6.
 */

public class UpdateInfoCompanyParams extends ClinetRequestParams {

    public static final String INTERFACE = "updateInfoCompany.do";

    /**
     * 企业
     */
    private Integer companyId;

    private String companyName;

    private String companyShortName;

    private String companyAddr;

    private String agentName;

    private String smsTel;

    private Integer transformerCapacity;

    private Integer transformerCount;

    private String eleAccounts;

    private int maintStaffId;

    private String areaId;

    private int industryId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEleAccounts() {
        return eleAccounts;
    }

    public void setEleAccounts(String eleAccounts) {
        this.eleAccounts = eleAccounts;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getSmsTel() {
        return smsTel;
    }

    public void setSmsTel(String smsTel) {
        this.smsTel = smsTel;
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

    public int getMaintStaffId() {
        return maintStaffId;
    }

    public void setMaintStaffId(int maintStaffId) {
        this.maintStaffId = maintStaffId;
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

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }
}
