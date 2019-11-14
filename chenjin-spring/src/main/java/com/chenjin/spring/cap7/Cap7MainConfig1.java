package com.chenjin.spring.cap7;

import com.chenjin.spring.cap1.Person;
import com.chenjin.spring.cap6.Dog;
import com.chenjin.spring.cap6.MyFactoryBean;
import com.chenjin.spring.cap6.MyImportBeanDefinitionRegistrar;
import com.chenjin.spring.cap6.MyImportSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ComponentScan("com.chenjin.spring.cap7")
public class Cap7MainConfig1 {
    /**
     *在bean的注解上面使用initMethod 和destroyMethod 方法定义初始化和销毁方法
     */
    @Bean(name = "bike", initMethod = "init", destroyMethod = "destroy")
    public Bike bike() {
        return new Bike();
    }

}
