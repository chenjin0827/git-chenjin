package com.chenjin.thread.exchange;


import java.util.concurrent.locks.*;
/**
 * 多线程之间的通讯
 * 演示手动开锁、解锁
 * ReentrantLock 重入锁
 */
public class ReentrantLockMain1 {
    public static void main(String[] args) {
        Res2 res2 = new Res2();
        IntThrad2 intThrad = new IntThrad2(res2);
        OutThread2 outThread = new OutThread2(res2);
        intThrad.start();
        outThread.start();
    }
}
class Res2 {

    public String userSex;
    public String userName;
    public Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    //flag为false表示out未读取值，为true表示in线程已经完成赋值
    public boolean flag = false;
}

class IntThrad2 extends Thread {
    private Res2 res2;

    public IntThrad2(Res2 res2) {
        this.res2 = res2;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            try {
                res2.lock.lock();
                if (res2.flag) {//为true说明当前res已经赋值了，当前线程等待
                    //线程从运行状态变为休眠状态,wait可以释放锁，sleep不释放锁
                    try {
                        res2.condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (count == 0) {
                    res2.userName = "陈进";
                    res2.userSex = "男";
                } else {
                    res2.userName = "姜颖";
                    res2.userSex = "女";
                }
                count = (count + 1) % 2;
                res2.flag = true;
                res2.condition.signal();//唤醒
                ;//写完后唤醒另外的线程

            } catch (Exception e) {

            } finally {
                //放到finally中就算程序出异常也能正常释放锁
                res2.lock.unlock();
            }

        }
    }
}

class OutThread2 extends Thread {
    private Res2 res2;

    public OutThread2(Res2 res2) {
        this.res2 = res2;
    }

    @Override
    public void run() {
        while (true) {
            try {
                res2.lock.lock();
                if (!res2.flag) {
                    try {
                        res2.condition.await();
                        //如果没有值那么等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(res2.userName + "--" + res2.userSex);
                res2.flag = false;//消费完之后重置为false
                res2.condition.signal();

            }catch (Exception e){

            }finally {
                //放到finally中就算程序出异常也能正常释放锁
                res2.lock.unlock();
            }

        }
    }
}


