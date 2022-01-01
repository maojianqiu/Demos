package com.vae.demo03.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/check")
    @ResponseBody
    public String check(){
        System.out.println("------------admincheck------------");
        return "admincheck~~";
    }
}
