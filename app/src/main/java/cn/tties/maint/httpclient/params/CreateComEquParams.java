package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 添加企业设备
 * author Lizhen
 */
public class CreateComEquParams extends ClinetRequestParams {

    public static final String INTERFACE = "insertComEqu.do";

    /**
     * {'comEquName':'三级测试企业设备App','equipmentId':10,'eleAccountId':11,'pid':15,'equArrFour':[{'equIdFour':17,'equItemFourArr':[{'equItemId':15,'equItemContent':'qqqq'},{'equItemId':14,'equItemContent':'qqqq'}]},{'equIdFour':17,'equItemFourArr':[{'equItemId':15,'equItemContent':'qqqq'},{'equItemId':14,'equItemContent':'qqqq'}]}]}
     */
    private String paramJson;

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }
}
