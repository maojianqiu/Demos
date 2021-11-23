package com.vae.controller;

import com.vae.base.RestResponse;
import com.vae.base.SystemCode;
import com.vae.service.AuthenticationService;
import com.vae.viewmodel.UserLoginVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("LoginController")
@RequestMapping(value = "/api/user")
public class LoginController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    private final AuthenticationService authenticationService;

    @Autowired
    public LoginController( AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResponse login(@RequestBody UserLoginVM userLoginVM) {
        String token = authenticationService.login(userLoginVM.getUserName(), userLoginVM.getPassword());
        if (token == null) {
            return RestResponse.fail(SystemCode.AuthError.getCode(),SystemCode.AuthError.getMessage());
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RestResponse.ok(tokenMap);
    }

}
