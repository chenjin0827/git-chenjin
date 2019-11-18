package com.chenjin.springbootshiro.mapper;

import com.chenjin.springbootshiro.model.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {

    User findByUsername(@Param("username") String username);
}
