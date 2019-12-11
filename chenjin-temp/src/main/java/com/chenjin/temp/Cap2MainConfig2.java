package com.chenjin.temp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

//配置类，相当于spring的配置文件applicationContext.xml
//使用include时候要将useDefaultFilters设置为false，不然还是会全部扫描到
//使用exclude 的useDefaultFilters要默认，默认为true
//排除了service扫描
@Configuration
@ComponentScan(value = "com.chenjin.temp",excludeFilters =
        {@Filter(type = FilterType.ANNOTATION,
                value = Controller.class)})
public class Cap2MainConfig2 {
    //给容器中注册一个bean，类型为返回值的类型 name属性对应容器中的bean的id
    //指定就是取的方法名（首字母小写）作为id
    @Bean(name = "person")
    public Person person() {
        return new Person("chenjin", 20);
    }
}
