package com.chenjin.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestDate {
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        c.add(Calendar.MONTH,1);
        Date y = c.getTime();
        String year = format.format(y);
        System.out.println(year);


        System.out.println(format.format(new Date()));
    }
}
