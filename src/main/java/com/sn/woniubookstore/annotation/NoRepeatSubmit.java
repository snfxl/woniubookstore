package com.sn.woniubookstore.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/30 11:41
 * @Description: 防止表单重复提交注解
 */
//作用在方法上
@Target(ElementType.METHOD)
//运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmit {


}
