package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/7.
 *
 */

public class QueryPrettiftParams extends ClinetRequestParams {

    public static final String INTERFACE ="selectPrettifyDustDescriptionByWorkOrderIdAndDescriptionTypeAndPrettifyDustType.do";

    private int prettifyDustType;
    private int workOrderId;

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getPrettifyDustType() {
        return prettifyDustType;
    }

    public void setPrettifyDustType(int prettifyDustType) {
        this.prettifyDustType = prettifyDustType;
    }
}
