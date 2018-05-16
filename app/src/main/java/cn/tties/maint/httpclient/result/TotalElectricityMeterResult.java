package cn.tties.maint.httpclient.result;


import java.util.List;

/**
 * 根据企业设备id查询设备项信息
 * author Lizhen
 */
public class TotalElectricityMeterResult {

    private List<MeterResult> haveMeterList;

    private List<MeterResult> noHaveMeterList;

    public List<MeterResult> getHaveMeterList() {
        return haveMeterList;
    }

    public void setHaveMeterList(List<MeterResult> haveMeterList) {
        this.haveMeterList = haveMeterList;
    }

    public List<MeterResult> getNoHaveMeterList() {
        return noHaveMeterList;
    }

    public void setNoHaveMeterList(List<MeterResult> noHaveMeterList) {
        this.noHaveMeterList = noHaveMeterList;
    }
}
