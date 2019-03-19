package com.chenjin.testPCQueue.common.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateTime
{
    public static final String[] DATE_PATTERNS = { "yyyy-MM", "yyyy/MM", "yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss.S", "yyyy/MM/dd HH:mm" };
    public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String MONTH_FORMAT = "yyyy-MM";
    public static final String FORMAT_SHORT_TIME = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_FULL_SEQ = "yyyy-MM-dd HH:mm:ss.SSS";
    private int daysOfDiff;
    private int hoursOfDiff;
    private int minutesOfDiff;
    private int secondsOfDiff;

    public DateTime()
    {
    }

    private DateTime(int daysOfDiff, int hoursOfDiff, int minutesOfDiff, int secondsOfDiff)
    {
        this.daysOfDiff = daysOfDiff;
        this.hoursOfDiff = hoursOfDiff;
        this.minutesOfDiff = minutesOfDiff;
        this.secondsOfDiff = secondsOfDiff;
    }

    public static Date now()
    {
        return new Date();
    }

    public static String getCurrentDate()
    {
        Calendar cal = Calendar.getInstance();
        String currentDate = DateFormatUtils.format(cal, "yyyy-MM-dd");
        return currentDate;
    }

    public static String getCurrentTime()
    {
        Calendar cal = Calendar.getInstance();
        String currentDate = DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss");
        return currentDate;
    }

    public static String now(String format)
    {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static Date getDate(long millis)
    {
        return new Date(millis);
    }

    public static String getMonthFirstDay()
    {
        Calendar cal = Calendar.getInstance();

        cal.set(5, 1);
        return DateFormatUtils.format(cal, "yyyy-MM-dd");
    }

    public static String getMonthLastDay()
    {
        Calendar cal = Calendar.getInstance();

        cal.set(5, 1);
        cal.add(2, 1);
        cal.add(5, -1);
        return DateFormatUtils.format(cal, "yyyy-MM-dd");
    }

    public static int getCurrentMonthDays()
    {
        Calendar cal = new GregorianCalendar();
        return cal.getActualMaximum(5);
    }

    public static int getSpecifiedMonthDays(String date)
    {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(DateUtils.parseDate(date, new String[] { "yyyy-MM" }));
            return cal.getActualMaximum(5);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return 0;
    }

    public static boolean isLeapYear(int year)
    {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    public static String getWeekDay(Date date)
    {
        return getWeekDay(DateUtils.toCalendar(date));
    }

    public static String getWeekDay(Calendar c)
    {
        if (c == null) {
            return "星期一";
        }
        switch (c.get(7)) {
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
        }
        return "星期日";
    }

    public static int getWeekOfMonth(Date date)
    {
        Calendar cal = DateUtils.toCalendar(date);
        return cal.get(4);
    }

    public static int getWeekOfYear(Date date)
    {
        Calendar cal = DateUtils.toCalendar(date);
        return cal.get(3);
    }

    public static List<String> getDaysListBetweenDates(String begin, String end)
    {
        List dateList = new ArrayList();
        Date d1 = parseDate(begin, "yyyy-MM-dd");
        Date d2 = parseDate(end, "yyyy-MM-dd");
        if (d1.compareTo(d2) > 0)
            return dateList;
        do
        {
            dateList.add(DateFormatUtils.format(d1, "yyyy-MM-dd"));
            d1 = DateUtils.addDays(d1, 1);
        }while (d1.compareTo(d2) <= 0);

        return dateList;
    }

    public static List<String> getMonthsListBetweenDates(String begin, String end)
    {
        List dateList = new ArrayList();
        Date d1 = parseDate(begin, "yyyy-MM-dd");
        Date d2 = parseDate(end, "yyyy-MM-dd");
        if (d1.compareTo(d2) > 0)
            return dateList;
        do
        {
            dateList.add(DateFormatUtils.format(d1, "yyyy-MM"));
            d1 = DateUtils.addMonths(d1, 1);
        }while (d1.compareTo(d2) <= 0);

        return dateList;
    }

    public static Date parseDate(String date)
    {
        try
        {
            return DateUtils.parseDate(date, DATE_PATTERNS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String date, String format)
    {
        try
        {
            return DateUtils.parseDate(date, new String[] { format });
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String format(Date date)
    {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(Date date, String format)
    {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static long getMillis()
    {
        return new Date().getTime();
    }

    public static long getMillis(Date date)
    {
        return date.getTime();
    }

    public static Date computeDays(String date, int amount)
    {
        return computeDays(parseDate(date), amount);
    }

    public static Date computeDays(Date date, int amount)
    {
        return DateUtils.addDays(date, amount);
    }

    public static Date computeYears(String date, int amount)
    {
        return computeYears(parseDate(date), amount);
    }

    public static Date computeYears(Date date, int amount)
    {
        return DateUtils.addYears(date, amount);
    }

    public static Date computeMonths(String date, int amount)
    {
        return computeMonths(parseDate(date), amount);
    }

    public static Date computeMonths(Date date, int amount)
    {
        return DateUtils.addMonths(date, amount);
    }

    public static Date computeWeeks(String date, int amount)
    {
        return DateUtils.addWeeks(parseDate(date), amount);
    }

    public static Date computeWeeks(Date date, int amount)
    {
        return DateUtils.addWeeks(date, amount);
    }

    public static boolean isSameDay(Date date1, Date date2)
    {
        return DateUtils.isSameDay(date1, date2);
    }

    public static boolean isSameInstant(Date date1, Date date2)
    {
        return DateUtils.isSameInstant(date1, date2);
    }

    public static int computeDayOfDiff(Date beginDate, Date endDate)
    {
        return (int)((endDate.getTime() - beginDate.getTime()) / 86400000L);
    }

    public static int computeDayOfDiff(String beginDate, String endDate)
    {
        Date date = parseDate(beginDate);
        Date date2 = parseDate(endDate);
        return computeDayOfDiff(date, date2);
    }

    public static long computeHoursOfDiff(Date beginDate, Date endDate)
    {
        return (endDate.getTime() - beginDate.getTime()) / 3600000L;
    }

    public static long computeHoursOfDiff(String beginDate, String endDate)
    {
        Date date = parseDate(beginDate);
        Date date2 = parseDate(endDate);
        return computeHoursOfDiff(date, date2);
    }

    public static long computeMinutesOfDiff(Date beginDate, Date endDate)
    {
        return (endDate.getTime() - beginDate.getTime()) / 60000L;
    }

    public static long computeMinutesOfDiff(String beginDate, String endDate)
    {
        Date date = parseDate(beginDate);
        Date date2 = parseDate(endDate);
        return computeMinutesOfDiff(date, date2);
    }

    public static DateTime computeDiff(Date beginDate, Date endDate)
    {
        long diff = endDate.getTime() - beginDate.getTime();
        int daysOfDiff = (int)(diff / 86400000L);
        int hoursOfDiff = (int)(diff / 3600000L % 24L);
        int minutesOfDiff = (int)(diff / 60000L % 60L);
        int secondsOfDiff = (int)(diff / 1000L % 60L);
        return new DateTime(daysOfDiff, hoursOfDiff, minutesOfDiff, secondsOfDiff);
    }

    public static DateTime computeDiff(String beginDate, String endDate)
    {
        Date date = parseDate(beginDate);
        Date date2 = parseDate(endDate);
        return computeDiff(date, date2);
    }

    public int getDaysOfDiff()
    {
        return this.daysOfDiff;
    }

    public int getHoursOfDiff()
    {
        return this.hoursOfDiff;
    }

    public int getMinutesOfDiff()
    {
        return this.minutesOfDiff;
    }

    public int getSecondsOfDiff()
    {
        return this.secondsOfDiff;
    }

    public static void main(String[] args) {
        System.out.println(computeDayOfDiff("2015-12-31 10:35:30.0", "2016-1-1 10:00:00.0"));
        System.out.println(computeHoursOfDiff("2015-12-31 10:35:30", "2016-1-1 10:00:00"));
        System.out.println(computeMinutesOfDiff("2015-12-31 10:35:30", "2016-1-1 10:00:00"));

        DateTime t = computeDiff("2015-12-31 10:35:00", "2016-1-1 10:00:00");

        System.out.print(t.getDaysOfDiff() + "天");
        System.out.print(t.getHoursOfDiff() + "小时");
        System.out.print(t.getMinutesOfDiff() + "分");
        System.out.println(t.getSecondsOfDiff() + "秒");
    }
}