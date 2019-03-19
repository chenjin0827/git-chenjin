package com.chenjin.testPCQueue.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

public class TestContextLoader {
    public static void main(String[] args) {
        ApplicationContext c = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object taskExecutor = c.getBean("taskExecutor");
        System.out.println(taskExecutor);
    }
}
