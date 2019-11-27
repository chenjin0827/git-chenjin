package com.chenjin.thread.volatileandthreadLocal;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用count在多线程下操作count++是不具备原子性的
 * 可以使用jdk1.5之后提供的并发包下的原子类
 */
public class AtomicIntegerDemo1 {
    public static void main(String[] args) throws InterruptedException {
        // 初始化10个线程
        VolatileNoAtomic[] volatileNoAtomic = new VolatileNoAtomic[10];
        for (int i = 0; i < 10; i++) {
            volatileNoAtomic[i] = new VolatileNoAtomic();
        }
        for (int i = 0; i < volatileNoAtomic.length; i++) {
            volatileNoAtomic[i].start();
        }
    }
}

class VolatileNoAtomic extends Thread {
    //volatile static int count = 0;
    volatile static int count = 0;//此处使用volatile只能保证可见性，还是无法保证原子性
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            //等同于i++
            atomicInteger.incrementAndGet();
            count++;
        }
        System.out.println("atomicInteger=" + atomicInteger);
        System.out.println("count=" + count);
    }
}

