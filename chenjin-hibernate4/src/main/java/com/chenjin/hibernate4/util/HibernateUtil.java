package com.chenjin.hibernate4.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
    public static final SessionFactory sessionFactory;
    //ThreadLocal可以隔离多个线程的数据共享，因此不需要对线程进行同步
    public static ThreadLocal<Session> session = new ThreadLocal<>();

    static {
        //使用默认配置文件创建Configuration实例
        Configuration cfg = new Configuration()
                .configure();

        //以Configuration实例来创建SessionFactory实例
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(cfg.getProperties())
                .buildServiceRegistry();

        sessionFactory = cfg.buildSessionFactory(serviceRegistry);
    }

    public static Session getSession() {
        Session s = session.get();
        //如果该线程还没有Session，则创建一个新的Session
        if (s == null) {
//            s = sessionFactory.getCurrentSession();
            s = sessionFactory.openSession();
            //将获得的Session变量存储在ThreadLocal变量session里
            session.set(s);
        }
        return s;
    }

    public static void clossSession() {
        Session s = session.get();
        if (s != null) {
            s.close();
        }
        session.set(null);
    }

    public static SessionFactory getSessionFactory() {
        if (null != sessionFactory)
            return sessionFactory;
        return null;
    }
}