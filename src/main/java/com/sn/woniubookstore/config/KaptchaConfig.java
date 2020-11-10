package com.sn.woniubookstore.config;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/21 16:18
 * @Description: 注册谷歌验证码的servlet
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public ServletRegistrationBean<KaptchaServlet> kaptchaServlet(){

        ServletRegistrationBean<KaptchaServlet> kaptchaServlet = new ServletRegistrationBean<>(new KaptchaServlet(),"/kaptchaServlet");

        return kaptchaServlet;
    }
}