package com.vae.demo03.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * description: MybatisConfig <br>
 * date: 2021/12/21 21:29 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */
@Configuration
@MapperScan("com.vae.demo03.mapper")
public class MybatisConfig {
}
