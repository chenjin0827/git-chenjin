
package com.chenjin.hibernate4.dao;

import com.chenjin.hibernate4.entity.User;

import java.util.List;


public interface IUserDao {

    void addUser(User user);

    User getUser(String name);

    List<User> getAll();
}