package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 */

public class CreateEleRoomParams extends ClinetRequestParams {
    public static final String INTERFACE = "createEleRoom.do";
    private Integer eleAccountId;
    private String roomName;
    private Boolean isHigh;
    private Boolean isLow;
    private Boolean isTransformer;
    private Integer flag;

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setHigh(Boolean isHigh) {
        this.isHigh = isHigh;
    }

    public void setLow(Boolean isLow) {
        this.isLow = isLow;
    }

    public void setTransformer(Boolean isTransformer) {
        this.isTransformer = isTransformer;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public String getRoomName() {
        return roomName;
    }

    public Boolean getIsHigh() {
        return isHigh;
    }

    public Boolean getIsLow() {
        return isLow;
    }

    public Boolean getIsTransformer() {
        return isTransformer;
    }

    public Integer getFlag() {
        return flag;
    }


}
