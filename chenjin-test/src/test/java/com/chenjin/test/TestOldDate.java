package com.chenjin.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TestOldDate {
    public static void main(String[] args) throws Exception{
        Calendar c = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = simpleDateFormat.parse("2018-01-01 12:00:02");
        int i = c.get(Calendar.YEAR);
        int ii = c.get(Calendar.MONTH);
        int iii = c.get(Calendar.DAY_OF_MONTH);
        int a = c.get(Calendar.HOUR_OF_DAY);
        int b = c.get(Calendar.MINUTE);
        int cc = c.get(Calendar.SECOND);
        System.out.println(i);
        System.out.println(ii);
        System.out.println(iii);
        System.out.println(a);
        System.out.println(b);
        System.out.println(cc);


    }
    public Long getTimeDifference(Date d1, Date d2){
        int exe_time_year = (d2.getYear() - d1.getYear()) * (365*24*60*60);
        int exe_time_month = (d2.getMonth() - d1.getMonth())* (30*24*60*60);
        int exe_time_day = (d2.getDay() - d1.getDay())* (24*60*60);
        int exe_time_hour = (d2.getHours() - d1.getHours())* (60*60);
        int exe_time_minute = (d2.getMinutes() - d1.getMinutes())* (60);
        int exe_time_second = d2.getSeconds() - d1.getSeconds();

        return Long.valueOf((exe_time_day + exe_time_hour + exe_time_minute + exe_time_month + exe_time_second + exe_time_year));
    }
}
