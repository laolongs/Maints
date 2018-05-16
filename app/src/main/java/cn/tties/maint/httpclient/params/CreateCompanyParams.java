package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 创建企业参数
 * author lizhen
 */
public class CreateCompanyParams extends ClinetRequestParams {

    public static final String INTERFACE = "createCompany.do";

    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 企业地址
     */
    private String companyAddr;
    /**
     * 短信接收电话
     */
    private String smsTel;
    /**
     * 代理人
     */
    private String agentName;
    /**
     * 变压器总容量
     */
    private int transformerCapacity;
    /**
     * 变压器个数
     */
    private int transformerCount;
    /**
     * 开始时间
     */
    private int startDate;
    /**
     * 结束时间
     */
    private int endDate;
    /**
     * 月服务费
     */
    private Double monthMoney;
    /**
     * 商务负责人
     */
    private String businessName;
    /**
     * 商务联系电话
     */
    private String businessTel;
    /**
     * 财务负责人
     */
    private String financeName;
    /**
     * 财务联系电话
     */
    private String financeTel;
    /**
     * 技术负责人
     */
    private String techName;
    /**
     * 技术联系电话
     */
    private String techTel;
    /**
     * 运维负责人id
     */
    private int maintStaffId;
    /**
     *（默认0）
     */
    private int status;
    /**
     * 创建企业员工id
     */
    private int creatorId;
    /**
     * 户号（逗号分隔）
     */
    private String eleAccounts;
    /**
     * 户号容量（逗号分隔）
     */
    private String eleVolumes;

    private String areaId;

    private int industryId;

    private String companyShortName;

    public CreateCompanyParams() {
    }

    public static String getINTERFACE() {
        return INTERFACE;
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
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public int getTransformerCapacity() {
        return transformerCapacity;
    }

    public void setTransformerCapacity(int transformerCapacity) {
        this.transformerCapacity = transformerCapacity;
    }

    public int getTransformerCount() {
        return transformerCount;
    }

    public void setTransformerCount(int transformerCount) {
        this.transformerCount = transformerCount;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
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

    public int getMaintStaffId() {
        return maintStaffId;
    }

    public void setMaintStaffId(int maintStaffId) {
        this.maintStaffId = maintStaffId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getEleAccounts() {
        return eleAccounts;
    }

    public void setEleAccounts(String eleAccounts) {
        this.eleAccounts = eleAccounts;
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

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getEleVolumes() {
        return eleVolumes;
    }

    public void setEleVolumes(String eleVolumes) {
        this.eleVolumes = eleVolumes;
    }
}
