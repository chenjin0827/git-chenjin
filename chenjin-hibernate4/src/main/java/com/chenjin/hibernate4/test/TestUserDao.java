package com.chenjin.hibernate4.test;


import com.chenjin.hibernate4.dao.IUserDao;
import com.chenjin.hibernate4.dao.impl.UserDao;
import com.chenjin.hibernate4.entity.User;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestUserDao {
    public final static Logger logger = Logger.getLogger(TestUserDao.class);

    private IUserDao userDao ;

    @Before
    public void setUp() {
        userDao = new UserDao();
    }

    @Test
    public void testAddUser() {
        logger.info("Test [UserDao.addUser].");
        User user = new User("admin",1);

        userDao.addUser(user);
    }

    @Test
    public void testGetUser() {
        logger.info("Test [UserDao.getUser].");

        User user = userDao.getUser("admin");
        logger.info(user);
    }

    @Test
    public void testGetAll() {
        logger.info("Test [UserDao.listAll].");

        List<User> userList = userDao.getAll();

    }

    @After
    public void tearDown() {
        userDao = null;
    }
}