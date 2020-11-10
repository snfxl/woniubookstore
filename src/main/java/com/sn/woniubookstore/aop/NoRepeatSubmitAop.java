package com.sn.woniubookstore.aop;

import com.google.common.cache.Cache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/30 9:41
 * @Description: 切面 并将其注册到容器中
 */
@Aspect
@Component
public class NoRepeatSubmitAop {

    private Log log = LogFactory.getLog(getClass());

    /**
     * 获取保存请求路径的缓存
     */
    @Autowired
    private Cache<String, Integer> urls;


    /**
     * 配置切点
     */
    @Pointcut("@annotation(com.sn.woniubookstore.annotation.NoRepeatSubmit)")
    public void hasTokenPointCut() {

    }

    /**
     * 判断切点是否有token
     */
    @Around("hasTokenPointCut()")
    public Object hasToken(ProceedingJoinPoint proceedingJoinPoint) {

        try {
            //1.获取域对象(将其放在方法的第一个参数上)
            HttpServletRequest request = (HttpServletRequest) proceedingJoinPoint.getArgs()[0];

            //2.获取session的id和请求路径
            String sessionId = request.getSession().getId();
            String servletPath = request.getServletPath();

            //3.将sessionId和请求路径拼起来作为key
            String key = sessionId + "-" + servletPath;

            //4.判断保存请求地址的缓存中是否有该路径
            //4.1如果没有  说明是第一次访问 放行并将其保存到
            if (urls.getIfPresent(key) == null) {

                //执行切点
                Object proceed = proceedingJoinPoint.proceed();
                //执行后将其放到缓存中
                urls.put(key, 0);
                //返回切点返回值
                return proceed;
            } else {

                //4.2 如果有就说明在2秒内重复提交 直接返回null
                log.error("[FORM]重复提交");
                return null;
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.error("[FORM]重复提交异常");
            return "[FORM]重复提交异常";
        }
    }
}