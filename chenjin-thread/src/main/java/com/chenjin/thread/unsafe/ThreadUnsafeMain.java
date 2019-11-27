package com.chenjin.thread.unsafe;

/**
 * 线程安全：
 * 当多个线程同时共享，同一个全局变量或静态变量，做写的操作时，可能会发生数据冲突问题，
 * 也就是线程安全问题。但是做读操作是不会发生数据冲突问题。
 * 本例中产生101张票是因为最后count=1时，两个线程同时进来了，都满足count>0的条件
 * 此时a线程count--，count变为0，然后b线程就显示100-0+1=101，卖出101张票
 * 还会出现两个线程卖同一张票的情况，这是不合理的
 * 线程安全的解决：1、使用synchronized包裹起来代码，也叫同步代码块，只包裹住存在线程安全的代码块
 * 2、使用jdk1.5的并发包lock锁
 * 一个很重要的注意点 synchronized不能直接加到run方法上，不然就相当于一个单线程了
 * 此处就算count使用static修饰也会出现一张票出现多次的情况，线程不安全
 *
 */
class ThreadTrain implements Runnable {
    //火车票总数
    private int count = 100;
    private Object object = new Object();

    @Override
    public void run() {
        //此处如果在run方法上加synchronized，由于是while循环，知道不满足条件count>0线程才会释放锁，
        //所以加上是不对的，就变成单线程执行了
        while (count > 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //同步代码块 这里的object相当于锁，一样的才能锁住
            synchronized (object) {
                if (count > 0) {
                    System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - count + 1) + "张票");
                    count--;
                }
            }

        }
    }
}

public class ThreadUnsafeMain {

    public static void main(String[] args) {
        ThreadTrain threadTrain = new ThreadTrain();
        Thread thread1 = new Thread(threadTrain);
        Thread thread2 = new Thread(threadTrain);
        thread1.start();
        thread2.start();
    }
}
