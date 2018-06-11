package cn.tties.maint.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 电表进线类型.
 * @author Justin
 *
 */
public enum LineInType {
       LOW(1, "低压"),
    HIGH(2, "高压");

    private int type;
    private String info;

    LineInType(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public int getType() {
        return type;
    }
    public String getInfo() {
        return info;
    }
    public static LineInType get(int type) {
        for (LineInType commProtocolType : LineInType.values()) {
            if (commProtocolType.getType() == type) {
                return commProtocolType;
            }
        }
        return null;
    }

    public static String getInfo(Integer value) {
        for (LineInType commProtocolType : LineInType.values()) {
            if (commProtocolType.getType() == value) {
                return commProtocolType.getInfo();
            }
        }
        return "";
    }

    public static int getValue(String info) {
        for (LineInType commProtocolType : LineInType.values()) {
            if (commProtocolType.getInfo().equals(info)) {
                return commProtocolType.getType();
            }
        }
        return 0;
    }

    public static List<String> getInfoList() {
        List<String> list = new ArrayList<>();
        for (LineInType e : LineInType.values()) {
            list.add(e.getInfo());
        }
        return list;
    }
}
