package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/7.
 */

public class DeleteEleBillParams extends ClinetRequestParams {

    public static final String INTERFACE ="deleteEleBill.do";

    private Integer billId;

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }
}
