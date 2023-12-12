package com.shiro.realm;

import com.shiro.entity.User;
import com.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author 原野
 * @DATE 2023/9/1 10:44
 * @Description:
 * @Version 1.0
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //自定义授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("自定义授权方法");
        // 1 获取用户身份信息
        String principal = principalCollection.getPrimaryPrincipal().toString();
        //2 调用业务层获取用户的角色信息 （db）
        List<String> roleInfo = userService.getUserRoleInfoMapper(principal);
        System.out.println("当前用户角色信息：" + roleInfo);
        // 2.5 调用业务层获取用户的权限信息 （db）
        List<String> permissInfo = userService.getUserPermissionInfoMapper(roleInfo);
        System.out.println("当前用户权限信息：" + permissInfo);
        // 3 创建对象 封装当前登陆用户的角色、权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 存储角色
        info.addRoles(roleInfo);
        info.addStringPermissions(permissInfo);

        // 4 返回信息
        return info;
    }

    //自定义登陆认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       // 1 获取用户身份信息
        String name = authenticationToken.getPrincipal().toString();
        //2 调用业务层获取用户角色信息 （db）
        User userInfo = userService.getUserInfoByName(name);
        // 3 创建对象 封装当前登陆用户的角色、权限信息
        if (userInfo != null){
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                authenticationToken.getPrincipal(),
                userInfo.getPwd(), ByteSource.Util.bytes("salt"),
                authenticationToken.getPrincipal().toString()
            );

            return info;
        }
        return null;
    }
}
