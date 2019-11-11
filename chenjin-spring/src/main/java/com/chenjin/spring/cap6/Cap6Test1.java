package com.chenjin.spring.cap6;

import com.chenjin.spring.cap5.Cap5MainConfig1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Cap6Test1 {
    public static void main(String[] args) {
        ApplicationContext app = new AnnotationConfigApplicationContext(Cap6MainConfig1.class);
        System.out.println("容器初始化完毕");
        //获取spring容器中所有的bean的名字
        String[] beanDefinitionNames = app.getBeanDefinitionNames();
        for(String s:beanDefinitionNames){
            System.out.println(s);
        }
        //使用@Import生成的bean 名字是包全名
        Dog dog = (Dog)app.getBean("com.chenjin.spring.cap6.Dog");
        System.out.println(dog);
        //这里有个注意点是spring  源码BeanFactoryUtils的isFactoryDereference方法，BeanFactory.FACTORY_BEAN_PREFIX
        //取bean的时候如果没有使用&符号，则会调用我们继承FactoryBean的类，最后得到Tiger对象，如果使用了&，则最后获得的对象是MyFactoryBean对象
        Object myFactoryBean = app.getBean("myFactoryBean");
//        Object myFactoryBean = app.getBean("&myFactoryBean");
        System.out.println("bean的类型===="+myFactoryBean.getClass());

    }
}
