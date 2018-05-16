package cn.tties.maint.httpclient.result;

import org.xutils.http.annotation.HttpResponse;

import cn.tties.maint.widget.JsonResponseParser;

/**
 * 根据企业设备id查询设备项信息
 *  author Lizhen
 */
@HttpResponse(parser = JsonResponseParser.class)
public class EquipmentInfoResult {

    private Integer equipmentInfoId;
    private Integer companyEquipmentId;
    private Integer equipmentItemId;
    private String content;

    public Integer getEquipmentInfoId() {
        return equipmentInfoId;
    }

    public void setEquipmentInfoId(Integer equipmentInfoId) {
        this.equipmentInfoId = equipmentInfoId;
    }

    public Integer getCompanyEquipmentId() {
        return companyEquipmentId;
    }

    public void setCompanyEquipmentId(Integer companyEquipmentId) {
        this.companyEquipmentId = companyEquipmentId;
    }

    public Integer getEquipmentItemId() {
        return equipmentItemId;
    }

    public void setEquipmentItemId(Integer equipmentItemId) {
        this.equipmentItemId = equipmentItemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
