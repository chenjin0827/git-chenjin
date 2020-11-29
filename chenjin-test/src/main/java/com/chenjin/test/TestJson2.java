package com.chenjin.test;

public class TestJson2 {

    public static void main(String[] args) {
        String s = "小明";
        hello(s);
        System.out.println(s);
    }

    public static void hello(String name) {
        System.out.println(name.hashCode());
        name += "你好";
        System.out.println("name===" + name);
        System.out.println("name===" + name.hashCode());
    }
}
