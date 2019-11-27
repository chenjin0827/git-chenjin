package com.chenjin.thread.stop;

/**
 * 演示终止线程
 */
public class StopThreadDemo2 {
    public static void main(String[] args) {
        StopThread2 stopThread2 = new StopThread2();
        stopThread2.start();

    }

}


class StopThread2 extends Thread {
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    private boolean flag=true;
    @Override
    public void run() {
        while(flag){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                interrupt();
            }
            System.out.println(getName() + "-----" );
        }
    }
}


