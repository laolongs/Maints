package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 根据设备类型获取企业设备列表接口.
 */

public class SelectCompanyEquipmentByEquipmentTypeParams extends ClinetRequestParams {
    public static final String INTERFACE = "selectCompanyEquipmentByEquipmentType.do";

    private int eleAccountId;
    private Integer type;

    public int getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(int eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

}
