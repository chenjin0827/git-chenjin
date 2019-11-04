package com.chenjin.thread;

/**
 * 用于线程中断异常测试
 */
public class Thread6 extends Thread {

    @Override
    public void run() {

        while (!isInterrupted()) {
            String name = Thread.currentThread().getName();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("中断标志位是==="+isInterrupted());
                e.printStackTrace();
            }
            System.out.println("我是Thread6，继承了Thread，当前线程名字是===="
                    + name+" is run！");
        }
        System.out.println("线程被中断了");

    }
}
