package com.chenjin.thread.stop;

/**
 * 演示终止线程
 */
public class StopThreadDemo1 {
    public static void main(String[] args) {
        StopThread stopThread1 = new StopThread();
        Thread thread1 = new Thread(stopThread1);
        Thread thread2 = new Thread(stopThread1);
        thread1.start();
        thread2.start();
        int i = 0;
        while (true) {
            System.out.println("thread main..");
            if (i == 300) {
                 stopThread1.stopThread();
                thread1.interrupt();
                thread2.interrupt();
                break;
            }
            i++;
        }
    }

}


class StopThread implements Runnable {
    private boolean flag = true;

    @Override
    public synchronized void run() {
        while (flag) {
           try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
                stopThread();
            }
            System.out.println("thread run..");
        }

    }

    public void stopThread() {
        flag = false;
    }
}
