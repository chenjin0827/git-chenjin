package com.chenjin.myproject.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "people")
public class People {


    @Column(name = "name")
    private String name;

    @Column(name = "sex")
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