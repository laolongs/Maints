package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/6.
 */

public class UpdateEleRoomParams extends ClinetRequestParams {
    private Integer roomId;
    private Integer eleAccountId;
    private String roomName;
    private Boolean isHigh;
    private Boolean isLow;
    private Boolean isTransformer;

    public Integer getRoomId() {
        return roomId;
    }

    public Boolean getHigh() {
        return isHigh;
    }

    public Boolean getLow() {
        return isLow;
    }

    public Boolean getTransformer() {
        return isTransformer;
    }

    private Integer flag;
    private String createTime;

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

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

    public String getCreateTime() {
        return createTime;
    }

    public static final String INTERFACE = "updateEleRoom.do";
}
