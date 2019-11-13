package com.chenjin.reflect;

public class Person {
    private String name = "111";

    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    private String getSecretName() {
        return "fool is you!";
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


}
