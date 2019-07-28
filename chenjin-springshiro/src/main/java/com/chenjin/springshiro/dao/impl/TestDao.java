package com.chenjin.springshiro.dao.impl;

import com.chenjin.springshiro.dao.ITestDao;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao implements ITestDao {
    @Override
    public void test(String a, String b, String c) {
        System.out.println("testDao--------:a=" + a + ",b=" + b + ",c=" + c);
    }
}
