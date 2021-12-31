package com.vae.demo03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class LoginController {

    @RequestMapping("test01")
    @ResponseBody
    public String test01(){
        System.out.println("------------newlogin------------");
        return "trest01";
    }

    @RequestMapping(value ="loginHandle",method = RequestMethod.POST)
    @ResponseBody
    public String loginHandle(String username,String password){
        System.out.println("------------loginHandle------------");
        System.out.println(username+":"+password);
        System.out.println("------------登陆成功------------");
        return "loginHandle";
    }
}
