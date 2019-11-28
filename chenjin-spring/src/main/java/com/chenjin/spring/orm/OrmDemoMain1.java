package com.chenjin.spring.orm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 项目使用注解，肯定会用到反射  反射应用场景jdbc的驱动注册，spring ioc常用框架，hibernate等框架
 */
public class OrmDemoMain1 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.chenjin.spring.orm.UserEntity");
        //getAnnotations 表示该类上用到了哪些注解
        Annotation[] annotations = aClass.getAnnotations();
        for(Annotation a:annotations){
            System.out.println(a.toString());
        }
        //获取某个注解对象
        SetTable annotation = aClass.getAnnotation(SetTable.class);
        String tableName = annotation.value();
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuffer sb = new StringBuffer();
        sb.append(" select ");
        for(int i =0;i<declaredFields.length;i++){
            SetProperty annotation1 = declaredFields[i].getAnnotation(SetProperty.class);
            String name = annotation1.name();
//            int length = annotation1.length();
            sb.append(name);
            if(i==declaredFields.length-1){
                sb.append(" from ");
            }else{
                sb.append(" , ");
            }
        }
        sb.append(tableName);
        System.out.println(sb.toString());


    }
}
