package com.chenjin.javastandard.cap1;

import  org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * 会变的常量 代码中应该保证常量就是常量，不能被改变
 */
interface Const {
    int RAND_CONST = new Random().nextInt(10);
}

public class Cap1Test {
    @Test
    //本例中容易让人以为最后输出结果为22，但是结果是2
//    注意Long标识类型时L要大写
    public void test1() {
//        long i = 1l;
        long i = 1L;
        System.out.println(i + i);
    }

    //测试常量会变 代码中不可取，避免这种情况 ，务必保证常量在运行期不变
    @Test
    public void test2() {
        System.out.println(Const.RAND_CONST);
    }
}


