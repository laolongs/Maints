package cn.tties.maint.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */


public enum PowerUtilizationNatureType {

    LARGE_SCALE_INDUSTRY(1, "大工业用电"),
    GENERAL_INDUSTRY_COMMERCE(2, "一般工商业");
    private final Integer value;
    private final String info;

    private PowerUtilizationNatureType(Integer value, String info) {
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
        for (PowerUtilizationNatureType marketMessionType : PowerUtilizationNatureType.values()) {
            if (marketMessionType.getValue().equals(value)) {
                return marketMessionType.getInfo();
            }
        }
        return null;
    }

    public static Integer getValue(String info) {
        for (PowerUtilizationNatureType marketMessionType : PowerUtilizationNatureType.values()) {
            if (marketMessionType.getInfo().equals(info)) {
                return marketMessionType.getValue();
            }
        }
        return null;
    }

    public static PowerUtilizationNatureType getTpye(Integer value) {
        for (PowerUtilizationNatureType marketMessionType : PowerUtilizationNatureType.values()) {
            if (marketMessionType.getValue().equals(value)) {
                return marketMessionType;
            }
        }
        return null;
    }

    public static PowerUtilizationNatureType getTpye(String info) {
        for (PowerUtilizationNatureType marketMessionType : PowerUtilizationNatureType.values()) {
            if (marketMessionType.getInfo().equals(info)) {
                return marketMessionType;
            }
        }
        return null;
    }

    public static List<String> getInfoList() {
        List<String> list = new ArrayList<>();
        for (PowerUtilizationNatureType marketOrderKindType : PowerUtilizationNatureType.values()) {
            list.add(marketOrderKindType.getInfo());
        }
        return list;
    }

    public static List<Integer> getValueList() {
        List<Integer> list = new ArrayList<>();
        for (PowerUtilizationNatureType marketOrderKindType : PowerUtilizationNatureType.values()) {
            list.add(marketOrderKindType.getValue());
        }
        return list;
    }

    public static int getPostion(Integer value) {
        int i =0;
        for (PowerUtilizationNatureType marketMessionType : PowerUtilizationNatureType.values()) {
            if (marketMessionType.getValue().equals(value)) {
                return i;
            }
            i++;
        }
        return i;
    }
}
