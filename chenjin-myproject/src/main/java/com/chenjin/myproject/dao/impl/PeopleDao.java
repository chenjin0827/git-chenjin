package com.chenjin.myproject.dao.impl;


import com.chenjin.myproject.dao.IPeopleDao;
import com.chenjin.myproject.entity.People;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@Repository("peopleDao")
public class PeopleDao implements IPeopleDao {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * 保存对象
     *
     * @param p
     * @return
     */
    @Override
    public People save(People p) {
        sessionFactory.getCurrentSession().save(p);
        return p;
    }


}