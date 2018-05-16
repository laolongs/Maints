package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 获取电费单
 * author chensi
 */
public class QueryEleBillParams extends ClinetRequestParams {

    public static final String INTERFACE = "queryEleBill.do";

    private Integer eleAccountId;
    private Integer year;

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
