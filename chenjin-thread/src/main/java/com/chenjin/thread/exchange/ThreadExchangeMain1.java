package com.chenjin.thread.exchange;
class Res {
    public String userSex;
    public String userName;
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
            synchronized (res){
                if (count == 0) {
                    res.userName = "陈进";
                    res.userSex = "男";
                } else {
                    res.userName = "姜颖";
                    res.userSex = "女";
                }
                count = (count + 1) % 2;
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
                System.out.println(res.userName + "--" + res.userSex);
            }
        }
    }
}


public class ThreadExchangeMain1 {
    public static void main(String[] args) {
        Res res = new Res();
        IntThrad intThrad = new IntThrad(res);
        OutThread outThread = new OutThread(res);
        intThrad.start();
        outThread.start();
    }
}
