package com.sn.woniubookstore.component;

import com.sn.woniubookstore.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/25 13:06
 * @Description: 登录拦截器
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.获取session域中的用户
        User user = (User) request.getSession().getAttribute("user");

        //2.判断用户名是否为null
        if (user.getAccount() == null) {

            //如果为null说明 说明没有登录 转发到登录页面
            request.setAttribute("loginPageErrorMessage", "请先进行登录");
            request.getRequestDispatcher("/loginPage").forward(request, response);
            return false;
        }
        return true;
    }
}