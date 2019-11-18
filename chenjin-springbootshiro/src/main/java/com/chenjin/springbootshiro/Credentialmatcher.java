package com.chenjin.springbootshiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;


public class Credentialmatcher extends SimpleCredentialsMatcher{

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //获取session中的密码
        String password = new String(usernamePasswordToken.getPassword());
        //从Relm中传递过来的数据库中的密码
        String dbPassword = (String) info.getCredentials();
        return this.equals(password,dbPassword);
    }
}
