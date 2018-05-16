package cn.tties.maint.enums;

/**
 * 额外巡视类型.
 * @author Justin
 *
 */
public enum OtherPatrolInputType {

    BODY("计量柜柜体");

    private String info;

    OtherPatrolInputType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public static OtherPatrolInputType getType(String info) {
        for (OtherPatrolInputType inputType : OtherPatrolInputType.values()) {
            if (inputType.getInfo().equals(info)) {
                return inputType;
            }
        }
        return null;
    }
}
