package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 查询电房.
 */

public class SelectEleRoomParams extends ClinetRequestParams {

    public static final String INTERFACE = "selectEleRoom.do";

    private Integer accountId;
    private boolean hasHigh;
    private boolean hasLow;
    private boolean hasTransformer;

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public boolean getHasHigh() {
        return hasHigh;
    }

    public void setHasHigh(boolean hasHigh) {
        this.hasHigh = hasHigh;
    }

    public boolean getHasLow() {
        return hasLow;
    }

    public void setHasLow(boolean hasLow) {
        this.hasLow = hasLow;
    }

    public boolean getHasTransformer() {
        return hasTransformer;
    }

    public void setHasTransformer(boolean hasTransformer) {
        this.hasTransformer = hasTransformer;
    }
}
