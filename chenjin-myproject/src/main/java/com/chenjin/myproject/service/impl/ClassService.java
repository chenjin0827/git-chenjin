package com.chenjin.myproject.service.impl;

import com.chenjin.myproject.dao.IClassDao;
import com.chenjin.myproject.service.IClassService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(readOnly = true)
public class ClassService implements IClassService {
    @Resource
    private IClassDao classDao;


}
