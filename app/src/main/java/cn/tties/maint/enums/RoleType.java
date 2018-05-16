package cn.tties.maint.enums;

/**
 *
 */
public enum RoleType {

    MANAGER(1, "管理员"), BUSINESS(2, "市场经理"), MAINT(3, "运维专员"), ENERGY(4, "能效专员");
    private final Integer value;
    private final String info;

    private RoleType(int value, String info) {
        this.value = value;
        this.info = info;
    }

    public Integer getValue() {
        return value;
    }

    public String getInfo() {
        return info;
    }

    public static String getInfo(Integer value) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.getValue().equals(value)) {
                return roleType.getInfo();
            }
        }
        return "";
    }

    public static RoleType getTpye(Integer value) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.getValue().equals(value)) {
                return roleType;
            }
        }
        return null;
    }

    public static Integer getValue(String info) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.getInfo().equals(info)) {
                return roleType.getValue();
            }
        }
        return null;
    }
}
