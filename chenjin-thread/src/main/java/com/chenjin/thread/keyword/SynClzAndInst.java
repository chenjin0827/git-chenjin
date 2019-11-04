package com.chenjin.thread.keyword;

/**
 * 测试对象锁和类锁
 */
public class SynClzAndInst {
    private static class SynClass extends Thread {
        @Override
        public void run() {
            System.out.println("SynClass is running!");
            synClass();
        }
    }

    private static class InstanceSyn extends Thread {
        private SynClzAndInst synClzAndInst;

        public InstanceSyn(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("InstanceSyn is running!");
            synClzAndInst.instance();
        }
    }

    private static class InstanceSyn2 extends Thread {
        private SynClzAndInst synClzAndInst;

        public InstanceSyn2(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("InstanceSyn2 is running!");
            synClzAndInst.instance2();
        }
    }

    /**
     * synchronized修饰在方法上，表示对象加锁，每个不一样的对象一把锁
     */
    private synchronized void instance() {
        SleepTools.second(3);
        System.out.println("instancesyn正在运行！");
        SleepTools.second(3);
        System.out.println("instancesyn正在运行！");
    }

    private synchronized void instance2() {
        SleepTools.second(3);
        System.out.println("instancesyn2正在运行！");
        SleepTools.second(3);
        System.out.println("instancesyn2正在运行！");
    }

    /**
     * static synchronized  这样修饰锁是绑定在类上的，一个class在jvm中只可能存在唯一一份
     */
    private static synchronized void synClass() {
        SleepTools.second(1);
        System.out.println("synClass is running!");
        SleepTools.second(1);
        System.out.println("synClass is end!");
    }

    public static void main(String[] args) {
/**
 * 测试对象锁
 */
      /*  SynClzAndInst synClzAndInst1 = new SynClzAndInst();
        SynClzAndInst synClzAndInst2 = new SynClzAndInst();
        Thread thread1 = new Thread(new InstanceSyn(synClzAndInst2));
        Thread thread2 = new Thread(new InstanceSyn2(synClzAndInst2));
        thread1.start();
        thread2.start();*/
        /**
         * 测试类锁  spring的注入是单例，使用类锁才行，不然每个对象都是new出来的锁不住的
         */

        SynClass synClass = new SynClass();
        synClass.start();

    }
}
