package com.chenjin.springshiro.service.impl;

import com.chenjin.springshiro.dao.ITestDao;
import com.chenjin.springshiro.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService implements ITestService {
    @Autowired
    private ITestDao testDao;

    @Override
    public void test(String a, String b, String c) {
        testDao.test(a, b, c);
    }
}
