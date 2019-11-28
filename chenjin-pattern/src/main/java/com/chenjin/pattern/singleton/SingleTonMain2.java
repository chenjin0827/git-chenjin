package com.chenjin.pattern.singleton;

/**
 *饿汉单例
 */
public class SingleTonMain2 {
    private SingleTonMain2() {

    }

    private static SingleTonMain2 singleTonMain2;

    public static SingleTonMain2 getSingleTonMain2() {
        if (null == singleTonMain2) {
            singleTonMain2 = new SingleTonMain2();
        }
        return singleTonMain2;
    }
    public static void main(String[] args) {
        SingleTonMain2 singleTonMain2 = getSingleTonMain2();
        SingleTonMain2 singleTonMain3 = getSingleTonMain2();
        System.out.println(singleTonMain2==singleTonMain3);

    }
}
