package com.chenjin.test;

import java.util.List;

public class Class {

    private String name;
    private List<Student> students;
    private List<Desk> desks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Class{" +
                "name='" + name + '\'' +
                ", students=" + students +
                ", desks=" + desks +
                '}';
    }

    public Class(String name) {
        this.name = name;
    }

    public List<Desk> getDesks() {
        return desks;
    }

    public void setDesks(List<Desk> desks) {
        this.desks = desks;
    }
}
