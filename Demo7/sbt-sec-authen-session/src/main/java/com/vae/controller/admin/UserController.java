package com.vae.controller.admin;

import com.github.pagehelper.PageInfo;
import com.vae.base.RestResponse;
import com.vae.controller.BaseApiController;
import com.vae.domain.User;
import com.vae.service.AuthenticationService;
import com.vae.service.UserService;
import com.vae.util.PageInfoHelper;
import com.vae.viewmodel.UserCreateVM;
import com.vae.viewmodel.UserPageRequestVM;
import com.vae.viewmodel.UserResponseVM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;


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


    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<UserResponseVM>> pageList(@RequestBody UserPageRequestVM model) {
        PageInfo<User> pageInfo = userService.userPage(model);
        PageInfo<UserResponseVM> page = PageInfoHelper.copyMap(pageInfo, d -> UserResponseVM.from(d));
        return RestResponse.ok(page);
    }


    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> select(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        UserResponseVM userVm = UserResponseVM.from(user);
        return RestResponse.ok(userVm);
    }

    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> current() {
        User user = getCurrentUser();
        UserResponseVM userVm = UserResponseVM.from(user);
        return RestResponse.ok(userVm);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<User> edit(@RequestBody UserCreateVM model) {
        if (model.getId() == null) {  //create
            User existUser = userService.getUserByUserName(model.getUserName());
            if (null != existUser) {
                return new RestResponse<>(2, "用户已存在");
            }
            if (StringUtils.isBlank(model.getPassword())) {
                return new RestResponse<>(3, "密码不能为空");
            }
        }
        if (StringUtils.isBlank(model.getBirthDay())) {
            model.setBirthDay(null);
        }
        User user = new User();
        BeanUtils.copyProperties(model,user);
//        User user = modelMapper.map(model, User.class);

        if (model.getId() == null) {
            String encodePwd = authenticationService.pwdEncode(model.getPassword());
            user.setPassword(encodePwd);
            user.setUserUuid(UUID.randomUUID().toString());
            user.setCreateTime(new Date());
            user.setLastActiveTime(new Date());
            user.setDeleted(false);
            userService.insertByFilter(user);
        } else {
            if (!StringUtils.isBlank(model.getPassword())) {
                String encodePwd = authenticationService.pwdEncode(model.getPassword());
                user.setPassword(encodePwd);
            }
            user.setModifyTime(new Date());
            userService.updateByIdFilter(user);
        }
        return RestResponse.ok(user);
    }


//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public RestResponse update(@RequestBody @Valid UserUpdateVM model) {
//        User user = userService.selectById(getCurrentUser().getId());
//        modelMapper.map(model, user);
//        user.setModifyTime(new Date());
//        userService.updateByIdFilter(user);
//        return RestResponse.ok();
//    }

//
//    @RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.POST)
//    public RestResponse<Integer> changeStatus(@PathVariable Integer id) {
//        User user = userService.getUserById(id);
//        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getStatus());
//        Integer newStatus = userStatusEnum == UserStatusEnum.Enable ? UserStatusEnum.Disable.getCode() : UserStatusEnum.Enable.getCode();
//        user.setStatus(newStatus);
//        user.setModifyTime(new Date());
//        userService.updateByIdFilter(user);
//        return RestResponse.ok(newStatus);
//    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        user.setDeleted(true);
        userService.updateByIdFilter(user);
        return RestResponse.ok();
    }


//    @RequestMapping(value = "/selectByUserName", method = RequestMethod.POST)
//    public RestResponse<List<KeyValue>> selectByUserName(@RequestBody String userName) {
//        List<KeyValue> keyValues = userService.selectByUserName(userName);
//        return RestResponse.ok(keyValues);
//    }

}
