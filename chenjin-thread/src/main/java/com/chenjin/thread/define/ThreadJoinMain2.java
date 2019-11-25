package com.chenjin.thread.define;

/**
 * 面试题 T1、T2、T3三个线程 ，T1执行完后执行T2，T2执行完之后执行T3
 */

public class ThreadJoinMain2 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "----" + i);
                }
            }
        }, "t1");
        t1.start();
        t1.join();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "----" + i);
                }
            }
        }, "t2");
        t2.start();
        t2.join();
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "----" + i);
                }
            }
        }, "t3");
        t3.start();
        t3.join();
    }


}
