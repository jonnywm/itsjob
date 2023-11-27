package br.com.ist.job.utils;

import java.math.BigDecimal;

/**
 *
 * @author jonny
 */
public class Utils {

    public static boolean isOutRanged(BigDecimal value, BigDecimal min, BigDecimal max) {
        return isLessThan(value, min) || isGreaterThan(value, max);
    }

    public static boolean isLessThan(BigDecimal value, BigDecimal target) {
        return value.compareTo(target) < 0;
    }

    public static boolean isGreaterThan(BigDecimal value, BigDecimal target) {
        return value.compareTo(target) > 0;
    }

    public static boolean isGreaterThanEquals(BigDecimal value, BigDecimal target) {
        return value.compareTo(target) >= 0;
    }

    public static boolean isLessThanEquals(BigDecimal value, BigDecimal target) {
        return value.compareTo(target) <= 0;
    }

    private Utils() {
    }

}
