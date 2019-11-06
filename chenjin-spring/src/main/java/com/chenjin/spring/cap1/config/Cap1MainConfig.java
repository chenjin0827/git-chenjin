package com.chenjin.spring.cap1.config;

import com.chenjin.spring.cap1.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//配置类，相当于spring的配置文件applicationContext.xml
@Configuration
public class Cap1MainConfig {
    //给容器中注册一个bean，类型为返回值的类型 name属性对应容器中的bean的id
    //指定就是取的方法名（首字母小写）作为id
    @Bean(name = "person")
    public Person person() {
        return new Person("chenjin", 20);
    }
}
