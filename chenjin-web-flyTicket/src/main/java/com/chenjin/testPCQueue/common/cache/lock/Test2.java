package com.chenjin.testPCQueue.common.cache.lock;


public class Test2 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {//那个是
            new T().test(String.valueOf(i));//不一样吧 这个是对象拿的方法，和是不是静态类没关系
        }
    }
    //inner class
    static class T {
        public String test(String i) {
            System.out.println(i);
            return i;
        }
    }
}
