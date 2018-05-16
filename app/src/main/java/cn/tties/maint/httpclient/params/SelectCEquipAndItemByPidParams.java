package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/7.
 */

public class SelectCEquipAndItemByPidParams extends ClinetRequestParams {

    public static final String INTERFACE ="selectCompanyEquipmentAndEquipmentItemByPid.do";

    private Integer pid;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
