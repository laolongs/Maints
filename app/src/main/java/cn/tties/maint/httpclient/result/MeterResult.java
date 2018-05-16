package cn.tties.maint.httpclient.result;

import cn.tties.maint.bean.CommonListViewInterface;

/**
 * Created by fultrust on 2018/1/6.
 */

public class MeterResult implements CommonListViewInterface {

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

    private Integer ratedPower;

    private Integer ratedVoltage;

    private Long dcPointId;

    private Long dcTerminalId;

    private Long energyMeterId;

    private boolean isChecked;

    private Integer connMode;

    private String areaCode;

    private Integer equipmentType;

    private Long point;

    private Integer commProtocolType;

    private Integer lineInType;

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

    public Integer getRatedPower() {
        return ratedPower;
    }

    public void setRatedPower(Integer ratedPower) {
        this.ratedPower = ratedPower;
    }

    public Integer getRatedVoltage() {
        return ratedVoltage;
    }

    public void setRatedVoltage(Integer ratedVoltage) {
        this.ratedVoltage = ratedVoltage;
    }

    public Long getDcPointId() {
        return dcPointId;
    }

    public void setDcPointId(Long dcPointId) {
        this.dcPointId = dcPointId;
    }

    public Long getDcTerminalId() {
        return dcTerminalId;
    }

    public void setDcTerminalId(Long dcTerminalId) {
        this.dcTerminalId = dcTerminalId;
    }

    public Long getEnergyMeterId() {
        return energyMeterId;
    }

    public void setEnergyMeterId(Long energyMeterId) {
        this.energyMeterId = energyMeterId;
    }

    public Integer getConnMode() {
        return connMode;
    }

    public void setConnMode(Integer connMode) {
        this.connMode = connMode;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public Integer getItemId() {
        return this.meterId;
    }

    @Override
    public String getItemName() {
        return this.meterName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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
