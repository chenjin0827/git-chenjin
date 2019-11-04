package com.chenjin.thread.define;

import java.util.concurrent.Callable;

public class Thread3 implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("我是Thread3，实现了Callable，当前线程名字是===="
                + Thread.currentThread().getName());
        return "Callable调用结果";
    }
}
