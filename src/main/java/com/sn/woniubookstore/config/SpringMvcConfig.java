package com.sn.woniubookstore.config;

import com.sn.woniubookstore.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/21 11:46
 * @Description: springmvc配置类
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    /**
     * 添加视图解析器
     * @param registry 可以设置视图
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("redirect:/getAllGoods?loginStatus=0");
        registry.addViewController("/index.html").setViewName("redirect:/getAllGoods?loginStatus=0");
    }

    /**
     *
     * @param registry 可以注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> pathPatterns = new ArrayList<>();

        //我的订单
        pathPatterns.add("/myOrderPage");
        //个人中心
        pathPatterns.add("/personalCenterPage");
        //加入购物车
        pathPatterns.add("/addGoodPage/**");
        //跳转到购物车页面
        pathPatterns.add("/cartPage");

        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns(pathPatterns);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //关于图片上传后需要重启服务器才能刷新图片
        //这是一种保护机制，为了防止绝对路径被看出来，目录结构暴露
        //解决方法:
        // 将虚拟路径/images/**
        // 向绝对路径 (D:\\Java学习\\springboot小滴\\springboot项目\\upload\\src\\main\\resources\\static\\images\\)映射

        // addResourceHandler是指你想在url请求的路径
        // addResourceLocations是图片存放的真实路径

        String path="E:\\ideaproject\\woniu-bookstore\\src\\main\\resources\\static\\images\\";
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+path);
    }

}