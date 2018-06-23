package cn.tties.maint.httpclient.params;

import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * Created by fultrust on 2018/1/7.
 */

public class Prettift_DescriptionParams extends ClinetRequestParams {

    public static final String INTERFACE ="UploadPrettifyDustDescriptionImage.do";

    private int companyId;
    private int workOrderId;
    private int descriptionType;
    private int prettifyDustType;
    private int maintStaffId;
    private String content;

    public int getPrettifyDustType() {
        return prettifyDustType;
    }

    public void setPrettifyDustType(int prettifyDustType) {
        this.prettifyDustType = prettifyDustType;
    }

    public int getMaintStaffId() {
        return maintStaffId;
    }

    public void setMaintStaffId(int maintStaffId) {
        this.maintStaffId = maintStaffId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(int descriptionType) {
        this.descriptionType = descriptionType;
    }
}
