package com.vea.springbootnew.web;
import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class HelloController {
 
    @RequestMapping("/hello")
    public String hello(Model m) {
    	m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        System.out.println("### /hello-----------");
        return "hello";
    }

    @RequestMapping("/exceptionHandle")
    public String exceptionHandle(Model m) throws Exception {
        m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        if(true){
            throw new Exception("some exception");
        }
        return "hello";
    }
}