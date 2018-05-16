package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 通过户号和设备号查询下级企业设备参数
 * author lizhen
 */
public class QueryComEquParams extends ClinetRequestParams {

    public static final String INTERFACE = "selectComEqu.do";

    /**
     * 户号id
     */
    private Integer eleAccountsId;
    /**
     * 当前设备id
     */
    private Integer equipmentId;
    /**
     * 当前企业设备pid
     */
    private Integer pid;

    public Integer getEleAccountsId() {
        return eleAccountsId;
    }

    public void setEleAccountsId(Integer eleAccountsId) {
        this.eleAccountsId = eleAccountsId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
