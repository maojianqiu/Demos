package com.vae.controller.admin;

import com.vae.base.RestResponse;
import com.vae.controller.BaseApiController;
import com.vae.domain.model.User;
import com.vae.service.AuthenticationService;
import com.vae.service.UserService;
import com.vae.viewmodel.UserResponseVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author 武汉思维跳跃科技有限公司
 */
@RestController("AdminUserController")
@RequestMapping(value = "/api/admin/user")
public class UserController extends BaseApiController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService,  AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> current() {
        User user = getCurrentUser();
        UserResponseVM userVm = UserResponseVM.from(user);
        return RestResponse.ok(userVm);
    }


    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public RestResponse<String> test1() {
        System.out.println("00");
        return RestResponse.ok("test~~~~~~1~~~~~~~~GET~~~~~~");
    }

    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public RestResponse<String> test2() {
        return RestResponse.ok("test~~~~~~2~~~~~~~~~POST~~~~~");
    }

    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    public RestResponse<String> test3() {
        return RestResponse.ok("test~~~~~~3~~~~~~~~GET~~~~~~");
    }

}
