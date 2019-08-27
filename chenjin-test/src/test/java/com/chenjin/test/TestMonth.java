package com.chenjin.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestMonth {


    public static void main(String[] args) throws Exception {
////        String yearMonth = getYearMonth();
////        System.out.println(yearMonth);
//        String d = "2019-11-10";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = sdf.parse(d);
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        System.out.println(cal.get(Calendar.MONTH));
        String yearMonth = getYearMonth();
        System.out.println(yearMonth);
    }


    // 获得上个月月份年份
    private static String getYearMonth() throws Exception{
        Calendar now = Calendar.getInstance();
        String d = "2019-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(d);
        now.setTime(date);
        int month = now.get(Calendar.MONTH);
        String m;
        if (month < 10) {
            m = "0" + month;
        } else {
            m = month + "";
        }
        return now.get(Calendar.YEAR) + "-" + m;
    }
}
