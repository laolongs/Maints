package cn.tties.maint.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 额外巡视类型.
 * @author Justin
 *
 */
public enum MeterEquipmentType {
    COLLECT(1, "采集器"),
    AMMETER(2, "电表");

    private int type;
    private String info;

    MeterEquipmentType(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public int getType() {
        return type;
    }
    public String getInfo() {
        return info;
    }
    public static MeterEquipmentType get(int type) {
        for (MeterEquipmentType meterEquipmentType : MeterEquipmentType.values()) {
            if (meterEquipmentType.getType() == type) {
                return meterEquipmentType;
            }
        }
        return null;
    }

    public static String getInfo(Integer value) {
        for (MeterEquipmentType meterEquipmentType : MeterEquipmentType.values()) {
            if (meterEquipmentType.getType() == value) {
                return meterEquipmentType.getInfo();
            }
        }
        return "";
    }

    public static int getValue(String info) {
        for (MeterEquipmentType meterEquipmentType : MeterEquipmentType.values()) {
            if (meterEquipmentType.getInfo().equals(info)) {
                return meterEquipmentType.getType();
            }
        }
        return 0;
    }

    public static List<String> getInfoList() {
        List<String> list = new ArrayList<>();
        for (MeterEquipmentType e : MeterEquipmentType.values()) {
            list.add(e.getInfo());
        }
        return list;
    }

    private static List<Long> AMMETER_TASK_ID_LIST = new ArrayList<>();
    private static List<Long> COLLECT_TASK_ID_LIST = new ArrayList<>();
    static {
        AMMETER_TASK_ID_LIST.add(1440581697615L);
        AMMETER_TASK_ID_LIST.add(1431932095371L);
        AMMETER_TASK_ID_LIST.add(1431932155623L);

        COLLECT_TASK_ID_LIST.add(1440581697615L);
        COLLECT_TASK_ID_LIST.add(1431932095371L);
        COLLECT_TASK_ID_LIST.add(1431932128807L);
        COLLECT_TASK_ID_LIST.add(1431932155623L);
    }
    public static List<Long> getTaskIdList(MeterEquipmentType equipmentType) {
        switch (equipmentType) {
            case AMMETER:
                return AMMETER_TASK_ID_LIST;
            case COLLECT:
                return COLLECT_TASK_ID_LIST;
        }
        return null;
    }
}
