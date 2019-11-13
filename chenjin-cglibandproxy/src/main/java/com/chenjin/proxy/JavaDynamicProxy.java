package com.chenjin.proxy;

import java.lang.reflect.Proxy;

/**
 * java动态代理
 * 作用：对传进来的对象方法进行增强，甚至决定原来对象的方法是否通过反射进行需要调用
 *
 */
public class JavaDynamicProxy {


    public static void main(String[] args222) {

        JavaDeveloper java = new JavaDeveloper("java");
        Developer proxy1 = (Developer) Proxy.newProxyInstance(java.getClass().getClassLoader(),
                java.getClass().getInterfaces(), (proxy, method, args) -> {
                    if (method.getName().equals("code")) {
                        System.out.println(java.getName() + "  prepared to coding....");
                        return method.invoke(java, args);
                    }
                    if (method.getName().equals("debug")) {
                        System.out.println(java.getName() + " is check has bug....");
                        return method.invoke(java, args);
//                        return null;
                    }
                    return null;
                });
        proxy1.code();
        proxy1.debug();
    }

}
