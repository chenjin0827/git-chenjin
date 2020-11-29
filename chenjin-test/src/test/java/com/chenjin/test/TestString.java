package com.chenjin.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TestString {

    public static void main(String[] args) {
        try {
            String a="2018-01-01";
            String b="2020-05-26";
            String[] splitA = a.split("-");
            String[] splitB = b.split("-");
            List<String> list = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.valueOf(splitA[0]),Integer.valueOf(splitA[1])-1,
                    Integer.valueOf(splitA[2])-1);
            long time = cal.getTime().getTime();
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Integer.valueOf(splitB[0]),Integer.valueOf(splitB[1])-1,
                    Integer.valueOf(splitB[2]));
            long i1 = cal2.getTime().getTime() - time;
            System.out.println(i1);
            long x = i1 / (1000 * 60 * 60 * 24);
            for (int i = 0; i < x; i++) {
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH) + 1, 00, 00,00);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println(sdf.format(cal.getTime()));
                list.add(sdf.format(cal.getTime()).toString());
            }
            System.out.println(list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }



