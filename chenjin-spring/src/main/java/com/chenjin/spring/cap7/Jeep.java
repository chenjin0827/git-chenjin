package com.chenjin.spring.cap7;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Jeep {


    public Jeep() {
        System.out.println(this.getClass().getSimpleName() + "构造方法创建。。。");
    }

    @PostConstruct
    public void init() {
        System.out.println(this.getClass().getSimpleName() + "初始化了。。。");
    }

    @PreDestroy
    public void destroy() {
        System.out.println(this.getClass().getSimpleName() + "销毁了。。。");
    }
}
