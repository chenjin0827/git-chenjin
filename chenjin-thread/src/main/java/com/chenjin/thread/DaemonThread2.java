package com.chenjin.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 测试守护线程   主线程休眠完了守护线程也跟着终止了
 * 守护线程的意思：和主线程共死的，主线程退出来守护线程就一定会退出
 * finally不能保证一定执行
 * 测试方法：修改本类中的
 */
public class DaemonThread2 extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("进入子线程===");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("异常了===");
            }
            System.out.println(System.currentTimeMillis());
        }
    }


    @Test
    public void testDaemonThread2() throws Exception {
        DaemonThread2 daemonThread2 = new DaemonThread2();
        //设置守护线程一定要放在start之前才能生效
//        daemonThread2.setDaemon(true);
        daemonThread2.start();
        TimeUnit.SECONDS.sleep(2);
//        daemonThread2.join();
        System.out.println("主线程退出");
    }
}