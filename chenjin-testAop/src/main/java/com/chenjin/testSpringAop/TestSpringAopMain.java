package com.chenjin.testSpringAop;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringAopMain {
    public static void main(String[] args) {
        System.out.println("Hello Spring AOP!");
        BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
        CustomerManager customerManager = (CustomerManager) factory.getBean("customerManager");
        customerManager.getCustomerById(2015);
        customerManager.deleteCustomer("1");
    }
}
