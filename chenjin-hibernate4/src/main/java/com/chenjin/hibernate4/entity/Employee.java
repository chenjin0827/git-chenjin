package com.chenjin.hibernate4.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    private static final long serialVersionUID = -1L;
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    private String name;
    private int age;
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    //referencedColumnName 指定关联表的哪个字段，不指定默认就是关联表的主键字段
    //name属性指定外键的名字 此处员工表存的名字为dId的外键
    @JoinColumn(name = "dId",referencedColumnName = "id")
    private Dept dept;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee() {
    }

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }
}