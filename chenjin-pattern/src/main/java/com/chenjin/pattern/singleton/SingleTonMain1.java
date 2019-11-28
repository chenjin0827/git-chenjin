package com.chenjin.pattern.singleton;

/**
 * 饱汉单例
 */
public class SingleTonMain1 {
    private SingleTonMain1() {

    }
    private static SingleTonMain1 singleTonMain1 =new SingleTonMain1();
    public static SingleTonMain1 getSingleTonMain1() {
        return singleTonMain1;
    }

    public static void main(String[] args) {
        SingleTonMain1 singleTonMain1 = getSingleTonMain1();
        SingleTonMain1 singleTonMain2 = getSingleTonMain1();
        System.out.println(singleTonMain1==singleTonMain2);

    }
}
