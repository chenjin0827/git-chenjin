package com.chenjin.thread.define;

public class Thread2 implements Runnable {


    @Override
    public void run() {
        System.out.println("我是Thread2，实现了Runnable，当前线程名字是===="
                + Thread.currentThread().getName());
    }
}
