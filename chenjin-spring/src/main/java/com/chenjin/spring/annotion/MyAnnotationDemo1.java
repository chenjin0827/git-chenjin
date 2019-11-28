package com.chenjin.spring.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@MyAnnotationDemo1(value = "11", classId = 3, array = {"a", "b"})
class Demo1 {

}

/**
 * @Target 允许在哪里使用注解
 * @Retention 标识允许反射获取信息，表示需要在什么级别保存该注释信息，用于描述注解的生命周期（即：被描述的注解在什么范围内有效）
 */
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotationDemo1 {
    String value() default "1233";//使用注解时候不写默认就是取value

    int classId() default 0;

    String[] array() default {};
}
