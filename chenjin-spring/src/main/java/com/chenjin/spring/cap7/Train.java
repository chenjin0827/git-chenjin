package com.chenjin.spring.cap7;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Train implements InitializingBean, DisposableBean {
    public Train() {
        System.out.println(this.getClass().getSimpleName() + "构造方法创建。。。");
    }

    //当bean销毁时调用此方法
    @Override
    public void destroy() throws Exception {
        System.out.println(this.getClass().getSimpleName() + "destroy了。。。");
    }

    //当bean属性赋值和初始化完成时调用
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.getClass().getSimpleName() + "afterPropertiesSet了。。。");
    }
}
