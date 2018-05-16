package cn.tties.maint.enums;

/**
 * 额外巡视类型.
 * @author Justin
 *
 */
public enum FaultType {

    NORLMAL(1, "紧急"),
    DANGEROUS(2, "重大"),
    FAULT(3, "一般");

    private int type;
    private String info;

    FaultType(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public int getType() {
        return type;
    }
    public String getInfo() {
        return info;
    }
    public static FaultType get(int type) {
        for (FaultType itemType : FaultType.values()) {
            if (itemType.getType() == type) {
                return itemType;
            }
        }
        return null;
    }

    public static String getInfo(Integer value) {
        for (FaultType faultType : FaultType.values()) {
            if (faultType.getType() == value) {
                return faultType.getInfo();
            }
        }
        return "";
    }
}
