package com.chenjin.thread.define;

import java.util.concurrent.TimeUnit;

/**
 * 测试守护线程   主线程休眠完了守护线程也跟着终止了
 * 守护线程的意思：和主线程共死的，主线程退出来守护线程就一定会退出
 * finally不能保证一定执行
 * 测试方法：修改本类中的
 */
public class DaemonThread1 extends Thread {

    @Override
    public void run() {
        System.out.println("进入子线程");
        try {
            System.out.println("enter try block");
            //这里如果阻塞时间比主线程长最后就不会进入到finally 因为主线程结束了，守护线程就不管阻塞，跟着结束了 阻塞后面的代码都不会执行，更不会抛异常了
            TimeUnit.SECONDS.sleep(5);
            int a=1/0;
            System.out.println(a);
            System.out.println("执行结束==");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("enter finally block");
        }
    }





    public static void main(String[] args) throws Exception{
        DaemonThread1 daemonThread = new DaemonThread1();
        //设置守护线程一定要放在start之前才能生效
//        daemonThread.setDaemon(true);
        daemonThread.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("主线程结束");
    }
}