package com.chenjin.thread.stop;

/**
 * 演示终止线程
 */
public class StopThreadDemo2 {
    public static void main(String[] args) throws InterruptedException {
        StopThread2 stopThread2 = new StopThread2();
        stopThread2.start();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(2000);
            System.out.println("main Thread----");
        }
        stopThread2.interrupt();
    }

}


class StopThread2 extends Thread {
    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //这里抛出InterruptedException异常，如果不作处理，线程是无法正常被终止的，应为
                // 中断标识位被重置为false了，所以需要在catch里面重新调用interrupt方法即可
                e.printStackTrace();
                interrupt();
            }
            System.out.println(getName() + "子线程-----");


        }
    }
}


