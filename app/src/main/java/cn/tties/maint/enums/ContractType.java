package cn.tties.maint.enums;

/**
 *
 */
public enum ContractType {

    EXECUTING(0, "合同中"), UNRENEWED(1, "未续签");
    private final Integer value;
    private final String info;

    private ContractType(int value, String info) {
        this.value = value;
        this.info = info;
    }

    public Integer getValue() {
        return value;
    }

    public String getInfo() {
        return info;
    }

    public static String getInfo(int value) {
        for (ContractType contractType : ContractType.values()) {
            if (contractType.getValue().equals(value)) {
                return contractType.getInfo();
            }
        }
        return null;
    }

    public static ContractType getTpye(Integer value) {
        for (ContractType contractType : ContractType.values()) {
            if (contractType.getValue().equals(value)) {
                return contractType;
            }
        }
        return null;
    }
}
