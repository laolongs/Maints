package cn.tties.maint.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 工单类型.
 */
public enum WorkType {

    ADORN_AMMETER(0, "电表装置", "去配置"),
    REMOVE_FAULT(1, "消缺", "去消缺"),
    PRETTIFT(2, "美化安规", "去美化"),
    PATROL(3, "电房巡视", "去巡视"),
    REMOVE_DUST(4, "除尘清理", "去清理");
    //---------------老的
//    UPLOAD_ELE_BILL(0, "上传电费单", "去上传"),
//    UPLOAD_CONTRACT(1, "上传合同", "去上传"),
//    HANDOVER(2, "创建档案", "去创建"),
//    PATROL(3, "巡视", "去巡视"),
//    OVERHAUL(4, "检修", "去检修");
    private final int value;
    private final String info;
    private final String opr;

    private WorkType(int value, String info, String opr) {
        this.value = value;
        this.info = info;
        this.opr = opr;
    }

    public int getValue() {
        return value;
    }

    public String getInfo() {
        return info;
    }

    public String getOpr() {
        return opr;
    }

    public static String getInfo(int type) {
        for (WorkType marketMessionType : WorkType.values()) {
            if (marketMessionType.getValue() == type) {
                return marketMessionType.getInfo();
            }
        }
        return null;
    }

    public static int getValue(String info) {
        for (WorkType marketMessionType : WorkType.values()) {
            if (marketMessionType.getInfo().equals(info)) {
                return marketMessionType.getValue();
            }
        }
        throw new RuntimeException("不支持的工单类型");
    }

    public static WorkType getTpye(int type) {
        for (WorkType marketMessionType : WorkType.values()) {
            if (marketMessionType.getValue() == type) {
                return marketMessionType;
            }
        }
        return null;
    }

    public static List<String> getInfoList() {
        List<String> list = new ArrayList<>();
        for (WorkType marketOrderKindType : WorkType.values()) {
            list.add(marketOrderKindType.getInfo());
        }
        return list;
    }

    public static List<Integer> getValueList() {
        List<Integer> list = new ArrayList<>();
        for (WorkType marketOrderKindType : WorkType.values()) {
            list.add(marketOrderKindType.getValue());
        }
        return list;
    }
}
