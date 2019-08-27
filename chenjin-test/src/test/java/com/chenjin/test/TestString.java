package com.chenjin.test;

public class TestString {

    public static void main(String[] args) {
        String maxCode = getMaxCode("100001");
        System.out.println(maxCode);
    }

    public static String getMaxCode(String code) {
        if (code == null) {
            code = "10001";
        } else {
            code = Integer.toString(Integer.valueOf(code) + 1);
            for (int i = code.length(); i < 5; i++) {
                code = "0" + code;
            }
        }
        return code;
    }
}