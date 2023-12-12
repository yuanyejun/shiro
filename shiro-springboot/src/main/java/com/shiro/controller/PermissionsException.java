package com.shiro.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author 原野
 * @DATE 2023/9/1 15:15
 * @Description:
 * @Version 1.0
 */
@ControllerAdvice
public class PermissionsException {


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public String unauthorizedException(Exception e){
        return "没有权限";
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public String authorizationException(Exception e){
        return "权限认证失败";
    }

}
