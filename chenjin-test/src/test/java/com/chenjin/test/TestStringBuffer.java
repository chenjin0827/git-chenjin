package com.chenjin.test;

public class TestStringBuffer {
    public static void main(String[] args) {

        StringBuffer stringBuffer = new StringBuffer("1234");
        System.out.println(stringBuffer);
        stringBuffer.setLength(0);
        System.out.println("清空后"+stringBuffer);
        stringBuffer.append("33424365");
        System.out.println("清空后后面"+stringBuffer);
    }
}