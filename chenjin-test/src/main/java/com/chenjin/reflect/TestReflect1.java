package com.chenjin.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * JAVA反射机制是在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；
 * 对于任意一个对象，都能够调用它的任意方法和属性；
 * 这种动态获取信息以及动态调用对象方法的功能称为java语言的反射机制。
 */
public class TestReflect1 {
    //通过反射创建对象
    @Test
    public void test1() throws IllegalAccessException, InstantiationException {
        Class<?> aClass = null;
        try {
            aClass = Class.forName("com.chenjin.reflect.Person");
            Person person = (Person) aClass.newInstance();
            person.setAge(18);
            person.setName("jiangying");
            System.out.println(person);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 反射私有的构造方法
    @Test
    public void reflectPrivateConstructor() {
        try {
            System.out.println("start");
            Class<?> aclass = Class.forName("com.chenjin.reflect.Person");
            Constructor<?> declaredConstructorBook = aclass.getDeclaredConstructor(String.class, Integer.class);
            //让private可见
            declaredConstructorBook.setAccessible(true);
            Object objectBook = declaredConstructorBook.newInstance("Android开发艺术探索", 18);
            Person book = (Person) objectBook;
            System.out.println(book);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // 反射私有属性
    @Test
    public void reflectPrivateField() {
        try {
            Class<?> classBook = Class.forName("com.chenjin.reflect.Person");
            Object objectBook = classBook.newInstance();
            Field fieldTag = classBook.getDeclaredField("name");
            fieldTag.setAccessible(true);
            String tag = (String) fieldTag.get(objectBook);
            System.out.println(tag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // 反射私有方法
    @Test
    public void reflectPrivateMethod() {
        try {
            Class<?> classBook = Class.forName("com.chenjin.reflect.Person");
            Method methodBook = classBook.getDeclaredMethod("getSecretName");
//            methodBook.setAccessible(true);
            Object objectBook = classBook.newInstance();
            String string = (String) methodBook.invoke(objectBook);
            System.out.println(string);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    //执行反射方法
    public void invoike222() {
        try {
            Class<?> ServiceManager = Class.forName("com.chenjin.reflect.Person");
            // 获得ServiceManager的getService方法
            Object o = ServiceManager.newInstance();
            //getDeclaredMethod可以获取到所有的方法包括private方法，  getMethod只能获取到public的方法
            Method getService = ServiceManager.getDeclaredMethod("getSecretName");
            System.out.println(getService);
            getService.setAccessible(true);
            Object invoke = getService.invoke(o);
            System.out.println(invoke);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
