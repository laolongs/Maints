package cn.tties.maint.enums;

/**
 * 检修项类型.
 * @author Justin
 *
 */
public enum OverhaulItemType {

    CABINET(1, "柜体"),
    TRANSFORMER(2, "变压器"),
    ROOM(3, "配电房"),
    TRANSFORMER_ROOM(4, "变压器室");

    private int type;
    private String info;

    OverhaulItemType(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public int getType() {
        return type;
    }
    public String getInfo() {
        return info;
    }
    public static OverhaulItemType get(int type) {
        for (OverhaulItemType itemType : OverhaulItemType.values()) {
            if (itemType.getType() == type) {
                return itemType;
            }
        }
        return null;
    }

}
