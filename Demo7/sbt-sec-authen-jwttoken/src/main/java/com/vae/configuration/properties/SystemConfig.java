package com.vae.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


/**
 * 拿到配置信息：
 *
 * @author 武汉思维跳跃科技有限公司
 */
//实体类中使用@ConfigurationProperties(prefix = "system") 注解，prefix = "system"  用来选择哪个属性的prefix名字来绑定，即对应yml文件中的system属性
@ConfigurationProperties(prefix = "system")
public class SystemConfig {

    private PasswordKeyConfig pwdKey;
    private List<String> securityIgnoreUrls;

    public PasswordKeyConfig getPwdKey() {
        return pwdKey;
    }

    public void setPwdKey(PasswordKeyConfig pwdKey) {
        this.pwdKey = pwdKey;
    }

    public List<String> getSecurityIgnoreUrls() {
        return securityIgnoreUrls;
    }

    public void setSecurityIgnoreUrls(List<String> securityIgnoreUrls) {
        this.securityIgnoreUrls = securityIgnoreUrls;
    }



}
