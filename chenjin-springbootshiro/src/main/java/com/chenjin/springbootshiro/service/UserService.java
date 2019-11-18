package com.chenjin.springbootshiro.service;

import com.chenjin.springbootshiro.model.User;


public interface UserService {

    User findByUsername(String username);
}
