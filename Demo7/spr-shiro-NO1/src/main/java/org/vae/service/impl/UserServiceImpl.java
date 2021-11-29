package org.vae.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vae.dao.IUserDao;
import org.vae.model.UserVO;
import org.vae.service.IUserService;


@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public void addUser(UserVO user) throws Exception{
        userDao.addUser(user);
    }
}