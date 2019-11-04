package com.chenjin.thread;

import org.junit.jupiter.api.Test;

/**
 * 测试守护线程   主线程休眠完了守护线程也跟着终止了
 * 守护线程的意思：和主线程共死的，主线程退出来守护线程就一定会退出
 * finally不能保证一定执行
 */
public class DaemonThread extends Thread {

    @Override
    public void run() {
        try{
            while (!isInterrupted()) {
                System.out.println("当前线程名字是===" + Thread.currentThread().getName()+
                        "当前线程id是==="+Thread.currentThread().getId());
            }
            System.out.println("线程被终止了");
        }finally {
            System.out.println("进入到finally===");
        }

    }

    @Test
    public void testDaemonThread() throws InterruptedException {
        DaemonThread daemonThread = new DaemonThread();
        //设置守护线程一定要放在start之前才能生效
//        daemonThread.setDaemon(true);
        daemonThread.start();
        Thread.sleep(5);
        daemonThread.interrupt();//不中断测不出来，相当于while一直是true，
        // 没执行玩，就不会打印finally
    }
}
