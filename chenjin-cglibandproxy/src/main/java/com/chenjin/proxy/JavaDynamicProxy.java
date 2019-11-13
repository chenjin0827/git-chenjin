package com.chenjin.proxy;

import java.lang.reflect.Proxy;

/**
 * java动态代理
 * 作用：对传进来的对象方法进行增强，甚至决定原来对象的方法是否通过反射进行需要调用
 * JDK动态代理所用到的代理类在程序调用到代理类对象时才由JVM真正创建，JVM根据传进来的
 * 业务实现类对象 以及 方法名 ，动态地创建了
 * 一个代理类的class文件并被字节码引擎执行，然后通过该代理类对象进行方法调用。
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
