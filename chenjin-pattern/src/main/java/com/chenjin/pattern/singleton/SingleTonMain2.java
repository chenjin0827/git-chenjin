package com.chenjin.pattern.singleton;

/**
 * 饿汉单例(懒汉)，存在线程安全问题,
 * 可以使用synchronized同步锁实现多线程下也线程安全
 * 效率没有饱汉式高，因为饱汉式是类初始化的
 */
public class SingleTonMain2 {
    private SingleTonMain2() {
    }

    private static SingleTonMain2 singleTonMain2;

    public static SingleTonMain2 getSingleTonMain2() {
        if (null == singleTonMain2) {
            synchronized (SingleTonMain2.class) {
                singleTonMain2 = new SingleTonMain2();
            }
        }
        return singleTonMain2;
    }

    public static void main(String[] args) {
        SingleTonMain2 singleTonMain2 = getSingleTonMain2();
        SingleTonMain2 singleTonMain3 = getSingleTonMain2();
        System.out.println(singleTonMain2 == singleTonMain3);

    }
}
