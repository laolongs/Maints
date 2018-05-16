package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 根据企业设备id查询设备项信息
 * author Lizhen
 */
public class QueryEquItemParams extends ClinetRequestParams {

    public static final String INTERFACE = "selectEquItemContent.do";

    /**
     * 企业设备id
     */
    private int comEquId;

    public int getComEquId() {
        return comEquId;
    }

    public void setComEquId(int comEquId) {
        this.comEquId = comEquId;
    }
}
