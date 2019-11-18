package com.chenjin.springbootshiro.service;

import com.chenjin.springbootshiro.mapper.UserMapper;
import com.chenjin.springbootshiro.model.User;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
