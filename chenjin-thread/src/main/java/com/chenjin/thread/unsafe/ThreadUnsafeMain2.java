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
 */
class ThreadTrain2 implements Runnable {
    //火车票总数
    private int count = 100;
    private Object object = new Object();

    @Override
    public void run() {
        //此处如果在run方法上加synchronized，由于是while循环，知道不满足条件count>0线程才会释放锁，
        //所以加上是不对的，就变成单线程执行了
        while (count > 0) {
            show();
        }
    }

    public  synchronized  void show() {
        //同步代码块 这里的object相当于锁，一样的才能锁住,同步方法就是用的this锁
        //解释下这里为什么还要判断下，两个线程同时到达show()方法，count都为1，那么
        //这里不判断的话，两个线程都会按1执行下去，加的synchronized就形同虚设，
        // 只不过一个线程要等另一个线程先执行完罢了
        if (count > 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - count + 1) + "张票");
            count--;
        }
    }
}

/**
 * 使用同步函数，也叫同步方法
 */
public class ThreadUnsafeMain2 {

    public static void main(String[] args) {
        ThreadTrain2 threadTrain = new ThreadTrain2();
        Thread thread1 = new Thread(threadTrain);
        Thread thread2 = new Thread(threadTrain);
        thread1.start();
        thread2.start();
    }
}
