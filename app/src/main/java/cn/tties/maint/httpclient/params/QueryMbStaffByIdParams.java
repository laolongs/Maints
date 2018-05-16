package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 */

public class QueryMbStaffByIdParams extends ClinetRequestParams {

    public static final String INTERFACE = "queryMbStaffById.do";

    private Integer staffId;

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }
}
