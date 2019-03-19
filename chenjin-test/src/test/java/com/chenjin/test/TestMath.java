package com.chenjin.test;

public class TestMath {
    public static void main(String[] args) {
//        double aa=214608;
//        System.out.println(aa/10000);
//        System.out.println(Math.ceil(aa/10000));//21.4608
////        double floor = Math.floor(a / 10000);
////        System.out.println(floor);
//        double a=35;
//        double b=20;
//        double c = a/b;
//        System.out.println(c); //1.75
//        System.out.println(Math.ceil(c)); //2.0
//        System.out.println(Math.floor(c)); //1.0
        int count = 214608;
        int times = (int) Math.ceil((double) count / 10000);
        System.out.println(times);
    }
}
