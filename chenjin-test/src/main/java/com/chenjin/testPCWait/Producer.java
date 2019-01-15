package com.chenjin.testPCWait;

import java.util.List;
import java.util.Random;

public class Producer implements Runnable {
    private List<PCData> queue;
    private int length;

    public Producer(List<PCData> queue, int length) {
        this.queue = queue;
        this.length = length;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("生产者线程被中断");
                    break;
                }
                Random r = new Random();
                long temp = r.nextInt(100);
                System.out.println(Thread.currentThread().getId() + " 生产了：" + temp);
                PCData data = new PCData();
                data.set(temp);
                synchronized (queue) {
                    if (queue.size() >= length) {
                        queue.notifyAll();
                        queue.wait();
                    } else
                        queue.add(data);
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
