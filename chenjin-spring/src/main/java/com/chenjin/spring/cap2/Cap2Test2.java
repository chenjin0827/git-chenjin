package com.chenjin.spring.cap2;

import com.chenjin.spring.cap2.config.Cap2MainConfig1;
import com.chenjin.spring.cap2.config.Cap2MainConfig2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Cap2Test2 {

    public static void main(String[] args) {
        ApplicationContext app = new AnnotationConfigApplicationContext(Cap2MainConfig2.class);
        //获取spring容器中所有的bean的名字
        String[] beanDefinitionNames = app.getBeanDefinitionNames();
        for (String s: beanDefinitionNames) {
            System.out.println(s);
        }
    }
}
