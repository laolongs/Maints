package cn.tties.maint.httpclient.result;


import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

import cn.tties.maint.entity.AreaEntity;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.EquipmentItemEntity;
import cn.tties.maint.entity.IndustryEntity;
import cn.tties.maint.entity.OverhaulItemEntity;
import cn.tties.maint.entity.PatrolItemEntity;
import cn.tties.maint.entity.PatrolItemTypeEntity;
import cn.tties.maint.entity.RateEntity;
import cn.tties.maint.widget.JsonResponseParser;

/**
 * 查询所有数据
 * author lizhen
 */
@HttpResponse(parser = JsonResponseParser.class)
public class AllDataResult implements Serializable {

    private List<EquipmentEntity> equipmentList;
    private List<EquipmentItemEntity> equipmentItemList;
    private List<PatrolItemEntity> patrolItemList;
    private List<OverhaulItemEntity> overhaulItemList;
    private List<PatrolItemTypeEntity> patrolItemTypeList;
    private List<AreaEntity> areaList;
    private List<IndustryEntity> industryList;
    private List<RateEntity> rateList;

    public List<EquipmentEntity> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<EquipmentEntity> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<EquipmentItemEntity> getEquipmentItemList() {
        return equipmentItemList;
    }

    public void setEquipmentItemList(List<EquipmentItemEntity> equipmentItemList) {
        this.equipmentItemList = equipmentItemList;
    }

    public List<PatrolItemEntity> getPatrolItemList() {
        return patrolItemList;
    }

    public void setPatrolItemList(List<PatrolItemEntity> patrolItemList) {
        this.patrolItemList = patrolItemList;
    }

    public List<OverhaulItemEntity> getOverhaulItemList() {
        return overhaulItemList;
    }

    public void setOverhaulItemList(List<OverhaulItemEntity> overhaulItemList) {
        this.overhaulItemList = overhaulItemList;
    }

    public List<PatrolItemTypeEntity> getPatrolItemTypeList() {
        return patrolItemTypeList;
    }

    public void setPatrolItemTypeList(List<PatrolItemTypeEntity> patrolItemTypeList) {
        patrolItemTypeList = patrolItemTypeList;
    }

    public List<AreaEntity> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaEntity> areaList) {
        this.areaList = areaList;
    }

    public List<IndustryEntity> getIndustryList() {
        return industryList;
    }

    public void setIndustryList(List<IndustryEntity> industryList) {
        this.industryList = industryList;
    }

    public List<RateEntity> getRateList() {
        return rateList;
    }

    public void setRateList(List<RateEntity> rateList) {
        this.rateList = rateList;
    }
}
