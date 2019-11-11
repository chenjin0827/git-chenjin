package com.chenjin.spring.cap7;

import com.chenjin.spring.cap6.Dog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 研究spring中bean的生命周期
 */
public class Cap7Test1 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap7MainConfig1.class);
        System.out.println("容器初始化完毕");

        app.close();
    }
}
