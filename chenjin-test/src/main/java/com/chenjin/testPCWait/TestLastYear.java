package com.chenjin.testPCWait;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestLastYear {

    public static void main(String[] args) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy");
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        c.add(Calendar.YEAR, -1);
//        Date y = c.getTime();
//        String year = format.format(y);
//        System.out.println("过去一年："+year);
        String today = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        today = sdf.format(date);
        System.out.println(today);
        System.out.println(today.substring(0,7));
    }
}
