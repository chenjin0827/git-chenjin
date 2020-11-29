package com.chenjin.test;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TestString {
    @Test
    public void test01() {
        try {
            String a = "2018-01-01";
            String b = "2020-05-26";
            String[] splitA = a.split("-");
            String[] splitB = b.split("-");
            List<String> list = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.valueOf(splitA[0]), Integer.valueOf(splitA[1]) - 1,
                    Integer.valueOf(splitA[2]) - 1);
            long time = cal.getTime().getTime();
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Integer.valueOf(splitB[0]), Integer.valueOf(splitB[1]) - 1,
                    Integer.valueOf(splitB[2]));
            long i1 = cal2.getTime().getTime() - time;
            System.out.println(i1);
            long x = i1 / (1000 * 60 * 60 * 24);
            for (int i = 0; i < x; i++) {
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH) + 1, 00, 00, 00);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println(sdf.format(cal.getTime()));
                list.add(sdf.format(cal.getTime()).toString());
            }
            System.out.println(list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test02() {
        String str1 = "abc";
        String str2 = new String("abc");
        String str3 = str2.intern();
        System.out.println(str1 == str2);//false  因为str2重新创建了一个对象，所以和str1的引用地址不相等
        System.out.println(str1 == str3);//true str3是通过new String("abc").intern（）方式获取String，由于使用了intern()会先到字符串常量池寻找，str1之前创建过，所以直接将str1的引用指向到str3
        System.out.println(str2 == str3);//false  上面str1和str3地址一致，str2就不等于上面两个了， str2是新创建了一个引用

    }
}



