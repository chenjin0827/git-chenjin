package com.chenjin.pattern.singleton;

/**
 * 饱汉单例 因为绑定在类上，所以天生线程安全
 */
public class SingleTonMain1 {
    private SingleTonMain1() {

    }
    private static final SingleTonMain1 singleTonMain1 =new SingleTonMain1();
    public static SingleTonMain1 getSingleTonMain1() {
        return singleTonMain1;
    }

    public static void main(String[] args) {
        SingleTonMain1 singleTonMain1 = getSingleTonMain1();
        SingleTonMain1 singleTonMain2 = getSingleTonMain1();
        System.out.println(singleTonMain1==singleTonMain2);

    }
}
