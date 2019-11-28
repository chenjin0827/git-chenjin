package com.chenjin.spring.orm;




/**
 * 表的别名
 */
@SetTable("user_table")
public class UserEntity {
    @SetProperty(name = "user_name", length = 10)
    private String userName;
    @SetProperty(name = "user_age", length = 10)
    private Integer userAge;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }
}