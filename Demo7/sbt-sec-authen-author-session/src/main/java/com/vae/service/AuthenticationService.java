package com.vae.service;


import com.vae.domain.model.UmsResource;
import com.vae.domain.model.User;

import java.util.List;

/**
 * @author 武汉思维跳跃科技有限公司
 */
public interface AuthenticationService {

    /**
     * authUser
     *
     * @param username username
     * @param password password
     * @return boolean
     */
    boolean authUser(String username, String password);

    /**
     * authUser
     *
     * @param user     user
     * @param username username
     * @param password password
     * @return boolean
     */
    boolean authUser(User user, String username, String password);

    /**
     * pwdEncode
     *
     * @param password password
     * @return String
     */
    String pwdEncode(String password);

    /**
     * pwdDecode
     *
     * @param endodePwd endodePwd
     * @return String
     */
    String pwdDecode(String endodePwd);

    /**
     *
     */
    List<UmsResource> getResourceList(String username);
}
