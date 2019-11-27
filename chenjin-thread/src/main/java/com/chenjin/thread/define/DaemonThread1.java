package com.chenjin.thread.define;

import java.util.concurrent.TimeUnit;

/**
 * 测试守护线程   主线程休眠完了守护线程也跟着终止了
 * 守护线程的意思：和主线程共死的，主线程退出来守护线程就一定会退出
 * finally不能保证一定执行
 * 测试方法：修改本类中的
 * 测试线程的时候一定要使用main函数，用@Test函数是测试不出来的
 * yield和sleep其实也就是时间等待的区别，简单理解为sleep(0)就是yield，yield是让出机会大家重新竞争cpu
 * 进程是所有线程的集合，每一个线程是进程中的一条执行路径。
 * 微波炉例子：sleep是有个时间，就相当于睡觉去了，时间不到我就不会过来竞争cpu资源的，yield就是我热完一个菜了，然后和大家一起重新排队，
 * 重新一起竞争cpu资源，可以简单理解为sleep(0)就相当于yield
 * 使用实现实现Runnable接口好，原因实现了接口还可以继续继承，继承了类不能再继承。
 * 使用多线程的目的是为了提高程序的运行效率
 * 分批发送短信、迅雷多线程下载等都可以使用多线程处理。
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