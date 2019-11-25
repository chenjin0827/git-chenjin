package com.chenjin.thread.unsafe;

class ThreadTrain4 implements Runnable {
    // 这是货票总票数,多个线程会同时共享资源
    private static int trainCount = 100;
    //判断使用哪个锁
    public boolean flag = true;

    @Override
    public void run() {
        if (flag) {
            while (true) {
                //此处使用this就和同步块一致，切换this和mutex得以证明
                synchronized (ThreadTrain4.class) {
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


    public static synchronized void sale() {
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

/**
 * 测试静态同步锁
 */
public class ThreadUnsafeMain4 {

    public static void main(String[] args) throws InterruptedException {

        ThreadTrain4 ThreadTrain4 = new ThreadTrain4(); // 定义 一个实例
        Thread thread1 = new Thread(ThreadTrain4, "一号窗口");
        Thread thread2 = new Thread(ThreadTrain4, "二号窗口");
        thread1.start();
        Thread.sleep(40);
        ThreadTrain4.flag = false;
        thread2.start();
    }

}

