package com.chenjin.guava;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;

public class GuavaTest {


    public static void main(String[] args) throws ExecutionException {
        Cache<Object, Object> cache =
                CacheBuilder.newBuilder().initialCapacity(100).maximumSize(1000)
                        .build();
        cache.put("a", "a");
        Object a = cache.getIfPresent("b");
        System.out.println(a);
    }


}
