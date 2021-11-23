package com.vae.dao;

import com.vae.domain.User;

/**
 * @author 武汉思维跳跃科技有限公司
 */

public interface UserMapper extends BaseMapper<User> {


    User getUserByUserName(String username);

}
