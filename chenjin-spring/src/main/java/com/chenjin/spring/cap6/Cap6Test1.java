package com.chenjin.spring.cap6;

import com.chenjin.spring.cap5.Cap5MainConfig1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Cap6Test1 {

    public static void main(String[] args) {
        ApplicationContext app = new AnnotationConfigApplicationContext(Cap6MainConfig1.class);
        System.out.println("容器初始化完毕");
        //获取spring容器中所有的bean的名字
        String[] beanDefinitionNames = app.getBeanDefinitionNames();
        for(String s:beanDefinitionNames){
            System.out.println(s);
        }
        //使用@Import生成的bean 名字是包全名
        Dog dog = (Dog)app.getBean("com.chenjin.spring.cap6.Dog");
        System.out.println(dog);

    }
}
