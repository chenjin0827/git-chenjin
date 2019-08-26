package com.chenjin.hibernate4.dao.impl;


import com.chenjin.hibernate4.dao.IUserDao;
import com.chenjin.hibernate4.entity.User;
import com.chenjin.hibernate4.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;


public class UserDao implements IUserDao {


    public void addUser(User user) {
        if (user == null) {
            throw new NullPointerException("空指针，要保存的用户为空");
        }

        Session session = null;
        try {
            SessionFactory factory = HibernateUtil.getSessionFactory();
            session = factory.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }


    public User getUser(String name) {
        User user = null;
        Session session = null;
        try {
            SessionFactory factory = HibernateUtil.getSessionFactory();
            session = factory.openSession();
            user = (User) session.createQuery("from User where name = ?").setParameter(0, name).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return user;
    }


    public List<User> getAll() {
        List<User> list = null;
        try {
            list = HibernateUtil.getSessionFactory().openSession().createCriteria(User.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

}