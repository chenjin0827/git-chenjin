package com.chenjin.thread;

/**
 * 用于线程中断异常测试
 */
public class Thread6 extends Thread {

    @Override
    public void run() {
//不建议手动标识来控制线程中断  比如boolean flag false;  一般情况能正常工作，
// 但是当while里面有阻塞方法如sleep、wait等标识就没用了，阻塞在那里，标识是拿不到的
        while (!isInterrupted()) {
            String name = Thread.currentThread().getName();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //此处捕获InterruptedException异常后不处理中断标志位默认会置为false，所以需要手动标识中断位才能正常中断线程
                interrupt();
                System.out.println("中断标志位是==="+isInterrupted());
                e.printStackTrace();
            }
            System.out.println("我是Thread6，继承了Thread，当前线程名字是===="
                    + name+" is run！");
        }
        System.out.println("线程被中断了");

    }
}
