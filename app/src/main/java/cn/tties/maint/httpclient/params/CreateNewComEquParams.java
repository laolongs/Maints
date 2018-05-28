package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 234级一起添加企业设备
 * author 郭
 */
public class CreateNewComEquParams extends ClinetRequestParams {

    public static final String INTERFACE = "insertNewComEqu.do";

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
