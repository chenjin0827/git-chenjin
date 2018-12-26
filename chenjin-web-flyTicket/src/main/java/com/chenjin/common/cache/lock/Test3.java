package com.chenjin.common.cache.lock;
import java.util.Arrays;
import java.util.List;

public class Test3 {
    static volatile int a = 0;
    static LockCollection lockCollection;
    public Test3() {
    }

    public static void main(String[] args) {
        Long[] longs = new Long[]{1l,2l,3l};
        List<Long> list =Arrays.asList(longs);
        for(final Long l : list){
            (new Thread(new Runnable() {

                public void run() {
                    Test.run1(l.intValue());
                }
            })).start();
        }
    }

    public static void run1(final int k) {
        for(int i = 0; i < 10; ++i) {
            System.out.println(i);
        }

    }
}
