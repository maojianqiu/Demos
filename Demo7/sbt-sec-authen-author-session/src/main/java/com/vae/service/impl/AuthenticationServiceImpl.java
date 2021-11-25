package com.vae.service.impl;

import com.vae.configuration.properties.SystemConfig;
import com.vae.dao.UmsUserRoleRelationDao;
import com.vae.domain.model.UmsResource;
import com.vae.domain.model.User;
import com.vae.service.UserService;
import com.vae.util.RsaUtil;
import com.vae.service.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 武汉思维跳跃科技有限公司
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UmsUserRoleRelationDao userRoleRelationDao;
    private final UserService userService;
    private final SystemConfig systemConfig;

    @Autowired
    public AuthenticationServiceImpl(UmsUserRoleRelationDao userRoleRelationDao,UserService userService, SystemConfig systemConfig) {
        this.userRoleRelationDao = userRoleRelationDao;
        this.userService = userService;
        this.systemConfig = systemConfig;
    }


    /**
     * @param username username
     * @param password password
     * @return boolean
     */
    @Override
    public boolean authUser(String username, String password) {
        User user = userService.getUserByUserName(username);
        return authUser(user, username, password);
    }


    @Override
    public boolean authUser(User user, String username, String password) {
        if (user == null) {
            return false;
        }
        String encodePwd = user.getPassword();
        if (null == encodePwd || encodePwd.length() == 0) {
            return false;
        }
        String pwd = pwdDecode(encodePwd);
        return pwd.equals(password);
    }

    @Override
    public String pwdEncode(String password) {
        return RsaUtil.rsaEncode(systemConfig.getPwdKey().getPublicKey(), password);
    }

    @Override
    public String pwdDecode(String encodePwd) {
        return RsaUtil.rsaDecode(systemConfig.getPwdKey().getPrivateKey(), encodePwd);
    }

    @Override
    public List<UmsResource> getResourceList(String username) {
        List<UmsResource> resourceList = userRoleRelationDao.getResourceList(username);
        return resourceList;
    }

}
