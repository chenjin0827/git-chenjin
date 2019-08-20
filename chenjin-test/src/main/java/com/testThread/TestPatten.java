package com.testThread;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPatten {
    public static void main(String[] args) {
        String phone="17688830351";
        phone="13123456765";
         Pattern pattern2 = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher matcher2 = pattern2.matcher(phone.trim());
        System.out.println("2222222222222"+matcher2.matches());
        if(!matcher2.matches()){
            Pattern pattern3 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");
            Matcher matcher3 = pattern3.matcher(phone.trim());
            System.out.println("333333"+matcher3.matches());
        }
    }
}
