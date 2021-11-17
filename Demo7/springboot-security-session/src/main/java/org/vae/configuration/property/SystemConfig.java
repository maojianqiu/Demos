package org.vae.configuration.property;

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
//    private WxConfig wx;
    private QnConfig qn;

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

//    public WxConfig getWx() {
//        return wx;
//    }

//    public void setWx(WxConfig wx) {
//        this.wx = wx;
//    }

    public QnConfig getQn() {
        return qn;
    }

    public void setQn(QnConfig qn) {
        this.qn = qn;
    }

}
