package com.chenjin.thread.keyword;

import java.util.concurrent.TimeUnit;

/**
 * 线程休眠辅助工具类
 */
public class SleepTools {

    /**
     * 按秒休眠
     * @param senconds
     */
    public static final void second(int senconds){
        try {
            TimeUnit.SECONDS.sleep(senconds);
        } catch (InterruptedException e) {
            System.out.println("按秒休眠出现异常！");
            e.printStackTrace();
        }
    }

    /**
     * 按毫秒休眠
     * @param ms
     */
    public static final void ms(int ms){
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("按毫秒休眠出现异常！");
            e.printStackTrace();
        }
    }
}
