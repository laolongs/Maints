package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 */

public class SafetyParams extends ClinetRequestParams {

    public static final String INTERFACE = "insertOrUpdateImgSendWeiXinDescriptionParam.do";

    private Integer maintStaffId;

    private String companyNameAndSafe;

    private Integer workOrderId;

    public Integer getMaintStaffId() {
        return maintStaffId;
    }

    public void setMaintStaffId(Integer maintStaffId) {
        this.maintStaffId = maintStaffId;
    }

    public String getCompanyNameAndSafe() {
        return companyNameAndSafe;
    }

    public void setCompanyNameAndSafe(String companyNameAndSafe) {
        this.companyNameAndSafe = companyNameAndSafe;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }
}
