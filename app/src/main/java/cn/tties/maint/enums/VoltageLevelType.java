package cn.tties.maint.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */


public enum VoltageLevelType {

    LS_1_TO_10_KV(1, PowerUtilizationNatureType.LARGE_SCALE_INDUSTRY, "1-10千伏"),
    LS_20_KV(2, PowerUtilizationNatureType.LARGE_SCALE_INDUSTRY, "20千伏"),
    LS_35_KV(3, PowerUtilizationNatureType.LARGE_SCALE_INDUSTRY, "35千伏"),
    LS_110_KV(4, PowerUtilizationNatureType.LARGE_SCALE_INDUSTRY, "110千伏"),
    LS_220_ABOVE_KV(5, PowerUtilizationNatureType.LARGE_SCALE_INDUSTRY, "220千伏及以上"),

    GE_UNDER_1_KV(6, PowerUtilizationNatureType.GENERAL_INDUSTRY_COMMERCE, "不满1千伏"),
    GE_1_TO_10_KV(7, PowerUtilizationNatureType.GENERAL_INDUSTRY_COMMERCE, "1-10千伏"),
    GE_20_KV(8, PowerUtilizationNatureType.GENERAL_INDUSTRY_COMMERCE, "20千伏"),
    GE_35_ABOVE_KV(9, PowerUtilizationNatureType.GENERAL_INDUSTRY_COMMERCE, "35千伏及以上");

    private int type;
    private PowerUtilizationNatureType nature;
    private String info;

    VoltageLevelType(int type, PowerUtilizationNatureType nature, String info) {
        this.type = type;
        this.nature = nature;
        this.info = info;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public PowerUtilizationNatureType getNature() {
        return nature;
    }

    public void setNature(PowerUtilizationNatureType nature) {
        this.nature = nature;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static VoltageLevelType get(int type) {
        for (VoltageLevelType voltageLevelType : VoltageLevelType.values()) {
            if (voltageLevelType.getType() == type) {
                return voltageLevelType;
            }
        }
        return null;
    }

    public static List<VoltageLevelType> getVoltagerLevelListByNature(PowerUtilizationNatureType nature) {
        List<VoltageLevelType> result = new ArrayList<>();
        for (cn.tties.maint.enums.VoltageLevelType VoltageLevelType : VoltageLevelType.values()) {
            if (VoltageLevelType.nature.equals(nature)) {
                result.add(VoltageLevelType);
            }
        }
        return result;
    }

    public static int getValue(String info, PowerUtilizationNatureType type) {
        for (VoltageLevelType voltageLevelType : getVoltagerLevelListByNature(type)) {
            if (voltageLevelType.getInfo().equals(info)) {
                return voltageLevelType.getType();
            }
        }
        throw new RuntimeException("不支持的电压等级");
    }
}
