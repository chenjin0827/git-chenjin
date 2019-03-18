package com.springboot.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BlogProperties {

    @Value("${com.chenjin.name}")
    private String name;
    @Value("${com.chenjin.work}")
    private String work;
//    @Value("${com.chenjin.radomInt}")
    private int radomInt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public int getRadomInt() {
        return radomInt;
    }

    public void setRadomInt(int radomInt) {
        this.radomInt = radomInt;
    }
}
