package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/6.
 */

public class DeleteEleRoomParams  extends ClinetRequestParams {
    public static final String INTERFACE = "deleteEleRoom.do";
    private Integer roomId;

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomId() {
        return roomId;
    }
}
