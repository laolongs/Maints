package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/7.
 */

public class AddWorkOrderActionParams extends ClinetRequestParams {

    public static final String INTERFACE ="addWorkOrderAction.do";

    private Integer companyId;
    private Integer staffId;
    private Integer fromStaffId;
    private Integer startDate;
    private Integer endDate;
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getFromStaffId() {
        return fromStaffId;
    }

    public void setFromStaffId(Integer fromStaffId) {
        this.fromStaffId = fromStaffId;
    }

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }

    public Integer getEndDate() {
        return endDate;
    }

    public void setEndDate(Integer endDate) {
        this.endDate = endDate;
    }
}
