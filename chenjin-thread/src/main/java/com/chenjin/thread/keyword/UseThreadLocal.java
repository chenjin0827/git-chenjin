package com.chenjin.thread.keyword;

public class UseThreadLocal {
    static ThreadLocal<Integer> t = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };


    public void startThreadArray() {

        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new TestThread(i));
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

    }

    public static class TestThread implements Runnable {
        int id;

        public TestThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "is running!");
            Integer integer = t.get();
            System.out.println(integer);
            integer = integer + id;
            t.set(integer);
            System.out.println(Thread.currentThread().getName() + "is end!");
            System.out.println("当前threadLocal的值为："+t.get());

        }
    }

    public static void main(String[] args) {

        UseThreadLocal useThreadLocal = new UseThreadLocal();
        useThreadLocal.startThreadArray();
    }
}
