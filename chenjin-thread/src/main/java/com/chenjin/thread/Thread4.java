package com.chenjin.thread;

/**
 * 用于线程中断测试  继承Thread的测试类
 */
public class Thread4 extends Thread {

    @Override
    public void run() {
        /**
         * 此处while里面如果方true ，就算主程序说了interrupt，子程序也不会被中断
         * 中断必须要在子程序中做好处理
         */
        while (!isInterrupted()) {
            System.out.println("我是Thread4，继承了Thread，当前线程名字是===="
                    + Thread.currentThread().getName()+" is run！");
        }
        System.out.println("线程被中断了");

    }
}
