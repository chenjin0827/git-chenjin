package com.chenjin.thread.define;

public class Thread7 {
    class Thread701 extends Thread {
        @Override
        public void run() {
            setName("701线程");
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我是" + getName() + ",循环id为===" + i);
            }

        }
    }

    class Thread702 extends Thread {
        @Override
        public void run() {
            setName("702线程");
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我是" + getName() + ",循环id为===" + i);
            }

        }
    }

    public static void main(String[] args) {
        Thread7 thread7 = new Thread7();
        Thread701 thread701 = thread7.new Thread701();
        thread701.start();
        Thread702 thread702 = thread7.new Thread702();
        thread702.start();

    }
}
