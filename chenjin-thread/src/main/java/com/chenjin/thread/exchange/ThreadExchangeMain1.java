package com.chenjin.thread.exchange;

class Res {
    public String userSex;
    public String userName;
    //flag为false表示out未读取值，为true表示in线程已经完成赋值
    public boolean flag = false;
}

class IntThrad extends Thread {
    private Res res;

    public IntThrad(Res res) {
        this.res = res;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            synchronized (res) {
                if (res.flag) {//为true说明当前res已经赋值了，当前线程等待
                    //线程从运行状态变为休眠状态,wait可以释放锁，sleep不释放锁
                    try {
                        res.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (count == 0) {
                    res.userName = "陈进";
                    res.userSex = "男";
                } else {
                    res.userName = "姜颖";
                    res.userSex = "女";
                }
                count = (count + 1) % 2;
                res.flag = true;
                res.notify();//写完后唤醒另外的线程
            }
        }
    }
}

class OutThread extends Thread {
    private Res res;

    public OutThread(Res res) {
        this.res = res;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (res) {
                if (!res.flag) {
                    try {
                        res.wait();//如果没有值那么等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(res.userName + "--" + res.userSex);
                res.flag = false;//消费完之后重置为false
                res.notify();
            }
        }
    }
}

/**
 * 多线程之间的通讯 wait和notify的理解
 */
public class ThreadExchangeMain1 {
    public static void main(String[] args) {
        Res res = new Res();
        IntThrad intThrad = new IntThrad(res);
        OutThread outThread = new OutThread(res);
        intThrad.start();
        outThread.start();
    }
}
