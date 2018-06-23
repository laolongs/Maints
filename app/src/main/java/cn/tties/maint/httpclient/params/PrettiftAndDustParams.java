package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 *查询，美化安规和除尘清理列表  1 美化  2 除尘
 */

public class PrettiftAndDustParams extends ClinetRequestParams {

    public static final String INTERFACE ="selectPrettifyDustItemByType.do";

    private int prettifyDustType;

    public int getPrettifyDustType() {
        return prettifyDustType;
    }

    public void setPrettifyDustType(int prettifyDustType) {
        this.prettifyDustType = prettifyDustType;
    }
}
