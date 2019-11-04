package com.chenjin.thread;

public class Thread1 extends Thread{

    @Override
    public void run() {
        System.out.println("我是Thread1，继承了Thread，当前线程名字是===="
                + Thread.currentThread().getName());
    }
}
