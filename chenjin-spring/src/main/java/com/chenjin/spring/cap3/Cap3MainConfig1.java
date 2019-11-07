package com.chenjin.spring.cap3;

import com.chenjin.spring.cap1.Person;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Controller;


@Configuration
public class Cap3MainConfig1 {

    //多实例模式，ioc容器启动时不会创建对象，只有用到的时候才创建对象，每个对象都不一样
    //单实例模式，ioc容器启动的时候就会调用方法创建对象放到容器中，只会new 一次
    //request 主要针对web应用，递交一次请求创建一个实例
    //session：同一个session创建一个实例
//    @Scope("prototype")
    @Scope("singleton")
    @Bean(name = "person")
    public Person person() {
        return new Person("chenjin", 20);
    }
}
