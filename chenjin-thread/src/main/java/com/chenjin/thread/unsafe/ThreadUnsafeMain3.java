package com.chenjin.thread.unsafe;

class ThreadTrain3 implements Runnable {
    // 这是货票总票数,多个线程会同时共享资源
    private int trainCount = 100;
    //判断使用哪个锁
    public boolean flag = true;
    private Object mutex = new Object();

    @Override
    public void run() {
        if (flag) {
            while (true) {
                //此处使用this就和同步块一致，切换this和mutex得以证明
                synchronized (this) {
                    if (trainCount > 0) {
                        try {
                            Thread.sleep(40);
                        } catch (Exception e) {

                        }
                        System.out.println(Thread.currentThread().getName() + ",出售 第" + (100 - trainCount + 1) + "张票.");
                        trainCount--;
                    }
                }
            }
        } else {
            while (true) {
                sale();
            }
        }
    }


    public synchronized void sale() {
        if (trainCount > 0) {
            try {
                Thread.sleep(40);
            } catch (Exception e) {

            }
            System.out.println(Thread.currentThread().getName() + ",出售 第" + (100 - trainCount + 1) + "张票.");
            trainCount--;
        }
    }
}

public class ThreadUnsafeMain3 {

    public static void main(String[] args) throws InterruptedException {

        ThreadTrain3 threadTrain3 = new ThreadTrain3(); // 定义 一个实例
        Thread thread1 = new Thread(threadTrain3, "一号窗口");
        Thread thread2 = new Thread(threadTrain3, "二号窗口");
        thread1.start();
        Thread.sleep(40);
        threadTrain3.flag = false;
        thread2.start();
    }

}

