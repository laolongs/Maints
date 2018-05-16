package cn.tties.maint.httpclient.result;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

import cn.tties.maint.httpclient.ClinetRequestParams;
import cn.tties.maint.widget.JsonResponseParser;

/**
 * Created by fultrust on 2018/1/7.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class CEquipAndItemByPidResult extends ClinetRequestParams {

    List<EquipmentInfoResult> selectListEquInfo;

    CompanyEquipmentResult companyEquipment;

    public List<EquipmentInfoResult> getSelectListEquInfo() {
        return selectListEquInfo;
    }

    public void setSelectListEquInfo(List<EquipmentInfoResult> selectListEquInfo) {
        this.selectListEquInfo = selectListEquInfo;
    }

    public CompanyEquipmentResult getCompanyEquipment() {
        return companyEquipment;
    }

    public void setCompanyEquipment(CompanyEquipmentResult companyEquipment) {
        this.companyEquipment = companyEquipment;
    }
}
