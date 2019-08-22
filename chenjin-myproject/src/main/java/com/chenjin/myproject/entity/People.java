package com.chenjin.myproject.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_people")
public class People extends BaseEntity {
    private String name;
    private String sex;

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }
}