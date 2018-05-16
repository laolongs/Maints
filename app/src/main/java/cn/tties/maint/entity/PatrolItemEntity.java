package cn.tties.maint.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import cn.tties.maint.enums.FaultType;
import cn.tties.maint.httpclient.result.PatrolResult;

/**
 *
 */
@Table(name = "patrol_item",
        onCreated = "CREATE INDEX patrol_item_search ON patrol_item(patrolItemId)")
public class PatrolItemEntity extends AbstractEntity {

    @Column(name = "id", isId = true, autoGen = true)
    private Integer id;

    @Column(name = "patrolItemId")
    private Integer patrolItemId;

    @Column(name = "equipmentId")
    private Integer equipmentId;

    @Column(name = "patrolItemTypeId")
    private Integer patrolItemTypeId;

    @Column(name = "title")
    private String title;

    @Column(name = "faultType")
    private Integer faultType;

    @Column(name = "faultHarm")
    private String faultHarm;

    @Column(name = "dealDesc")
    private String dealDesc;

    @Column(name = "itemOrder")
    private Integer itemOrder;

    @Column(name = "isHigh")
    private Boolean isHigh;

    @Column(name = "isLow")
    private Boolean isLow;

    @Column(name = "isTransfomer")
    private Boolean isTransfomer;

    @Column(name = "hasTemperature")
    private Boolean hasTemperature;

    @Column(name = "hasLineTemperature")
    private Boolean hasLineTemperature;

    @Column(name = "hasVoltage")
    private Boolean hasVoltage;

    @Column(name = "hasCurrent")
    private Boolean hasCurrent;

    @Column(name = "activePower")
    private Boolean activePower;

    @Column(name = "isBody")
    private Boolean isBody;

    private String itemTypeName;

    private String equipType;

    private int InputType;

    private PatrolResult result;

    public Integer getPatrolItemId() {
        return patrolItemId;
    }

    public void setPatrolItemId(Integer patrolItemId) {
        this.patrolItemId = patrolItemId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getPatrolItemTypeId() {
        return patrolItemTypeId;
    }

    public void setPatrolItemTypeId(Integer patrolItemTypeId) {
        this.patrolItemTypeId = patrolItemTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFaultType() {
        return faultType;
    }

    public String getFaultTypeString() {
        return FaultType.getInfo(faultType);
    }

    public void setFaultType(Integer faultType) {
        this.faultType = faultType;
    }

    public String getFaultHarm() {
        return faultHarm;
    }

    public void setFaultHarm(String faultHarm) {
        this.faultHarm = faultHarm;
    }

    public String getDealDesc() {
        return dealDesc;
    }

    public void setDealDesc(String dealDesc) {
        this.dealDesc = dealDesc;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    public Boolean getHigh() {
        return isHigh;
    }

    public void setHigh(Boolean high) {
        isHigh = high;
    }

    public Boolean getLow() {
        return isLow;
    }

    public void setLow(Boolean low) {
        isLow = low;
    }

    public Boolean getTransfomer() {
        return isTransfomer;
    }

    public void setTransfomer(Boolean transfomer) {
        isTransfomer = transfomer;
    }

    public Boolean getHasTemperature() {
        return hasTemperature;
    }

    public void setHasTemperature(Boolean hasTemperature) {
        this.hasTemperature = hasTemperature;
    }

    public Boolean getHasLineTemperature() {
        return hasLineTemperature;
    }

    public void setHasLineTemperature(Boolean hasLineTemperature) {
        this.hasLineTemperature = hasLineTemperature;
    }

    public Boolean getHasVoltage() {
        return hasVoltage;
    }

    public void setHasVoltage(Boolean hasVoltage) {
        this.hasVoltage = hasVoltage;
    }

    public Boolean getHasCurrent() {
        return hasCurrent;
    }

    public void setHasCurrent(Boolean hasCurrent) {
        this.hasCurrent = hasCurrent;
    }

    public Boolean getActivePower() {
        return activePower;
    }

    public void setActivePower(Boolean activePower) {
        this.activePower = activePower;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public PatrolResult getResult() {
        return result;
    }

    public void setResult(PatrolResult result) {
        this.result = result;
    }

    public String getEquipType() {
        return equipType;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    public int getInputType() {
        return InputType;
    }

    public void setInputType(int inputType) {
        InputType = inputType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getBody() {
        if (isBody == null) {
            return false;
        }
        return isBody;
    }

    public void setBody(Boolean body) {
        isBody = body;
    }
}
