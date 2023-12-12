package cn.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * @Author 原野
 * @DATE 2023/9/1 9:06
 * @Description:
 * @Version 1.0
 */
public class ShiroRun {

    public static void main(String[] args) {
        //1 初始化获取 SecurityManager
        IniSecurityManagerFactory factory =
                new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //2 获取 Subject 对象
        Subject subject = SecurityUtils.getSubject();
        //3 创建 token 对象，web 应用用户名密码从页面传递
        UsernamePasswordToken token =
                new UsernamePasswordToken("zhangsan","z3");
        //4 完成登录
        try {
            subject.login(token);
            System.out.println("登陆成功...");
            //5 判断角色
            boolean result = subject.hasRole("role1");
            System.out.println("是否拥有此角色: == : " + result);
            // 6 判断权限
            boolean result2 = subject.isPermitted("user:insert");
            System.out.println("是否拥有此权限: == : " + result2);

            //也可以用 checkPermission 方法，但没有返回值，没权限抛 AuthenticationException
//            subject.checkPermission("user:select");


        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户不存在");
        }
        catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        }
        catch (AuthenticationException ae) {
            //unexpected condition? error?
        }
    }
}
