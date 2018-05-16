package cn.tties.maint.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 额外巡视类型.
 * @author Justin
 *
 */
public enum ConnModeType {
    THREE(1, "三相三线"),
    FOUR(2, "三相四线"),
    ONE(3, "单相表");

    private int type;
    private String info;

    ConnModeType(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public int getType() {
        return type;
    }
    public String getInfo() {
        return info;
    }
    public static ConnModeType get(int type) {
        for (ConnModeType connModeType : ConnModeType.values()) {
            if (connModeType.getType() == type) {
                return connModeType;
            }
        }
        return null;
    }

    public static String getInfo(Integer value) {
        for (ConnModeType connModeType : ConnModeType.values()) {
            if (connModeType.getType() == value) {
                return connModeType.getInfo();
            }
        }
        return "";
    }

    public static int getValue(String info) {
        for (ConnModeType connModeType : ConnModeType.values()) {
            if (connModeType.getInfo().equals(info)) {
                return connModeType.getType();
            }
        }
        throw new RuntimeException("不支持的接线方式");
    }

    public static List<String> getInfoList() {
        List<String> list = new ArrayList<>();
        for (ConnModeType e : ConnModeType.values()) {
            list.add(e.getInfo());
        }
        return list;
    }
}
