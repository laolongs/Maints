package cn.tties.maint.util;


import java.math.BigDecimal;

public class DoubleUtils {

    private static final int ROUND_HALF_UP_COUNT = 2;

    public static Double div(Double dou1, Double dou2) {
       return  new BigDecimal(dou1 / dou2).setScale(ROUND_HALF_UP_COUNT, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double mul(Double dou1, Double dou2) {
        return  new BigDecimal(dou1 * dou2).setScale(ROUND_HALF_UP_COUNT, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double add(Double dou1, Double dou2) {
        return  new BigDecimal(dou1 + dou2).setScale(ROUND_HALF_UP_COUNT, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double sub(Double dou1, Double dou2) {
        return  new BigDecimal(dou1 - dou2).setScale(ROUND_HALF_UP_COUNT, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}