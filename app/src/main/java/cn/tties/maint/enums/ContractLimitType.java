package cn.tties.maint.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public enum ContractLimitType {

    SIX_MONTH(6, "六个月"), TWELVE_MONTH(12, "十二个月");
    private final Integer value;
    private final String info;

    private ContractLimitType(Integer value, String info) {
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
        for (ContractLimitType marketMessionType : ContractLimitType.values()) {
            if (marketMessionType.getValue().equals(value)) {
                return marketMessionType.getInfo();
            }
        }
        return null;
    }

    public static ContractLimitType getTpye(Integer value) {
        for (ContractLimitType marketMessionType : ContractLimitType.values()) {
            if (marketMessionType.getValue().equals(value)) {
                return marketMessionType;
            }
        }
        return null;
    }

    public static List<String> getInfoList() {
        List<String> list = new ArrayList<>();
        for (ContractLimitType marketMessionType : ContractLimitType.values()) {
            list.add(marketMessionType.getInfo());
        }
        return list;
    }

    public static List<Integer> getValueList() {
        List<Integer> list = new ArrayList<>();
        for (ContractLimitType marketMessionType : ContractLimitType.values()) {
            list.add(marketMessionType.getValue());
        }
        return list;
    }
}
