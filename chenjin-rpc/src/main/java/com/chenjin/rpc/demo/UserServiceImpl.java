package com.chenjin.rpc.demo;

public class UserServiceImpl implements IUserService {
    @Override
    public User findById(Long id) {
        User user = new User();
        user.setName("nezha");
        user.setAge(123);
        return user;
    }
}
