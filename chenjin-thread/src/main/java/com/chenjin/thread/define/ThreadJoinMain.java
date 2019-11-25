package com.chenjin.thread.define;

class ThreadJoin extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 40; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName() + "----" + i);
        }
    }
}

public class ThreadJoinMain {


    public static void main(String[] args) throws InterruptedException {
        ThreadJoin threadJoin1 = new ThreadJoin();
        ThreadJoin threadJoin2 = new ThreadJoin();
        threadJoin1.start();
        /**
         * 让其他线程等待，只有当前线程执行完毕，才会释放资格
         * 如果这里加上时间，意思就是join（500），500毫秒之后不管了，直接执行下面别的逻辑
         */
        threadJoin1.join();

        threadJoin2.start();

    }
}
