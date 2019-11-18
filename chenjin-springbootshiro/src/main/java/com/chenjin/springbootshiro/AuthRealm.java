package com.chenjin.springbootshiro;

import com.chenjin.springbootshiro.model.Permission;
import com.chenjin.springbootshiro.model.Role;
import com.chenjin.springbootshiro.model.User;
import com.chenjin.springbootshiro.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //授权(取出Session中的User对象)
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //session中获取用户
        User user = (User) principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        String username = user.getUsername();
        //权限集合
        List<String> permissionList = new ArrayList<>();
        //角色集合
        List<String> roleNameList = new ArrayList<>();
        Set<Role> roleSet = user.getRoles();
        if (CollectionUtils.isNotEmpty(roleSet)) {
            for (Role role : roleSet) {
                roleNameList.add(role.getRname());
                Set<Permission> permissionSet = role.getPermissons();
                if (CollectionUtils.isNotEmpty(permissionSet)) {
                    for (Permission permission : permissionSet) {
                        //放入权限集合
                        permissionList.add(permission.getName());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);
        info.addRoles(roleNameList);
        return info;
    }

    //认证登陆(认证登陆成功后把User对应放入session中)
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //从转换后的token中获取用户名的信息
        String username = usernamePasswordToken.getUsername();
        //DB获取用户的密码
        User user = userService.findByUsername(username);
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
    }
}
