package com.chenjin.common.util;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Random;

public class NumberUtil
{
    public static String addZeroForNum(String str, int strLength)
    {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);

            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    public static String getRandomNum(int strLength)
    {
        Random rm = new Random();
        double pross = rm.nextDouble();
        String result = pross + "";
        String[] r = result.split("\\.");
        if (r.length > 1)
            result = r[1];
        else {
            result = r[0];
        }
        if (result.length() >= strLength)
            result = result.substring(0, strLength);
        else {
            result = addZeroForNum(result, strLength);
        }
        return result;
    }

    public static Double scale2(Double d)
    {
        BigDecimal bg = new BigDecimal(d.doubleValue());
        return Double.valueOf(bg.setScale(2, 4).doubleValue());
    }

    public static int getPointNum(Double d)
    {
        String s = d + "";
        int position = s.length() - s.indexOf(".") + 1;
        return position;
    }

    public static void main(String[] args)
    {
        System.out.println("==" + getRandomNum(10));
    }
}