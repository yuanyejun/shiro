package com.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shiro.entity.User;
import com.shiro.mapper.UserMapper;
import com.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 原野
 * @DATE 2023/9/1 10:34
 * @Description:
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User getUserInfoByName(String name) {

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName,name);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        return user;

    }

    //获取用户的角色信息
    @Override
    public List<String> getUserRoleInfoMapper(String principal) {
        return userMapper.getUserRoleInfoMapper(principal);
    }

    //获取用户角色的权限信息
    @Override
    public List<String> getUserPermissionInfoMapper(List<String> roles) {
        return userMapper.getUserPermissionInfoMapper(roles);
    }


}
