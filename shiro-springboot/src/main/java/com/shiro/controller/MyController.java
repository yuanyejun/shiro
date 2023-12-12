package com.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author 原野
 * @DATE 2023/9/1 11:01
 * @Description:
 * @Version 1.0
 */

@RequestMapping("/myController")
@Controller
public class MyController {


    //跳转登录页面
    @GetMapping("login")
    public String login(){
        return "login";
    }



//    @GetMapping("/userLogin")
//    @ResponseBody
//    public String userLogin(String name,String password){
//
//        // 1 获取subject对象
//        Subject subject = SecurityUtils.getSubject();
//        // 2 封装请求数据到token
//        AuthenticationToken token = new UsernamePasswordToken(name, password);
//        // 3 调用login方法进行登陆验证
//        try {
//            subject.login(token);
//            return "登陆成功....";
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("登陆失败.....");
//            return "登陆失败";
//        }
//    }

    @GetMapping("/userLogin")
    public String userLogin(@RequestParam String name
            ,@RequestParam String password
            ,@RequestParam(defaultValue = "false") Boolean rememberMe
            , HttpServletRequest request){
        System.out.println(name + ":" + password);
        // 1 获取subject对象
        Subject subject = SecurityUtils.getSubject();
        // 2 封装请求数据到token
        AuthenticationToken token = new UsernamePasswordToken(name, password,rememberMe);
        // 3 调用login方法进行登陆验证
        try {
            subject.login(token);
            request.getSession().setAttribute("user",token.getPrincipal().toString());
//            return "登陆成功....";
            return "main";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("登陆失败.....");
            return "登陆失败";
        }
    }


    //登录认证验证 rememberMe
    @GetMapping("userLoginRm")
    public String userLogin(HttpServletRequest request) {
        request.getSession().setAttribute("user","rememberMe");
        return "main";
    }

    //登陆认证验证角色
    @RequiresRoles(value = "admin,user",logical = Logical.OR)
    @GetMapping("/userLoginRoles")
    @ResponseBody
    public String userLoginRoles(){
        System.out.println("登陆验证角色成功..");
        return "验证角色成功";
    }

    //登陆认证验证权限
    @RequiresPermissions("user:delete")
    @GetMapping("/userLoginPermissions")
    @ResponseBody
    public String userLoginPermissions(){
        System.out.println("登陆验证权限成功..");
        return "验证权限成功";
    }


}
