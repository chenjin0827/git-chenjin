package com.testThread;

public class TestThread1 {
    private  static  class Thread1 {
        static int a ;
        public Thread1(int a) {
            this.a=a;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public static  void run() {
//            System.out.println(Thread.currentThread().getName());
            System.out.println("a======="+a);
        }
    }
    public static void main(String[] args) {
        Thread1 thread1 = new Thread1(1);
        thread1.run();
        thread1.setA(222);
        TestThread1.Thread1.run();
        Thread1 thread11 = new Thread1(333);
        Thread1.run();
    }
}
