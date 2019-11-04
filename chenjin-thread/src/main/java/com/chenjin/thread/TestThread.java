package com.chenjin.thread;

import org.junit.jupiter.api.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestThread {
    /**
     * 测试java本身是多线程的操作
     * 当前线程id是===6；当前线程名字是==Monitor Ctrl-Break
     当前线程id是===5；当前线程名字是==Attach Listener
     当前线程id是===4；当前线程名字是==Signal Dispatcher
     当前线程id是===3；当前线程名字是==Finalizer
     当前线程id是===2；当前线程名字是==Reference Handler
     当前线程id是===1；当前线程名字是==main
     *
     */
    @Test
    public void testThread999() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for(ThreadInfo threadInfo:threadInfos){
            System.out.println("当前线程id是==="+threadInfo.getThreadId()
            +"；当前线程名字是=="+threadInfo.getThreadName());
        }
    }
    @Test
    public void testThread1() {
        Thread1 thread1 = new Thread1();
        thread1.start();
    }
    @Test
    public void testThread2() {
        Thread2 runnable = new Thread2();
        new Thread(runnable).start();
    }

    @Test
    public void testThread3() throws ExecutionException, InterruptedException {
        Thread3 thread3 = new Thread3();
        //实现Callable的线程必须使用FutureTask先包装起来，FutureTask里面实现了runnable接口
        FutureTask<String> ft = new FutureTask<>(thread3);
        new Thread(ft).start();
        //线程执行结束后才能通过FutureTask获取到值
        String s = ft.get();
        System.out.println("Callable返回值=="+s);
    }
}
