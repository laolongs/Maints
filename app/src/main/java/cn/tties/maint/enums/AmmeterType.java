package cn.tties.maint.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */


public enum AmmeterType {

//    BASE_INFO(1, "基础信息"),
//    ACCOUNT(2, "开通账户"),
    COLLECTION_POINT(3, "采集点配置"),
    TOTAL_ELECTRIC(4, "总电量配置"),
    INFRARED_POINT(5, "电表红外配置");
    private final Integer value;
    private final String info;

    private AmmeterType(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public Integer getValue() {
        return value;
    }

    public String getInfo() {
        return info;
    }

    public static String getInfo(Integer value) {
        for (AmmeterType marketMessionType : AmmeterType.values()) {
            if (marketMessionType.getValue().equals(value)) {
                return marketMessionType.getInfo();
            }
        }
        return null;
    }

    public static Integer getValue(String info) {
        for (AmmeterType marketMessionType : AmmeterType.values()) {
            if (marketMessionType.getInfo().equals(info)) {
                return marketMessionType.getValue();
            }
        }
        return null;
    }

    public static AmmeterType getTpye(Integer value) {
        for (AmmeterType marketMessionType : AmmeterType.values()) {
            if (marketMessionType.getValue().equals(value)) {
                return marketMessionType;
            }
        }
        return null;
    }

    public static List<String> getInfoList() {
        List<String> list = new ArrayList<>();
        for (AmmeterType marketOrderKindType : AmmeterType.values()) {
            list.add(marketOrderKindType.getInfo());
        }
        return list;
    }

    public static List<Integer> getValueList() {
        List<Integer> list = new ArrayList<>();
        for (AmmeterType marketOrderKindType : AmmeterType.values()) {
            list.add(marketOrderKindType.getValue());
        }
        return list;
    }
}
