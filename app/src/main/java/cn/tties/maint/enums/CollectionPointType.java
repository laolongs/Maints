package cn.tties.maint.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */


public enum CollectionPointType {

    COLLECTION_POINT(1, "采集点"),
    TEST_TOOL(2, "测试工具");
    private final Integer value;
    private final String info;

    private CollectionPointType(Integer value, String info) {
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
        for (CollectionPointType marketMessionType : CollectionPointType.values()) {
            if (marketMessionType.getValue().equals(value)) {
                return marketMessionType.getInfo();
            }
        }
        return null;
    }

    public static Integer getValue(String info) {
        for (CollectionPointType marketMessionType : CollectionPointType.values()) {
            if (marketMessionType.getInfo().equals(info)) {
                return marketMessionType.getValue();
            }
        }
        return null;
    }

    public static CollectionPointType getTpye(Integer value) {
        for (CollectionPointType marketMessionType : CollectionPointType.values()) {
            if (marketMessionType.getValue().equals(value)) {
                return marketMessionType;
            }
        }
        return null;
    }

    public static List<String> getInfoList() {
        List<String> list = new ArrayList<>();
        for (CollectionPointType marketOrderKindType : CollectionPointType.values()) {
            list.add(marketOrderKindType.getInfo());
        }
        return list;
    }

    public static List<Integer> getValueList() {
        List<Integer> list = new ArrayList<>();
        for (CollectionPointType marketOrderKindType : CollectionPointType.values()) {
            list.add(marketOrderKindType.getValue());
        }
        return list;
    }
}
