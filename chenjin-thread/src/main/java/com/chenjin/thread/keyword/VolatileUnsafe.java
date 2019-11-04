package com.chenjin.thread.keyword;

/**
 * 测试Volatile  一个线程写，多个线程读，效率高，但是线程不安全
 */
public class VolatileUnsafe {

    private static class VolatileVar implements Runnable {

        private volatile int a = 0;


        @Override
        public void run() {
            a = a + 1;
            System.out.println("当前线程是==" + Thread.currentThread().getName() + "" +
                    "age:=" + a);
            SleepTools.ms(100);
            a = a + 1;
            System.out.println("现在线程是==" + Thread.currentThread().getName() + "" +
                    "age:=" + a);
        }
    }

    public static void main(String[] args) {
        VolatileVar volatileVar = new VolatileVar();
        Thread thread1 = new Thread(volatileVar);
        Thread thread2 = new Thread(volatileVar);
        Thread thread3 = new Thread(volatileVar);
        Thread thread4 = new Thread(volatileVar);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }


}
