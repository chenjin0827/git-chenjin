package com.chenjin.rpc;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
/**
 * 该注解具备 Spring 的Component注解的特性，可被 Spring 扫描。
 */
public @interface RpcService {
    Class<?> value();
}
