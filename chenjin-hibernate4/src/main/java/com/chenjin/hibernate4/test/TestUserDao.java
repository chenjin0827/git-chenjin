package com.chenjin.hibernate4.test;


import com.chenjin.hibernate4.dao.IUserDao;
import com.chenjin.hibernate4.dao.impl.UserDao;
import com.chenjin.hibernate4.entity.User;
import org.jboss.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

public class TestUserDao {
    public final static org.jboss.logging.Logger logger = Logger.getLogger(TestUserDao.class);

    private IUserDao userDao ;

    @Before
    public void setUp() {
        userDao = new UserDao();
    }

    @Test
    public void testAddUser() {
        logger.info("Test [UserDao.addUser].");
        Calendar c = Calendar.getInstance();
        c.set(2019, 01,01);
        User user = new User("admin",1);

        userDao.addUser(user);
    }

    @Test
    public void testGetUser() {
        logger.info("Test [UserDao.getUser].");

        User user = userDao.getUser("admin");
        logger.info(user);
        Assert.assertEquals(user.getName(),"admin");
        Assert.assertEquals(user.getAge(),1);
    }

    @Test
    public void testGetAll() {
        logger.info("Test [UserDao.listAll].");

        List<User> userList = userDao.getAll();
        Assert.assertTrue(userList.size() == 1);
        logger.info(userList.get(0));
    }

    @After
    public void tearDown() {
        userDao = null;
    }
}