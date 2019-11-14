package com.chenjin.spring.cap7;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Jeep {


    public Jeep() {
        System.out.println(this.getClass().getSimpleName() + "构造方法创建。。。");
    }

    //使用jdk的一个规范定义的注解  JSR250 （JAVA Specfication Requests）
    //JAVA规范提案
    //在bean创建完成，且属于赋值完成之后进行初始化，属于JDK规范的注解
    @PostConstruct
    public void init() {
        System.out.println(this.getClass().getSimpleName() + "初始化了。。。");
    }

    //在bean被移除之前进行通知，在容器销毁之前进行清理工作
    @PreDestroy
    public void destroy() {
        System.out.println(this.getClass().getSimpleName() + "销毁了。。。");
    }
}
