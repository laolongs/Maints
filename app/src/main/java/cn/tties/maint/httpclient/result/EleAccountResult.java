package cn.tties.maint.httpclient.result;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;

import cn.tties.maint.widget.JsonResponseParser;

/**
 * 户号result
 */
@HttpResponse(parser = JsonResponseParser.class)
public class EleAccountResult implements Serializable {

    int eleAccountId;

    String eleNo;

    int companyId;

    String flag;

    Integer volume;

    public int getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(int eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public String getEleNo() {
        return eleNo;
    }

    public void setEleNo(String eleNo) {
        this.eleNo = eleNo;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getVolume() {
        if (volume == null) {
            volume = 0;
        }
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }
}
