package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 查询该用电户号下的未在此电房下的设备（设备只到三级设备，四级设备不显示出来）.
 */

public class SelectCompanyEquipmentByAccountParams extends ClinetRequestParams {
    public static final String INTERFACE = "selectCompanyEquipmentByAccount.do";

    private Integer eleAccountId;

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public Integer getEleAccountId() {
        return eleAccountId;
    }
}
