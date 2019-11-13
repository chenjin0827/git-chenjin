package com.chenjin.proxy;

/**
 * java程序猿
 */
public class JavaDeveloper implements Developer {
    private String name;

    public JavaDeveloper(String name) {
        this.name = name;
    }

    public JavaDeveloper() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void code() {
        System.out.println(name + " is coding...");
    }

    @Override
    public void debug() {
        System.out.println(name + " is debugging...");
    }
}
