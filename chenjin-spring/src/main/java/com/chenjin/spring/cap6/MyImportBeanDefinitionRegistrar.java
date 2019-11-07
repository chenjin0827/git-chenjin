package com.chenjin.spring.cap6;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar{
    /**
     *自己手动注册bean
     * @param annotationMetadata
     * 当前类的注解信息
     * @param registry BeanDefinition注册类，把所有需要添加到容器中的bean加入
     */
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        boolean b = registry.containsBeanDefinition("com.chenjin.spring.cap6.bean.Pig");


    }
}
