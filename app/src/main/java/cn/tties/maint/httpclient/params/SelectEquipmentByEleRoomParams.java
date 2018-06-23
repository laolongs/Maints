package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 查询电房所有企业设备.
 */

public class SelectEquipmentByEleRoomParams extends ClinetRequestParams {
    private Integer roomId;

    private Integer eleAccountId;

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public static final String INTERFACE = "selectEquipmentByEleRoom.do";
    /**
     * {
     "errorCode": 0,
     "errorMessage": "成功",
     "result": [
     {
     "companyEquipmentId": 942,
     "createTime": "2018-05-28 10:15:29",
     "eleAccountId": 54,
     "equipmentId": 2,
     "flag": 0,
     "name": "天天智电",
     "pid": -1,
     "roomId": 40
     },
     {
     "companyEquipmentId": 940,
     "createTime": "2018-05-25 15:54:51",
     "eleAccountId": 54,
     "equipmentId": 2,
     "flag": 0,
     "name": "架空线",
     "pid": -1,
     "roomId": 40
     },
     {
     "companyEquipmentId": 489,
     "createTime": "2018-01-24 19:07:37",
     "eleAccountId": 54,
     "equipmentId": 2,
     "flag": 0,
     "name": "架空线123",
     "pid": -1,
     "roomId": 40
     }
     ]
     }
     */
}
