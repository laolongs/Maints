package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by li on 2018/5/29
 * description：根据二级id 查询三级四级企业设备
 * author：guojlli
 */

public class SelectCompanyEquipmentByCompanyParams extends ClinetRequestParams {
    public static final String INTERFACE = "selectCompanyEquipmentByCompanyEquipmentId.do";

    private Integer companyEquipmentId;

    public Integer getCompanyEquipmentId() {
        return companyEquipmentId;
    }

    public void setCompanyEquipmentId(Integer companyEquipmentId) {
        this.companyEquipmentId = companyEquipmentId;
    }
}
