package cn.tties.maint.enums;

/**
 * 额外巡视类型.
 * @author Justin
 *
 */
public enum OtherPatrolItemType {

    OUTSIDE("室外"),
    ROOM("电房"),
    ENVIRONMENT("附属环境"),
    HEALTH("清洁卫生"),
    TOOL("工器具");

    private String info;

    OtherPatrolItemType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}
