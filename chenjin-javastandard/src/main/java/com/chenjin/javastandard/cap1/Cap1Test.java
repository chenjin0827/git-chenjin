package com.chenjin.javastandard.cap1;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class Cap1Test {
    @Test
    //本例中容易让人以为最后输出结果为22，但是结果是2
//    注意Long标识类型时L要大写
    public void test1() {
//        long i = 1l;
        long i = 1L;
        System.out.println(i + i);
    }
}


