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
     *
     * 1、当前线程id是===6；当前线程名字是==Monitor Ctrl-Break
     *
     2、当前线程id是===5；当前线程名字是==Attach Listener  负责获取当前程序运行时相关的信息，
        内存的印象，线程的栈，类信息的统计，获取系统属性等等
     3、当前线程id是===4；当前线程名字是==Signal Dispatcher  处理发送给虚拟机信号的线程
     4、当前线程id是===3；当前线程名字是==Finalizer  调用对象的finalize方法  ，
     这个方法是对象被回收之前调用的，告诉该对象把最后的临终事情做完，
     如果此阶段人为处理下，也有可能在此阶段重新复活，不被回收掉
     5、当前线程id是===2；当前线程名字是==Reference Handler  负责清除引用的线程
     6、当前线程id是===1；当前线程名字是==main   主程序
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
