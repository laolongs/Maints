package cn.tties.maint.enums;

/**
 *
 */
public enum ItemInputType {
    STRING(0), DOUBLE(1), INT(2),DATE(3);
    private final Integer type;

    ItemInputType(int type) {
        this.type = type;
    }


    public int getType() {
        return type;
    }

    public static ItemInputType get(int type) {
        for (ItemInputType itemType : ItemInputType.values()) {
            if (itemType.getType() == type) {
                return itemType;
            }
        }
        return null;
    }
}
