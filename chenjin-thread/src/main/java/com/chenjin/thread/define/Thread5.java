package com.chenjin.thread.define;

/**
 * 用于线程中断测试  实现Runnable的测试类
 */
public class Thread5 implements Runnable {

    @Override
    public void run() {
        /**
         * 此处while里面如果方true ，就算主程序说了interrupt，子程序也不会被中断
         * 中断必须要在子程序中做好处理
         * 和继承Thread不一样的地方是  runnable里面没有isInterrupted 方法，所以直接可以用Thread里面的
         */
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("我是Thread5，实现了Runnable，当前线程名字是===="
                    + Thread.currentThread().getName()+" is run！");
        }
        System.out.println("线程被中断了");

    }
}
