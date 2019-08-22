package com.chenjin.myproject.service.impl;


import com.chenjin.myproject.dao.IPeopleDao;
import com.chenjin.myproject.entity.People;
import com.chenjin.myproject.service.IPeopleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("peopleService")
@Transactional
public class PeopleService implements IPeopleService {

    @Resource(name = "peopleDao")
    private IPeopleDao peopleDao;

    @Override
    public People save(People p) {
        People people = peopleDao.save(p);
        return people;
    }
}