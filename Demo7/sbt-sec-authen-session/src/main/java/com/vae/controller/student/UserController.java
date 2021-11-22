package com.vae.controller.student;

import com.vae.base.RestResponse;
import com.vae.controller.BaseApiController;
import com.vae.domain.User;
import com.vae.viewmodel.UserResponseVM;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;


/**
 * @author 武汉思维跳跃科技有限公司
 */
@RestController("StudentUserController")
@RequestMapping(value = "/api/student/user")
public class UserController extends BaseApiController {

//    private final UserService userService;
//    private final AuthenticationService authenticationService;
//
//    @Autowired
//    public UserController(UserService userService, AuthenticationService authenticationService) {
//        this.userService = userService;
//        this.authenticationService = authenticationService;
//    }



    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> current() {
        User user = getCurrentUser();
        UserResponseVM userVm = UserResponseVM.from(user);
        return RestResponse.ok(userVm);
    }

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public RestResponse<String> test1() {
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

    @RequestMapping(value = "/test4", method = RequestMethod.GET)
    public RestResponse<String> test4() {
        return RestResponse.ok("test~~~~~~4~~~~~~~GET~~~~~~~");
    }



}
