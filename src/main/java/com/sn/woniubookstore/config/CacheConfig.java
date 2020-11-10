package com.sn.woniubookstore.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Author: shinan
 * @Version: 1.8
 * @create: 2020/10/30 12:59
 * @Description: 在容器中注册一个缓存 用来保存请求路径
 */
@Configuration
public class CacheConfig {

    @Bean
    public Cache<String,Integer> getCache(){

        /**
         *
         * 1.CacheBuilder.newBuilder() 获取一个CacheBuilder
         * 2.调用expireAfterWrite 方法
         *  设置存入缓存的数据在多长时间后清除
         *  参数：duration
         *      the length of time after an entry is created that it should be automatically removed
         *      缓存在多久后自动删除
         *
         *      unit 指定时间单位
         *      返回值类型：CacheBuilder
         * 3.调用CacheBuilder中的build()方法
         *   创建的LocalManualCache<K, V>的对象  LocalManualCache<K, V>实现了Cache<K, V>接口
         *
         * 4.设置 请求路径保存的时间为2秒
         */
        return CacheBuilder.newBuilder().expireAfterWrite(3L, TimeUnit.SECONDS).build();
    }
}