package com.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import static java.io.File.separator;

/**
 * @Author 原野
 * @DATE 2023/9/1 10:31
 * @Description:
 * @Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    ////获取用户的角色信息
    @Select("SELECT NAME FROM role WHERE id IN (SELECT rid FROM role_user WHERE uid=(SELECT id FROM USER WHERE NAME=#{principal}))")
    List<String> getUserRoleInfoMapper(@Param("principal") String principal);

    //获取用户角色的权限信息
    @Select({
            "<script>",
            "select info FROM permissions WHERE id IN ",
            "(SELECT pid FROM role_ps WHERE rid IN (",
            "SELECT id FROM role WHERE NAME IN ",
            "<foreach collection='roles' item='name' open='(' separator=',' close=')'>",
            "#{name}",
            "</foreach>",
            "))",
            "</script>"
            })
    List<String> getUserPermissionInfoMapper(@Param("roles")List<String> roles);

}
