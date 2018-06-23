package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 查询电房.
 */

public class SelectEleRoomParams extends ClinetRequestParams {

    public static final String INTERFACE = "selectEleRoom.do";

    private Integer accountId;
    private boolean hasHigh;
    private boolean hasLow;
    private boolean hasTransformer;

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public boolean getHasHigh() {
        return hasHigh;
    }

    public void setHasHigh(boolean hasHigh) {
        this.hasHigh = hasHigh;
    }

    public boolean getHasLow() {
        return hasLow;
    }

    public void setHasLow(boolean hasLow) {
        this.hasLow = hasLow;
    }

    public boolean getHasTransformer() {
        return hasTransformer;
    }

    public void setHasTransformer(boolean hasTransformer) {

        this.hasTransformer = hasTransformer;
    }
    /**
     * "result": [
     {
     "flag": 0,
     "createTime": "2018-06-02 15:30:14",
     "isLow": false,
     "isTransformer": true,
     "eleAccountId": 54,
     "roomId": 49,
     "roomName": "测试版",
     "isHigh": true
     },
     {
     "flag": 0,
     "createTime": "2018-02-26 15:40:56",
     "isLow": false,
     "isTransformer": false,
     "eleAccountId": 54,
     "roomId": 40,
     "roomName": "test",
     "isHigh": true
     },
     {
     "flag": 0,
     "createTime": "2018-01-25 10:50:12",
     "isLow": false,
     "isTransformer": false,
     "eleAccountId": 54,
     "roomId": 35,
     "roomName": "rew",
     "isHigh": true
     },
     {
     "flag": 0,
     "createTime": "2018-01-25 10:42:29",
     "isLow": true,
     "isTransformer": true,
     "eleAccountId": 54,
     "roomId": 34,
     "roomName": "321",
     "isHigh": true
     }
     ],
     "errorMessage": "成功",
     "errorCode": 0
     }
     */
}
