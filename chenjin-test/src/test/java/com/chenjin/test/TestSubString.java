package com.chenjin.test;

import org.junit.Test;

public class TestSubString {
    @Test
    public  void getMaxCode() {
        String code="232";
        if (code == null) {
            code = "10000001";
        } else {
            code = Integer.toString(Integer.valueOf(code) + 1);
            for (int i = code.length(); i < 8; i++) {
                code = "0" + code;
            }
            if(code.indexOf("0")==0){
                code=code.substring(1);
                code="1"+code;
            }
        }
        System.out.println(code);
    }
}
