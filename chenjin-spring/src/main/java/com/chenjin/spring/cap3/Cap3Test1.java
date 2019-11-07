package com.chenjin.spring.cap3;

import com.chenjin.spring.cap1.Person;
import com.chenjin.spring.cap2.config.Cap2MainConfig1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Cap3Test1 {

    public static void main(String[] args) {
        ApplicationContext app = new AnnotationConfigApplicationContext(Cap3MainConfig1.class);
        //获取spring容器中所有的bean的名字
        System.out.println("容器初始化完毕");
        String[] beanDefinitionNames = app.getBeanDefinitionNames();
        for (String s : beanDefinitionNames) {
            System.out.println(s);
        }
        Person person1 = (Person) app.getBean("person");
        Person person2 = (Person) app.getBean("person");
        System.out.println(person1 == person2);
    }
}
