package com.chenjin.thread;

import java.util.Date;

/**
 * 测试用户线程和守护线程的区别
 * @author sxf
 *
 */
public class DaemonThread3 {

    public static void main(String[] args) {
        //测试守护线程
        testDaemonThread();
        //测试用户线程
//        testUserThread();
        //主线程休眠5秒后退出
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("DaemonThreadTest.main()主线程退出");
    }

    /**
     * 测试守护线程结果：
     *I'm running at Tue Dec 26 16:37:32 CST 2017, I am daemon
     *I'm running at Tue Dec 26 16:37:33 CST 2017, I am daemon
     *I'm running at Tue Dec 26 16:37:34 CST 2017, I am daemon
     *I'm running at Tue Dec 26 16:37:35 CST 2017, I am daemon
     *I'm running at Tue Dec 26 16:37:36 CST 2017, I am daemon
     *DaemonThreadTest.main()主线程退出
     */
    public static void testDaemonThread(){
        DaemonThead t=new DaemonThead();
        Thread thread=new Thread(t);
        thread.setDaemon(true);//将当前线程设置为守护线程
        thread.start();//启动当前线程
    }


    /**
     * 测试用户线程结果：
     * I'm running at Tue Dec 26 16:41:37 CST 2017, I am not daemon
     *I'm running at Tue Dec 26 16:41:38 CST 2017, I am not daemon
     *I'm running at Tue Dec 26 16:41:39 CST 2017, I am not daemon
     *I'm running at Tue Dec 26 16:41:40 CST 2017, I am not daemon
     *I'm running at Tue Dec 26 16:41:41 CST 2017, I am not daemon
     *DaemonThreadTest.main()主线程退出
     *I'm running at Tue Dec 26 16:41:42 CST 2017, I am not daemon
     *I'm running at Tue Dec 26 16:41:43 CST 2017, I am not daemon
     *I'm running at Tue Dec 26 16:41:44 CST 2017, I am not daemon
     *I'm running at Tue Dec 26 16:41:45 CST 2017, I am not daemon
     *I'm running at Tue Dec 26 16:41:46 CST 2017, I am not daemon
     *I'm running at Tue Dec 26 16:41:47 CST 2017, I am not daemon
     */
    public static void testUserThread(){
        UserThead u=new UserThead();
        Thread userThread=new Thread(u);
        userThread.setDaemon(false);//可不用设置，默认为用户线程
        userThread.start();
    }
}


/**
 * 守护线程
 * @author sxf
 *
 */
class DaemonThead implements Runnable{

    @Override
    public void run() {
        String daemon = (Thread.currentThread().isDaemon() ? "daemon": "not daemon");
        while (true) {
            System.out.println("I'm running at " + new Date() + ", I am " + daemon);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("I was interrupt, I am " + daemon);
            }
        }
    }

}

/**
 * 用户线程
 * @author sxf
 *
 */
class UserThead implements Runnable{

    @Override
    public void run() {
        String daemon = (Thread.currentThread().isDaemon() ? "daemon": "not daemon");
        while (true) {
            System.out.println("I'm running at " + new Date() + ", I am " + daemon);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("I was interrupt, I am " + daemon);
            }
        }

    }

}