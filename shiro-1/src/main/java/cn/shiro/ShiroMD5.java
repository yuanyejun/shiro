package cn.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @Author 原野
 * @DATE 2023/9/1 9:34
 * @Description:
 * @Version 1.0
 */
public class ShiroMD5 {

    public static void main(String[] args) {


        //密码明文
        String password = "123456";
        //使用 md5 加密
        Md5Hash md5Hash = new Md5Hash(password);
        System.out.println("md5Hash = " + md5Hash);
        System.out.println("----------------------------");
        //带盐的 md5 加密，盐就是在密码明文后拼接新字符串，然后再进行加密
        Md5Hash md5Hash2 = new Md5Hash(password,"salt");
        System.out.println("\"带盐加密:\" + md5Hash2 = " + "带盐加密:" + md5Hash2);
        //为了保证安全，避免被破解还可以多次迭代加密，保证数据安全
        Md5Hash md5Hash3 = new Md5Hash(password, "salt", 3);
        System.out.println("\"多次迭代带盐加密：\" + md5Hash3 = " + "多次迭代带盐加密：" + md5Hash3);
        //使用父类实现加密
        SimpleHash simpleHash = new SimpleHash("MD2", password, "salt", 3);
        System.out.println("\"使用父类进行加密:\" + simpleHash = " + "使用父类进行加密:" + simpleHash);
    }
}
