package cn.tties.maint.enums;

/**
 * 检修项类型.
 * @author Justin
 *
 */
public enum QuestionStatusType {

    UNSTART(0, "未进行"),
    END(1, "已完成"),
    HANDING(2, "进行中");

    private int type;
    private String info;

    QuestionStatusType(int type, String info) {
        this.type = type;
        this.info = info;
    }
    public int getValue() {
        return type;
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
