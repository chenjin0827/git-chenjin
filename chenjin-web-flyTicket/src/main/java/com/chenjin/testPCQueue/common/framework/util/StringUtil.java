package com.chenjin.testPCQueue.common.framework.util;

import java.io.PrintStream;
import java.text.DecimalFormat;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class StringUtil
{
    public static final String FILE_SEPARATOR = SystemUtils.FILE_SEPARATOR;

    public static final String FILE_ENCODING = SystemUtils.FILE_ENCODING;

    public static final String JAVA_VM_VERSION = SystemUtils.JAVA_VM_VERSION;

    /** @deprecated */
    public static String randomString(int count)
    {
        return RandomStringUtils.random(count);
    }

    public static String randomString(int count, String chars)
    {
        return RandomStringUtils.random(count, chars);
    }

    public static String randomString(int count, boolean letters, boolean number)
    {
        return RandomStringUtils.random(count, letters, number);
    }

    public static String deleteWhitespace(String chars)
    {
        return StringUtils.deleteWhitespace(chars);
    }

    public static boolean isAssignable(Class<?> cls, Class<?> toCls)
    {
        return ClassUtils.isAssignable(cls, toCls);
    }

    public static boolean isDigits(String chars)
    {
        return NumberUtils.isDigits(chars);
    }

    public static boolean isNumber(String chars)
    {
        return NumberUtils.isNumber(chars);
    }

    public static double max(double[] args)
    {
        return NumberUtils.max(args);
    }

    public static double min(double[] args)
    {
        return NumberUtils.min(args);
    }

    public static double max(int[] args)
    {
        return NumberUtils.max(args);
    }

    public static double min(int[] args)
    {
        return NumberUtils.min(args);
    }

    public static String numberFormat(Object number, String pattern)
    {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }

    public static void main(String[] args)
    {
        System.out.println(numberFormat(Integer.valueOf(1111111), "#,###.000"));
    }
}