package com.chenjin.test;

import java.util.concurrent.CountDownLatch;

public class TestValue {
    public static String key;

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        TestValue.key = key;
    }

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread t1 =new Thread() {
            @Override
            public void run() {
                countDownLatch.countDown();
                setKey("A");
                try {
                    Thread.sleep(1000);
                    System.out.println("A thread-->"+getKey());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 =new Thread() {
            @Override
            public void run() {
                countDownLatch.countDown();
                try {
//                Thread.sleep(3000);
                setKey("B");
                    Thread.sleep(1000);
                    System.out.println("B thread-->"+getKey());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        countDownLatch.await();
        t2.start();
        t1.start();
    }
}