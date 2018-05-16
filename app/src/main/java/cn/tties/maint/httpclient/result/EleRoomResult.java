package cn.tties.maint.httpclient.result;

import cn.tties.maint.bean.CommonListViewInterface;

/**
 * Created by fultrust on 2018/1/6.
 */

public class EleRoomResult implements CommonListViewInterface {
    private Integer roomId;
    private Integer eleAccountId;
    private String roomName;
    private Boolean isHigh;
    private Boolean isLow;
    private Boolean isTransformer;
    private Integer flag;
    private String createTime;

    private boolean isChecked;

    public Integer getRoomId() {
        return roomId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
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

    @Override
    public Integer getItemId() {
        return this.roomId;
    }

    @Override
    public String getItemName() {
        return this.roomName;
    }

    @Override
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override
    public void setChecked(boolean checkedItem) {
        this.isChecked = checkedItem;
    }
}
