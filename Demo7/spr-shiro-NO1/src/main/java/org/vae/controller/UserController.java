package org.vae.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.vae.base.ResponseVO;
import org.vae.base.SystemCode;
import org.vae.exception.BugException;
import org.vae.model.UserVO;
import org.vae.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public ResponseVO<String> checkLogin(@RequestParam("userName") String userName,
                                         @RequestParam("password") String password) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

            Subject subject = SecurityUtils.getSubject();

            subject.login(token);
        }catch (Exception e) {
            logger.error("Login Error:",e);
            Throwable ex = e.getCause();
            if(ex instanceof BugException) {
                if(ex.getMessage() != null) {
                    return new ResponseVO<>(SystemCode.InnerError.getCode(),SystemCode.AuthError.getMessage());
                }
            }else
            if(e instanceof IncorrectCredentialsException) {
                return  ResponseVO.fail(SystemCode.AuthError.getCode(),SystemCode.AuthError.getMessage());
            }else {
                return  ResponseVO.fail(SystemCode.InnerError.getCode(),SystemCode.InnerError.getMessage());
            }
        }

        return ResponseVO.ok();
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseVO<String> logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            subject.logout();
        }
        return ResponseVO.ok("登出成功");
    }

    @RequestMapping(value="/queryUserInfo",method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO<UserVO> queryUserInfo() {
        try {
//            UserVO user = userService.selectUserById("1");
            UserVO user = new UserVO();
            return  ResponseVO.ok(user);
        } catch (Exception e) {
            logger.error("queryUserInfo error:",e);
            return  ResponseVO.fail(SystemCode.InnerError.getCode(),SystemCode.InnerError.getMessage());
        }


    }


    @RequestMapping(value="/other", method = RequestMethod.GET)
    public String userCheck() {
//        ModelMap model, HttpServletRequest request
        System.out.println("other信息");
        return "success";
    }

}

