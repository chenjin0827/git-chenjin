package com.chenjin.spring.cap6;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;

public class MyFactoryBean implements FactoryBean<Tiger> {
    @Override
    public boolean isSingleton() {
        return true;
    }

    @Nullable
    @Override
    public Tiger getObject() throws Exception {
        return new Tiger();
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return Tiger.class;
    }
}
