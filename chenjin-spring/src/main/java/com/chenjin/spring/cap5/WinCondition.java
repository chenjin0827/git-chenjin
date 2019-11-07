package com.chenjin.spring.cap5;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WinCondition implements Condition{
    /**
     *
     * @param context 判断可以使用的上下文（环境）
     * @param metadata 注解的信息
     * @return
     * beanFactory 和factoryBean区别
     * factoryBean 一种注册机制，可以把java实例（bean）通过factorybean注入到容器中
     * beanFactory 从我们的容器中获取实例化后的bean
     */
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Environment environment = context.getEnvironment();
        String osName = environment.getProperty("os.name");
        if(osName.contains("Windows")){
            return true;
        }
        return false;
    }
}
