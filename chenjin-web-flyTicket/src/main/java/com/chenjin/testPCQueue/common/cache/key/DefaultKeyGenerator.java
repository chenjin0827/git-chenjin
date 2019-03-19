//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chenjin.testPCQueue.common.cache.key;

import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;

public class DefaultKeyGenerator implements KeyGenerator {
    public DefaultKeyGenerator() {
    }

    public Object generate(Object target, Method method, Object... params) {
        return new DefaultKey(target, method, params);
    }
}
