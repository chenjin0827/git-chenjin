package com.chenjin.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
public class TestLastMonth {
    public static void main(String[] args) throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String str =new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
        System.out.println(str);
    }
}
