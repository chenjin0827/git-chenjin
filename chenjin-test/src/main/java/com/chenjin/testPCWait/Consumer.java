package com.chenjin.testPCWait;


import java.util.List;

public class Consumer implements Runnable {
    private List<PCData> queue;

    public Consumer(List<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("消费者线程被中断");
                    break;
                }
                PCData data;
                synchronized (queue) {
                    if (queue.size() == 0) {
                        queue.wait();
                        queue.notifyAll();
                    }
                    data = queue.remove(0);
                }
                System.out.println(
                        Thread.currentThread().getId() + " 消费了:" + data.get() + " result:" + (data.get() * data.get()));
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
