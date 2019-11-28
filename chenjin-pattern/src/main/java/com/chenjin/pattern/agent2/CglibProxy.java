package com.chenjin.pattern.agent2;

import com.chenjin.pattern.agent1.House;
import com.chenjin.pattern.agent1.XiaoMing;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib方式代理
 */
public class CglibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("我是cglib代理，开始监听");
        Object o1 = methodProxy.invokeSuper(o, objects);
        System.out.println("我是cglib代理，结束监听");
        return o1;
    }
    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        Enhancer enhancer = new Enhancer();
        //使用asm框架生成代理类，不是使用的java反射技术
        //asm其实就是java字节码控制
        enhancer.setSuperclass(XiaoMing.class);
        enhancer.setCallback(cglibProxy);
        House house = (House) enhancer.create();
        house.buy();
    }
}
