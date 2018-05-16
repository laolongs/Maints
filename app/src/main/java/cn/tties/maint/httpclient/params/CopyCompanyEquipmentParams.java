package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 */

public class CopyCompanyEquipmentParams extends ClinetRequestParams {

    public static final String INTERFACE = "copyCompanyEquipment.do";

    private Integer companyEquipmentId;

    private String companyEquipmentName;

    public Integer getCompanyEquipmentId() {
        return companyEquipmentId;
    }

    public void setCompanyEquipmentId(Integer companyEquipmentId) {
        this.companyEquipmentId = companyEquipmentId;
    }

    public String getCompanyEquipmentName() {
        return companyEquipmentName;
    }

    public void setCompanyEquipmentName(String companyEquipmentName) {
        this.companyEquipmentName = companyEquipmentName;
    }
}
