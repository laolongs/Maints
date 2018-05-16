package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/7.
 */

public class AddEnereyCompanyParams extends ClinetRequestParams {
private  Integer companyId;
private int nature;
private double powerFactor;
private  int voltageLevel;

    public void setNature(int nature) {
        this.nature = nature;
    }

    public void setPowerFactor(double powerFactor) {
        this.powerFactor = powerFactor;
    }

    public void setVoltageLevel(int voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    public int getNature() {
        return nature;
    }

    public double getPowerFactor() {
        return powerFactor;
    }

    public int getVoltageLevel() {
        return voltageLevel;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public static final String INTERFACE ="createEnereyCompany.do";

}
