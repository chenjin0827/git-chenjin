package com.chenjin.common.util;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{
    public static boolean checkDateFMT(String data, int type)
    {
        if (data == null)
            return false;
        try {
            if (type == 1) {
                if (data.length() != 10)
                    return false;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                sdf.parse(data);
                return true;
            }if (type == 2) {
                if (data.length() != 19)
                    return false;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setLenient(false);
                sdf.parse(data);
                return true;
            }if (type == 3) {
                if (data.length() != 7)
                    return false;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                sdf.setLenient(false);
                sdf.parse(data);
                return true;
            }
            return false; } catch (ParseException e) {
        }
        return false;
    }

    public static String to19(String data)
    {
        return data + " 00:00:00";
    }

    public static Date strToDate(String data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (data.length() == 10)
            data = data.substring(0, 10) + " 00:00:00";
        else if (data.length() != 19)
        {
            return null;
        }
        try {
            return sdf.parse(data);
        }
        catch (ParseException localParseException) {
        }
        return null;
    }

    public static String dateToStr(Date date)
    {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getToday() {
        String today = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        today = sdf.format(date);
        return today;
    }

    public static String getYear() {
        String today = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        today = sdf.format(date);
        return today;
    }

    public static String getFirstDayOfMonth(String month)
    {
        Date date = strToDate(month + "-01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        Date firstDayOfMonth = calendar.getTime();
        return dateToStr(firstDayOfMonth).substring(0, 10);
    }

    public static String getLastDayOfMonth(String month)
    {
        Date date = strToDate(month + "-01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        calendar.add(2, 1);
        calendar.add(5, -1);
        Date lastDayOfMonth = calendar.getTime();
        return dateToStr(lastDayOfMonth).substring(0, 10);
    }

    public static String getToday10()
    {
        String today = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        today = sdf.format(date);
        return today;
    }

    public static String getToday19()
    {
        String today = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        today = sdf.format(date);
        return today;
    }

    public static String getToday10(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    public static String getYesterday() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, -1);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String tomorrow = sdf.format(date);
        return tomorrow;
    }
    public static String getYesterday10() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, -1);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrow = sdf.format(date);
        return tomorrow;
    }
    public static String getTomorrow10() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, 1);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrow = sdf.format(date);
        return tomorrow;
    }
    public static String getTomorrow19() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, 1);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tomorrow = sdf.format(date);
        return tomorrow;
    }

    public static Date getAddNumDate(int c, int num) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(c, num);
        date = calendar.getTime();
        return date;
    }

    public static int compareDate(String DATE1, String DATE2)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime())
                return 1;
            if (dt1.getTime() < dt2.getTime()) {
                return -1;
            }
            return 0;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static int compareDate(Date dt1, Date dt2)
    {
        if (dt1.getTime() > dt2.getTime())
            return 1;
        if (dt1.getTime() < dt2.getTime()) {
            return -1;
        }
        return 0;
    }

    public static String getDateAfterMonths(Date date, int months, String format)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(2, c.get(2) - months);
        Date d = c.getTime();
        return new SimpleDateFormat(format).format(d);
    }

    public static Date getSpecifiedDay(Date date, int num)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(5, num);
        return cal.getTime();
    }

    public static String getSpecifiedDayStr(String dateStr, int num)
    {
        Date date = strToDate(dateStr);
        Date newdate = getSpecifiedDay(date, num);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("sdf.format(newdate)=" + sdf.format(newdate));
        return sdf.format(newdate).substring(0, 10);
    }
}