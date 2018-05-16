package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 删除企业设备
 * author Lizhen
 */
public class DeleteComEquParams extends ClinetRequestParams {

    public static final String INTERFACE = "deleteComEqu.do";

    /**
     * 企业设备id
     */
    private String comEquIds;

    public String getComEquIds() {
        return comEquIds;
    }

    public void setComEquIds(String comEquIds) {
        this.comEquIds = comEquIds;
    }
}
