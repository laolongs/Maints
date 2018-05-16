package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/7.
 */

public class GetTransformerParams extends ClinetRequestParams {

    public static final String INTERFACE ="getTransformer.do";

    private Integer eleAccountId;

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }
}
