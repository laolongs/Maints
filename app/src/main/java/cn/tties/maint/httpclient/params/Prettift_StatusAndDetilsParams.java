package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 查询美化安规  处理前 处理后  状态
 */

public class Prettift_StatusAndDetilsParams extends ClinetRequestParams {

    public static final String INTERFACE ="selectPrettifyDustDescriptionByWorkOrderIdAndDescriptionTypeAndPrettifyDustType.do";

    private int workOrderId;
    private int prettifyDustType;

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
