package com.chenjin.springbootshiro;

import com.chenjin.springbootshiro.mapper.UserMapper;
import com.chenjin.springbootshiro.model.User;
import com.chenjin.springbootshiro.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    public void testUserMapper(){
        System.out.println("测试开始");
        User admin = userMapper.findByUsername("admin");
        System.out.println(admin.getPassword());

    }

    @Test
    public void testService(){
        System.out.println("测试开始");
        User user = userService.findByUsername("admin");
        System.out.println(user.getRoles());
    }


}
