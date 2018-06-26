package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 *公司信息
 */

public class CompanyParams extends ClinetRequestParams {

//    public static final String INTERFACE = "selectListCompany.do";//原接口
    public static final String INTERFACE = "selectCompanyListByMaintStaff.do";//selectCompanyListByMaintStaff.do

    private Integer maintStaffId;

    private Integer creatorId;

    private Integer companyId;

    public Integer getMaintStaffId() {
        return maintStaffId;
    }

    public void setMaintStaffId(Integer maintStaffId) {
        this.maintStaffId = maintStaffId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
