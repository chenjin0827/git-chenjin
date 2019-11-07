package com.chenjin.spring.cap5;

import com.chenjin.spring.cap1.Person;
import org.springframework.context.annotation.*;


@Configuration
public class Cap5MainConfig1 {
    @Conditional(WinCondition.class)
    @Bean(name = "chenjin")
    public Person chenjin() {
        System.out.println("创建chenjin===");
        return new Person("chenjin", 50);
    }

    @Conditional(LinuxCondition.class)
    @Bean(name = "jiangying")
    public Person jiangying() {
        System.out.println("创建jiangying====");
        return new Person("jiangying", 18);
    }
}
