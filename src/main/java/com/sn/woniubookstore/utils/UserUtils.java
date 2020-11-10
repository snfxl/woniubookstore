package com.sn.woniubookstore.utils;

import com.sn.woniubookstore.pojo.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/27 14:20
 * @Description: TODO
 */
public class UserUtils {

    /**
     *
     * @param request 域对象
     * @return 获取session域中的登录的用户
     */
    public static User getLoginUser(HttpServletRequest request){

        return (User) request.getSession().getAttribute("user");
    }
}