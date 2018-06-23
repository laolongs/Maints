package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 * 删除采集点   需要运维  能效 采集点  平台同开   必须采集点下有数据才可以删除成功
 */

public class AmmeterDeletrParams extends ClinetRequestParams {
    
    public static final String INTERFACE = "deleteMeter.do";

    private Integer meterId;

    public Integer getMeterId() {
        return meterId;
    }

    public void setMeterId(Integer meterId) {
        this.meterId = meterId;
    }
}
