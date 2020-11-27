package com.chenjin.pattern.agent2;

import com.chenjin.pattern.agent1.House;
import com.chenjin.pattern.agent1.XiaoMing;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理只能代理接口
 * 是通过java反射机制生成代理类，不用我们手动写代理类
 */
public class JdkProxy implements InvocationHandler {
    public Object target;

    public JdkProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是jdk动态代理开始监听");
        Object invoke = method.invoke(target, args);
        System.out.println("我是jdk动态代理监听结束");
        return invoke;
    }

    public static void main(String[] args) {
        XiaoMing xiaoMing = new XiaoMing();
        JdkProxy jdkProxy = new JdkProxy(xiaoMing);
        House house = (House) Proxy.newProxyInstance(xiaoMing.getClass().getClassLoader(), xiaoMing.getClass().getInterfaces(), jdkProxy);
        house.buy();
    }
}
