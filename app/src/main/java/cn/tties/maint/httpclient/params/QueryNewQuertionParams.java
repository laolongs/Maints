package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 查询问题列表请求参数.2.0
 * Created by fultrust on 2018/1/7.
 */

public class QueryNewQuertionParams extends ClinetRequestParams {

    public static final String INTERFACE = "selectCompanyQuestionList.do";

    private int status;

    private Integer maintStaffId;

    private Integer[] companyIds;

    private String  createTimeEnd;

    private String  createTimeStart;

    public Integer getMaintStaffId() {
        return maintStaffId;
    }

    public void setMaintStaffId(Integer maintStaffId) {
        this.maintStaffId = maintStaffId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer[] getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(Integer[] companyIds) {
        this.companyIds = companyIds;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }
}
