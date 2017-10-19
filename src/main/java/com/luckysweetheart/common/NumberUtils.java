package com.luckysweetheart.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangxin on 2017/10/19.
 */
public class NumberUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(NumberUtils.class);

    private static final Pattern PATTERN = Pattern.compile("[0-9]+");

    public static Integer getInteger(final String str) {
        return getInteger(str, null);
    }

    public static Integer getInteger(final String str, final Integer defaultValue) {
        if (!isNumber(str)) {
            return defaultValue;
        }
        Integer value = null;
        try {
            value = Integer.valueOf(str);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            value = defaultValue;
        }
        return value;
    }

    public static Long getLong(final String str) {
        return getLong(str, null);
    }

    public static Long getLong(final String str, final Long defaultValue) {
        if (!isNumber(str)) {
            return defaultValue;
        }
        Long value = null;
        try {
            value = Long.valueOf(str);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            value = defaultValue;
        }
        return value;
    }

    public static boolean isNumber(String number) {
        if (StringUtils.isBlank(number)) {
            return false;
        }
        Matcher isNum = PATTERN.matcher(number);
        return isNum.matches();
    }

    public static Integer[] stringToInteger(String str, String regex) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        if (regex != null && regex.length() > 0) {
            try {
                String[] splits = str.split(regex);
                return stringToInteger(splits);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static Long[] stringToLong(String str, String regex) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        if (regex != null && regex.length() > 0) {
            try {
                String[] splits = str.split(regex);
                return stringToLong(splits);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static Integer[] stringToInteger(String[] str) {
        if (str == null || str.length == 0) {
            return null;
        }
        Integer[] l = new Integer[str.length];
        for (int i = 0; i < str.length; i++) {
            l[i] = getInteger(str[i]);
        }
        return l;
    }

    public static Long[] stringToLong(String[] str) {
        if (str == null || str.length == 0) {
            return null;
        }
        Long[] l = new Long[str.length];
        for (int i = 0; i < str.length; i++) {
            l[i] = getLong(str[i]);
        }
        return l;
    }

    public static boolean equals(Number a, Number b) {
        return a == null && b == null || a != null && b != null && a.doubleValue() == b.doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(stringToLong("1 2 3 4", " ")));
    }

}
