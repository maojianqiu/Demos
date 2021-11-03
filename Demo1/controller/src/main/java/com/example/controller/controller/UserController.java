package com.example.controller.controller;

import com.example.entity.User;
import com.example.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/queryUser")
    public  List<User> queryUser(ModelMap map){
        List<User> list= userService.queryService();
        map.put("list",list);
        System.out.println("123");
        return list;
    }
}

