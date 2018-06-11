package cn.tties.maint.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */


public enum EditVoltageType {
    ONE(1, "1"),
    HUNDRED(2, "100"),
    THREEHUNDREDANDFIFTY(3, "350");


    private int type;
    private String info;

    EditVoltageType(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public int getType() {
        return type;
    }
    public String getInfo() {
        return info;
    }
    public static EditVoltageType get(int type) {
        for (EditVoltageType commProtocolType : EditVoltageType.values()) {
            if (commProtocolType.getType() == type) {
                return commProtocolType;
            }
        }
        return null;
    }

    public static String getInfo(Integer value) {
        for (EditVoltageType commProtocolType : EditVoltageType.values()) {
            if (commProtocolType.getType() == value) {
                return commProtocolType.getInfo();
            }
        }
        return "";
    }

    public static int getValue(String info) {
        for (EditVoltageType commProtocolType : EditVoltageType.values()) {
            if (commProtocolType.getInfo().equals(info)) {
                return commProtocolType.getType();
            }
        }
        return 0;
    }

    public static List<String> getInfoList() {
        List<String> list = new ArrayList<>();
        for (EditVoltageType e : EditVoltageType.values()) {
            list.add(e.getInfo());
        }
        return list;
    }
}
