package com.chenjin.hibernate4.test;


import com.chenjin.hibernate4.dao.IUserDao;
import com.chenjin.hibernate4.dao.impl.UserDao;
import com.chenjin.hibernate4.entity.Dept;
import com.chenjin.hibernate4.entity.Employee;
import com.chenjin.hibernate4.entity.User;
import com.chenjin.hibernate4.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestUserDao {
    public final static Logger logger = Logger.getLogger(TestUserDao.class);

    private IUserDao userDao;

    @Before
    public void setUp() {
        userDao = new UserDao();
    }

    @Test
    public void testAddUser() {
        logger.info("Test [UserDao.addUser].");
        User user = new User("admin", 1);
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
        for (User u : userList) {
            System.out.println(u);
        }
    }

    @After
    public void tearDown() {
        userDao = null;
    }

    //测试ManyToOne查询
    @Test
    public void testManyToOne1() {
        logger.info("测试ManyToOne级联操作");
        //使用currentSession不需要手动关闭连接，使用opensession需要关闭
        //使用currentSession不管是查询还是别的都需要开启事务支持，不然报错
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Dept dept = (Dept) session.get(Dept.class, 1L);
        logger.info(dept);
        Employee employee = (Employee) session.get(Employee.class, 1L);
        logger.info(employee);


//        测试懒加载初始化操作

//        测试级联删除
        tx.commit();
        session.close();
    }

    //测试ManyToOne级联保存
    @Test
    public void testManyToOne2() {
        logger.info("测试ManyToOne级联操作");
        //使用currentSession不需要手动关闭连接，使用opensession需要关闭
        //使用currentSession不管是查询还是别的都需要开启事务支持，不然报错
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
//        测试级联保存
        Employee employee1 = new Employee("jiangying", 18);
        Employee employee2 = new Employee("chenjin", 180);

        Dept dept1 = new Dept("dept1", 2);
        dept1.setId(1L);
        employee1.setDept(dept1);
        employee2.setDept(dept1);
        session.save(employee1);
        session.save(employee2);
        tx.commit();
        session.close();
    }

    //测试级联删除
    @Test
    public void testManyToOne3() {
        logger.info("测试ManyToOne级联操作");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Employee employee = new Employee("jiangying", 18);
        //测试删除多的一方一个，
        employee.setId(1L);
        Dept dept1 = new Dept("dept1", 2);
        dept1.setId(1L);
        employee.setDept(dept1);
        session.delete(employee);
        tx.commit();
        session.close();
    }

    //测试懒加载
    //可以通过传参，判定是否使用hibernate的初始化工具在关闭连接前加载好懒加载的数据
    @Test
    public void testManyToOne4() {
        logger.info("测试ManyToOne级联操作");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Employee employee = (Employee) session.get(Employee.class, 1L);
        logger.info("employee==========" + employee);
        Hibernate.initialize(employee.getDept());
        tx.commit();
        session.close();
        Dept dept = employee.getDept();
        System.out.println(dept);
    }


}