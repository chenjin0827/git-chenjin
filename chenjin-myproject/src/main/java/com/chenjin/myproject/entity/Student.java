package com.chenjin.myproject.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 学生
 */
@Entity
@Table(name = "t_student")
public class Student extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
