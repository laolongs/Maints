package cn.tties.maint.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 *
 */
@Table(name = "rate",
        onCreated = "CREATE INDEX rate_search ON rate(rateId)")
public class RateEntity extends AbstractEntity {

    @Column(name = "id", isId = true, autoGen = true)
    private Integer id;

    @Column(name = "rateId")
    private Integer rateId;

    @Column(name = "rateName")
    private String rateName;

    @Column(name = "areaId")
    private Integer areaId;

    @Column(name = "nature")
    private Integer nature;

    @Column(name = "voltageLevel")
    private Integer voltageLevel;

    @Column(name = "energyRateId")
    private Integer energyRateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRateId() {
        return rateId;
    }

    public void setRateId(Integer rateId) {
        this.rateId = rateId;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }

    public Integer getVoltageLevel() {
        return voltageLevel;
    }

    public void setVoltageLevel(Integer voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    public Integer getEnergyRateId() {
        return energyRateId;
    }

    public void setEnergyRateId(Integer energyRateId) {
        this.energyRateId = energyRateId;
    }
}

