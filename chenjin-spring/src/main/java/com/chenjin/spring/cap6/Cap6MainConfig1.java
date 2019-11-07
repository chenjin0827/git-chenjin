package com.chenjin.spring.cap6;

import com.chenjin.spring.cap1.Person;
import com.chenjin.spring.cap5.LinuxCondition;
import com.chenjin.spring.cap5.WinCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import(value = {Dog.class, MyImportSelector.class,
        MyImportBeanDefinitionRegistrar.class})
public class Cap6MainConfig1 {
    /**
     * 给容器注册组件的三种方式
     * 1、@Bean
     * 2、包扫描+组件标注的注解
     * 3、@Import （快速给容器导入一个组件），@Bean有点简单
     * a，@Import（要导入到容器中的组件）：容器会自动注册这个组件，bean的id为全类名
     * b，ImportSelector：是一个接口，返回需要导入到容器的组件的全类名数组
       c,ImportBeanDefinitionRegistrar :可以手动添加组件到IOC容器，所有
        bean的注册都可以通过它来实现
     */
    @Bean(name = "person")
    public Person person() {
        return new Person("zhangsan", 18);
    }
}
