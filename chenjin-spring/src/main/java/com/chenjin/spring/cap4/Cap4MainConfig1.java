package com.chenjin.spring.cap4;

import com.chenjin.spring.cap1.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;


@Configuration
public class Cap4MainConfig1 {

    @Scope("singleton")
    //懒加载  主要针对单实例，配置后容器启动时候不创建对象，
    //仅当第一次使用（获取）bean的时候才创建初始化
    @Lazy
    @Bean(name = "person")
    public Person person() {
        return new Person("chenjin", 20);
    }
}
