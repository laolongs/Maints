package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 创建企业参数
 * author lizhen
 */
public class SaveOrUpdateMeterConfigParams extends ClinetRequestParams {

    public static final String INTERFACE = "meterConfigApiAction.do";

    private Integer meterId;

    private String meterName;

    private Integer companyId;

    private Integer eleAccountId;

    private Integer ammeterNumber;

    private String terminalAddress;

    private String gprsCardNo;

    private Integer tv;

    private Integer ta;

    private Integer ratedCurrent;

    private Integer ratedVoltage;

    private Integer ratedPower;

    private Integer connMode;

    private String areaCode;
    private Integer equipmentType;
    private Integer lineInType;

    private Long point;

    private Integer commProtocolType;

    private Integer transformerId;

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Integer getCommProtocolType() {
        return commProtocolType;
    }

    public void setCommProtocolType(Integer commProtocolType) {
        this.commProtocolType = commProtocolType;
    }
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getMeterId() {
        return meterId;
    }

    public void setMeterId(Integer meterId) {
        this.meterId = meterId;
    }

    public String getMeterName() {
        return meterName;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

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

    public Integer getAmmeterNumber() {
        return ammeterNumber;
    }

    public void setAmmeterNumber(Integer ammeterNumber) {
        this.ammeterNumber = ammeterNumber;
    }

    public String getTerminalAddress() {
        return terminalAddress;
    }

    public void setTerminalAddress(String terminalAddress) {
        this.terminalAddress = terminalAddress;
    }

    public String getGprsCardNo() {
        return gprsCardNo;
    }

    public void setGprsCardNo(String gprsCardNo) {
        this.gprsCardNo = gprsCardNo;
    }

    public Integer getTv() {
        return tv;
    }

    public void setTv(Integer tv) {
        this.tv = tv;
    }

    public Integer getTa() {
        return ta;
    }

    public void setTa(Integer ta) {
        this.ta = ta;
    }

    public Integer getRatedCurrent() {
        return ratedCurrent;
    }

    public void setRatedCurrent(Integer ratedCurrent) {
        this.ratedCurrent = ratedCurrent;
    }

    public Integer getRatedVoltage() {
        return ratedVoltage;
    }

    public void setRatedVoltage(Integer ratedVoltage) {
        this.ratedVoltage = ratedVoltage;
    }

    public Integer getRatedPower() {
        return ratedPower;
    }

    public void setRatedPower(Integer ratedPower) {
        this.ratedPower = ratedPower;
    }

    public Integer getConnMode() {
        return connMode;
    }

    public void setConnMode(Integer connMode) {
        this.connMode = connMode;
    }

    public Integer getLineInType() {
        return lineInType;
    }

    public void setLineInType(Integer lineInType) {
        this.lineInType = lineInType;
    }

    public Integer getTransformerId() {
        return transformerId;
    }

    public void setTransformerId(Integer transformerId) {
        this.transformerId = transformerId;
    }
}
