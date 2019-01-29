package com.chenjin.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestReg {
    public static void main(String[] args) {
        String emailRegEx = "^[a-zA-Z0-9_.-]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z0-9]{2,4}$";
        String email = "bjliyi@tarena.com.cn";
        System.out.println(email.matches(emailRegEx));

        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}$");
        String month = "2018-01";
        Matcher matcher = pattern.matcher(month);
        boolean b = matcher.find();
        System.out.println(b);


    }
}
