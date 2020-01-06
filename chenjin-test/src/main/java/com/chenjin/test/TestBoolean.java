package com.chenjin.test;

public class TestBoolean {
    public static void main(String[] args) {
        Boolean b1 = new Boolean(true);
        Boolean b2 = new Boolean(true);
        System.out.println(b1|b2); //true
        System.out.println(b1&&b2);//true
        System.out.println(b1&b2);//true
        System.out.println(b1.equals(b2));//true
        System.out.println(b1==b2);//false
        System.out.println(b1||b2);//true
    }
}
