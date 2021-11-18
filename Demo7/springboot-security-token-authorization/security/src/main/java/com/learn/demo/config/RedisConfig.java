package com.learn.demo.config;

import com.learn.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Redis配置类
 * Created by macro on 2020/3/2.
 */
//@EnableCaching
//spring cathe 开启缓存，可以使用注解操作缓存更方便@Cacheable；但是如果缓存（例如Redis宕机了，就会抛异常，不会往下进行了，只能重新编写方法（以后要学习）
@Configuration
public class RedisConfig extends BaseRedisConfig {

}
