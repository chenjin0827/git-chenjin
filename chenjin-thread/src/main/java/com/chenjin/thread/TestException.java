package com.chenjin.thread;

import org.junit.jupiter.api.Test;

public class TestException {
    @Test
    public void TestException() {
        try {
            int a = 1 / 1;
            System.out.println("我是a===="+a);
        } finally {
            //不论是否有异常都会执行finally
            System.out.println("....finally");
        }
    }
}
