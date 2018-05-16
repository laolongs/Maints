package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 修改企业设备
 * author Lizhen
 */
public class UpdateComEquParams extends ClinetRequestParams {

    public static final String INTERFACE = "updateComEquList.do";

    /**
     * {'newComEquName':'新新企业设备222','comEquId':50,'updateEquItemInfo':[{'equipmentInfoId':21,'newEquipmentInfo':'qw123errr'},{'equipmentInfoId':22,'newEquipmentInfo':'qwer123rr'}]}
     */
    private String paramJson;

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }
}
