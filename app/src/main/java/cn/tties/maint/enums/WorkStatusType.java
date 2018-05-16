package cn.tties.maint.enums;

/**
 * 工单状态.
 */
public enum WorkStatusType {

    UNSTART(0, "未进行"), STARTING(1, "进行中"), END(2, "已完成");
    private final int value;
    private final String info;

    private WorkStatusType(int value, String info) {
        this.value = value;
        this.info = info;
    }

    public int getValue() {
        return value;
    }

    public String getInfo() {
        return info;
    }

    public static String getInfo(int value) {
        for (WorkStatusType orderStatusType : WorkStatusType.values()) {
            if (orderStatusType.getValue() == value) {
                return orderStatusType.getInfo();
            }
        }
        return "";
    }

    public static int getValue(String info) {
        for (WorkStatusType orderStatusType : WorkStatusType.values()) {
            if (orderStatusType.getInfo().equals(info)) {
                return orderStatusType.getValue();
            }
        }
        throw new RuntimeException("不支持的工单状态");
    }

    public static WorkStatusType getTpye(int type) {
        for (WorkStatusType orderStatusType : WorkStatusType.values()) {
            if (orderStatusType.getValue() == type) {
                return orderStatusType;
            }
        }
        return null;
    }
}
