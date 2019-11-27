package com.chenjin.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 因为线程池大小为3，每个任务输出index后sleep 1秒，所以每1秒打印3个数字。
 */
public class NewFixedThreadPoolDemo {
    public static void main(String[] args) {

        // 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
        final ExecutorService newCachedThreadPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    System.out.println(Thread.currentThread().getName() + "---" + "i:" + index);
                }
            });
        }
        //手动关闭线程池
        newCachedThreadPool.shutdown();

    }
}
