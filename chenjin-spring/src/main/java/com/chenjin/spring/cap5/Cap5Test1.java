package com.chenjin.spring.cap5;

import com.chenjin.spring.cap4.Cap4MainConfig1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Cap5Test1 {

    public static void main(String[] args) {
        ApplicationContext app = new AnnotationConfigApplicationContext(Cap5MainConfig1.class);
        //获取spring容器中所有的bean的名字
        System.out.println("容器初始化完毕");
    }
}
