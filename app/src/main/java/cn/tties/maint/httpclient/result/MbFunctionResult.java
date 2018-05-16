package cn.tties.maint.httpclient.result;

import java.io.Serializable;

/**
 * Created by fultrust on 2018/1/5.
 */

public class MbFunctionResult implements Serializable {

    private Integer funcId;
    private String funcName;
    private String alias;
    private Integer pid;
    private Integer funcType;
    private String url;
    private String disporder;
    private String remark;

    public Integer getFuncId() {
        return funcId;
    }

    public void setFuncId(Integer funcId) {
        this.funcId = funcId;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getFuncType() {
        return funcType;
    }

    public void setFuncType(Integer funcType) {
        this.funcType = funcType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisporder() {
        return disporder;
    }

    public void setDisporder(String disporder) {
        this.disporder = disporder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
