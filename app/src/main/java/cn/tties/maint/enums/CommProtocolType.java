package cn.tties.maint.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 额外巡视类型.
 * @author Justin
 *
 */
public enum CommProtocolType {
    PROTOCOL1(1, "DL/T 645—1997"),
    PROTOCOL2(2, "交流采样装置通信协议"),
    PROTOCOL30(30, "DL/T 645—2007");

    private int type;
    private String info;

    CommProtocolType(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public int getType() {
        return type;
    }
    public String getInfo() {
        return info;
    }
    public static CommProtocolType get(int type) {
        for (CommProtocolType commProtocolType : CommProtocolType.values()) {
            if (commProtocolType.getType() == type) {
                return commProtocolType;
            }
        }
        return null;
    }

    public static String getInfo(Integer value) {
        for (CommProtocolType commProtocolType : CommProtocolType.values()) {
            if (commProtocolType.getType() == value) {
                return commProtocolType.getInfo();
            }
        }
        return "";
    }

    public static int getValue(String info) {
        for (CommProtocolType commProtocolType : CommProtocolType.values()) {
            if (commProtocolType.getInfo().equals(info)) {
                return commProtocolType.getType();
            }
        }
        return 0;
    }

    public static List<String> getInfoList() {
        List<String> list = new ArrayList<>();
        for (CommProtocolType e : CommProtocolType.values()) {
            list.add(e.getInfo());
        }
        return list;
    }
}
