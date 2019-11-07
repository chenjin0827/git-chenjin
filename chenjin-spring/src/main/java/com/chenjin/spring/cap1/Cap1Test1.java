package com.chenjin.spring.cap1;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Cap1Test1 {

    public static void main(String[] args) {
//        通过配置xml文件加载bean
        ApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
        Person person = (Person) app.getBean("person");
        System.out.println("11");
        System.out.println(person);
    }
}
