package com.chenjin.myproject.test;

import org.eclipse.core.internal.runtime.Product;
import org.hibernate.Session;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public class TestSessionFactory {


    @Test
    @Transactional
    public void  save(String projectCode, Product entity) {
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        Product product = (Product)session.get(Product.class, 40361L);
//        System.out.println(product);
//        session.getTransaction().commit();
//        session.close();


    }
}
