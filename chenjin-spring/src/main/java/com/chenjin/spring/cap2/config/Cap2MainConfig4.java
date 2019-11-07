package com.chenjin.spring.cap2.config;

import com.chenjin.spring.cap1.Person;
import com.chenjin.spring.cap2.controller.OrderController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//配置类，相当于spring的配置文件applicationContext.xml
//使用include时候要将useDefaultFilters设置为false，不然还是会全部扫描到
//使用exclude 的useDefaultFilters要默认，默认为true
//测试自定义过滤器
//此处有个巨坑  测试时spring会扫描basepackage下面的所有组件，如果下面组件存在@Configuration
//就会将相关能扫描到的组件进行加载实例化   简单点讲就是扫包时应细化，不要让@Configuration和bean一起扫描，这样会出事的
@Configuration
@ComponentScan(value = "com.chenjin.spring.cap2",includeFilters =
        {@Filter(type = FilterType.CUSTOM,
                classes={MyTypeFilter.class})},useDefaultFilters = false)
public class Cap2MainConfig4 {
    //给容器中注册一个bean，类型为返回值的类型 name属性对应容器中的bean的id
    //指定就是取的方法名（首字母小写）作为id
    @Bean(name = "person")
    public Person person() {
        return new Person("chenjin", 20);
    }
}
