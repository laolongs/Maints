package cn.tties.maint.enums;

/**
 * 检修项类型.
 * @author Justin
 *
 */
public enum QuestionStatusType {

    HANDING(0, "处理中"),
    END(1, "已处理");

    private int type;
    private String info;

    QuestionStatusType(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public int getType() {
        return type;
    }
    public String getInfo() {
        return info;
    }
    public static QuestionStatusType get(int type) {
        for (QuestionStatusType itemType : QuestionStatusType.values()) {
            if (itemType.getType() == type) {
                return itemType;
            }
        }
        return null;
    }

    public static int getValue(String info) {
        for (QuestionStatusType auestionStatusType : QuestionStatusType.values()) {
            if (auestionStatusType.getInfo().equals(info)) {
                return auestionStatusType.getType();
            }
        }
        throw new RuntimeException("不支持的工单状态");
    }

}
