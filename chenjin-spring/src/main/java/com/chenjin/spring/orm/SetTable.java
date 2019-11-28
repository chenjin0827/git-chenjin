package com.chenjin.spring.orm;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;



@Retention(RetentionPolicy.RUNTIME)
public @interface SetTable {
    String value();
}
