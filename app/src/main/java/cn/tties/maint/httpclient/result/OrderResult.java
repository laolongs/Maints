package cn.tties.maint.httpclient.result;

import java.io.Serializable;

import cn.tties.maint.enums.WorkStatusType;
import cn.tties.maint.enums.WorkType;

/**
 * Created by fultrust on 2018/1/5.
 */

public class OrderResult implements Serializable {

    private Integer workOrderId;

    private Integer companyId;

    private Integer staffId;

    private Integer workType;

    private Integer fromStaffId;

    private Integer status;

    private Integer startDate;

    private Integer endDate;

    private Integer inTime;

    private String createTime;

    private String updateTime;

    private Integer xunShiCount;

    private CompanyResult company;

    public CompanyResult getCompany() {
        if (company == null) {
            company = new CompanyResult();
        }
        return company;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

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

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public Integer getFromStaffId() {
        return fromStaffId;
    }

    public void setFromStaffId(Integer fromStaffId) {
        this.fromStaffId = fromStaffId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public void setCompany(CompanyResult company) {
        this.company = company;
    }

    public WorkType getWorkTypeType() {
        return WorkType.getTpye(workType);
    }

    public WorkStatusType getStatusType() {
        return WorkStatusType.getTpye(status);
    }

    public Integer getInTime() {
        return inTime;
    }

    public void setInTime(Integer inTime) {
        this.inTime = inTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getXunShiCount() {
        return xunShiCount;
    }

    public void setXunShiCount(Integer xunShiCount) {
        this.xunShiCount = xunShiCount;
    }
}
