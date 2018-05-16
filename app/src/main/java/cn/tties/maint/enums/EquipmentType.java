package cn.tties.maint.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */


public enum EquipmentType {

    CABINET(1, "柜体"),
    TRANSFORMER(2, "变压器"),
    EQUIPMENT(3, "设备"),
    TOOL(4, "工器具");
    private final Integer value;
    private final String info;

    private EquipmentType(Integer value, String info) {
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
        for (EquipmentType marketMessionType : EquipmentType.values()) {
            if (marketMessionType.getValue().equals(value)) {
                return marketMessionType.getInfo();
            }
        }
        return null;
    }

    public static Integer getValue(String info) {
        for (EquipmentType marketMessionType : EquipmentType.values()) {
            if (marketMessionType.getInfo().equals(info)) {
                return marketMessionType.getValue();
            }
        }
        return null;
    }

    public static EquipmentType getTpye(Integer value) {
        for (EquipmentType marketMessionType : EquipmentType.values()) {
            if (marketMessionType.getValue().equals(value)) {
                return marketMessionType;
            }
        }
        return null;
    }

    public static List<String> getInfoList() {
        List<String> list = new ArrayList<>();
        for (EquipmentType marketOrderKindType : EquipmentType.values()) {
            list.add(marketOrderKindType.getInfo());
        }
        return list;
    }

    public static List<Integer> getValueList() {
        List<Integer> list = new ArrayList<>();
        for (EquipmentType marketOrderKindType : EquipmentType.values()) {
            list.add(marketOrderKindType.getValue());
        }
        return list;
    }
}
