package org.vae.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import org.vae.exception.BugException;
import org.vae.model.UserVO;
import org.vae.service.IUserService;

/**
 * 自定义Realm
 * @author longwentao
 *
 */
public class UserShiroRealm extends AuthorizingRealm{

    private IUserService userService;

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        if(username == null) {
            throw new BugException("未登录");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<String>();
        Set<String> stringPermissions = new HashSet<String>();
        roles.add("USER");
        stringPermissions.add("USER:DELETE");//角色:权限

        info.setRoles(roles);//角色可以通过数据库查询得到
        info.setStringPermissions(stringPermissions);//权限可以通过数据库查询得到

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken autToken) throws AuthenticationException {

        UsernamePasswordToken userPwdToken = (UsernamePasswordToken) autToken;
        String userName = userPwdToken.getUsername();

//        UserVO user = userService.selectUserByUserName(userName);
        UserVO user = new UserVO("1","no1","123");
        if (null == user) {
            throw new BugException("未知账号");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(),
                user.getPassword().toCharArray(), getName());

        return authenticationInfo;
    }
}
