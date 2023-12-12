package com.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiro.entity.User;
import com.shiro.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 原野
 * @DATE 2023/9/1 10:32
 * @Description:
 * @Version 1.0
 */

public interface UserService {

    //用户登录
    User getUserInfoByName(String name);

    ////获取用户的角色信息
    List<String> getUserRoleInfoMapper(@Param("principal") String principal);


    List<String> getUserPermissionInfoMapper(@Param("roles")List<String> roles);

}
