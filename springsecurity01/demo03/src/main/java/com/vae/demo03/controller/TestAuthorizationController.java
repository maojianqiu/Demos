package com.vae.demo03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/autho")
public class TestAuthorizationController {

    // admin角色可以访问
    @RequestMapping("/admin")
    @ResponseBody
    public String adminTest1(){
        System.out.println("------------adminTest1------------");
        return "adminTest1~~";
    }

    // admin角色可以访问
    @RequestMapping("/admin/a")
    @ResponseBody
    public String adminTest2(){
        System.out.println("------------adminTest2------------");
        return "adminTest2~~";
    }

    //user角色可以访问
    @RequestMapping("/user")
    @ResponseBody
    public String userTest1(){
        System.out.println("------------userTest1------------");
        return "userTest1~~";
    }

    //user角色可以访问
    @RequestMapping("/user/a")
    @ResponseBody
    public String userTest2(){
        System.out.println("------------userTest2------------");
        return "userTest2~~";
    }

    //所有人都可以访问可以访问
    @RequestMapping("/all")
    @ResponseBody
    public String allTest1(){
        System.out.println("------------allTest1------------");
        return "allTest1~~";
    }

    //所有人都可以访问可以访问
    @RequestMapping("/all/a")
    @ResponseBody
    public String allTest2(){
        System.out.println("------------allTest2------------");
        return "allTest2~~";
    }

}
