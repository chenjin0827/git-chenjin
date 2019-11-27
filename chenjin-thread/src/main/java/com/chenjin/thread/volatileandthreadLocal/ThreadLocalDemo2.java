package com.chenjin.thread.volatileandthreadLocal;

class Res {
    // 生成序列号共享变量
    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            return 0;
        }
    };

    public Integer getNum() {
        int count = threadLocal.get() + 1;
        threadLocal.set(count);
        return count;
    }
}

public class ThreadLocalDemo2 extends Thread {
    private Res res;

    public ThreadLocalDemo2(Res res) {
        this.res = res;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(Thread.currentThread().getName() + "---" + "i---" + i + "--num:" + res.getNum());
        }

    }

    public static void main(String[] args) {
        Res res = new Res();
        ThreadLocalDemo2 threadLocaDemo1 = new ThreadLocalDemo2(res);
        ThreadLocalDemo2 ThreadLocalDemo2 = new ThreadLocalDemo2(res);
        ThreadLocalDemo2 threadLocaDemo3 = new ThreadLocalDemo2(res);
        threadLocaDemo1.start();
        ThreadLocalDemo2.start();
        threadLocaDemo3.start();
    }
}