package com.vae.demo03.base;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于配置白名单资源路径
 * Created by macro on 2018/11/5.
 */

@ConfigurationProperties(prefix = "secure.ignored")
//指明要加载哪个bean
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();


}
