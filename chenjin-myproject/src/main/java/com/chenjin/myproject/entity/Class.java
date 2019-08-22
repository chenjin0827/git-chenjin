package com.chenjin.myproject.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 班级
 */
@Entity
@Table(name = "t_class")
public class Class extends BaseEntity{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
