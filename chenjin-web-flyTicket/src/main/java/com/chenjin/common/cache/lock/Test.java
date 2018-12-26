//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chenjin.common.cache.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Arrays;
import java.util.List;

public class Test {
    static volatile int a = 0;
    static LockCollection lockCollection;
    public Test() {
    }

    public static void print() {
        ++a;
        System.out.println(a);
    }

    public static void main(String[] args) {
        System.out.println("********1****");
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework cf = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(5000).connectionTimeoutMs(5000).retryPolicy(retryPolicy).build();
        cf.start();
        System.out.println("********2****");
        LockFactory lockFactory = new LockFactory();
        lockCollection = new LockCollection();
        lockCollection.setLockFactory(lockFactory);
        Long[] longs = new Long[]{1l,2l,3l};
        List<Long> list = Arrays.asList(longs);
        for(final Long l : list){
            (new Thread(new Runnable() {
                public void run() {
                    Test.run1(l.intValue());
                }
            })).start();
        }

    }

    public static void run1( int k) {
        for(int i = 0; i < 10; ++i) {
            (new Thread(new Runnable() {
                public void run() {
                    System.out.println("********3****");
                    IBaseLock nativeLock = Test.lockCollection.getLock(NativeLock.class, k + "111");
                    IBaseLock nativeLock1 = Test.lockCollection.getLock(NativeLock.class, k + "111");
                    System.out.println("********4****");

                    try {
                        nativeLock.lock();
                        Test.print();

                        try {
                            nativeLock1.lock();
                            Test.print();
                        } finally {
                            nativeLock1.unlock();
                        }
                    } finally {
                        nativeLock.unlock();
                    }

                }
            })).start();
        }

    }
}
