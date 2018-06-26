package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/5.
 * 企业微信群发   安全交底
 */

public class Patrol_WXParams extends ClinetRequestParams {

    public static final String INTERFACE = "createQuestionDescriptionParam.do";

    private Integer maintStaffId;

    private String companyNameAndElectricRoomPatrol;

    private String content;

    public Integer getMaintStaffId() {
        return maintStaffId;
    }

    public void setMaintStaffId(Integer maintStaffId) {
        this.maintStaffId = maintStaffId;
    }

    public String getCompanyNameAndElectricRoomPatrol() {
        return companyNameAndElectricRoomPatrol;
    }

    public void setCompanyNameAndElectricRoomPatrol(String companyNameAndElectricRoomPatrol) {
        this.companyNameAndElectricRoomPatrol = companyNameAndElectricRoomPatrol;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
