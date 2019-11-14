package com.chenjin.spring.cap7;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * 实现spring的 BeanPostProcessor接口
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        返回一个对象（传过来的对象）
//        在初始化方法调用之前进行后置处理工作
//        什么时候调用：init-method=init之前调用
        System.out.println(this.getClass().getSimpleName()+"   beforeInitialization"+bean+"    ,"+beanName);
        return bean;
    }

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(this.getClass().getSimpleName()+"   afterInitialization"+bean+"    ,"+beanName);
        return bean;
    }
}
