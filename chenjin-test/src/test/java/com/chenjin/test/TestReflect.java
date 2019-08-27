package com.chenjin.test;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestReflect {

    @Test
    public void test1() throws Exception {
        Student jiangying = new Student("jiangying", 18);
        Student chenjin = new Student("chenjin", 22);
        Desk desk1 = new Desk("desk1", 1);
        Desk desk2 = new Desk("desk2", 2);
        ArrayList<Desk> desks = new ArrayList<>();
        desks.add(desk1);
        desks.add(desk2);
        List<Student> students = new ArrayList<>();
        students.add(jiangying);
        students.add(chenjin);
        Class class1 = new Class("一年级");
        class1.setStudents(students);
        class1.setDesks(desks);
        Field field = class1.getClass().getDeclaredField("students");
        String name = field.getName();
        System.out.println(name);


        Method[] methods = class1.getClass().getMethods();
        //判断是否返回集合，如果是则调用方法
        for (Method m : methods) {
            java.lang.Class<?> returnType = m.getReturnType();
            if (returnType.isAssignableFrom(java.util.List.class)
                    || returnType.isAssignableFrom(java.util.Set.class)) {
                System.out.println("m========" + m);
                Collection collection = (Collection)m.invoke(class1);
                for(Object o:collection){
                    System.out.println("ooo=============="+o);
                }
            }

        }

    }

}
