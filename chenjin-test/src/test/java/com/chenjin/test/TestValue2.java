package com.chenjin.test;

public class TestValue2 {
    private static ThreadLocal<String> key = new ThreadLocal<>();

    public static String getKey() {
        return key.get();
    }
    public static void setKey(String str) {
        key.set(str);
    }

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                setKey("A");
                try {
                    Thread.sleep(1000);
                    System.out.println("A thread-->"+getKey());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                setKey("B");
                try {
                    Thread.sleep(1000);
                    System.out.println("B thread-->"+getKey());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}