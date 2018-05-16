package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 *查询员工列表请求参数.
 */

public class GetMetsureStatusParams extends ClinetRequestParams {
    public static final String INTERFACE = "getMetsureStatus.do";

    private Long pointId;

    private Long frameId;

    public Long getFrameId() {
        return frameId;
    }

    public void setFrameId(Long frameId) {
        this.frameId = frameId;
    }

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }
}
