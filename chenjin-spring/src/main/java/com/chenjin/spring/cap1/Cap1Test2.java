package com.chenjin.spring.cap1;

import com.chenjin.spring.cap1.config.Cap1MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Cap1Test2 {
    public static void main(String[] args) {
        //通过注解形式加载bean
        ApplicationContext app = new AnnotationConfigApplicationContext(Cap1MainConfig.class);
        //从容器中获取bean
        Person person = (Person) app.getBean("person");
        System.out.println(person);
/**
 * 根据类型 获取bean的名字 就相当于bean的id
 */
        String[] beanNamesForType = app.getBeanNamesForType(Person.class);
        for (String s:beanNamesForType) {
            System.out.println(s);
        }
    }
}
