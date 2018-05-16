package cn.tties.maint.httpclient.result;

import com.google.gson.JsonElement;

import java.util.List;

/**
 * Created by fultrust on 2018/1/6.
 */

public class FrameResult {

    private Long frameId;

    private Long ret;

    private Boolean isLast;

    private Boolean isTimeOut;

    private String msg;

    private List<JsonElement> DataList;

    public Long getFrameId() {
        return frameId;
    }

    public void setFrameId(Long frameId) {
        this.frameId = frameId;
    }

    public Long getRet() {
        return ret;
    }

    public void setRet(Long ret) {
        this.ret = ret;
    }

    public Boolean getLast() {
        if (isLast == null) {
            isLast = false;
        }
        return isLast;
    }

    public void setLast(Boolean last) {
        isLast = last;
    }

    public Boolean getTimeOut() {
        if (isTimeOut == null) {
            isTimeOut = false;
        }
        return isTimeOut;
    }

    public void setTimeOut(Boolean timeOut) {
        isTimeOut = timeOut;
    }

    public String getMsg() {
        if (msg == null) {
            msg = "请等待";
        }
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<JsonElement> getDataList() {
        return DataList;
    }

    public void setDataList(List<JsonElement> dataList) {
        DataList = dataList;
    }
}
