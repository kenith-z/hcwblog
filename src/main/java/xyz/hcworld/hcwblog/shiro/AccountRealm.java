package xyz.hcworld.hcwblog.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.hcworld.hcwblog.service.UserService;
import xyz.hcworld.hcwblog.util.ConstantUtil;

/**
 * 自定义realm，完成登录授权和认证
 * @ClassName: AccountRealm
 * @Author: 张红尘
 * @Date: 2020/9/19 16:52
 * @Version： 1.0
 */
@Component
public class AccountRealm  extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     *用户授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AccountProfile profile = (AccountProfile) principalCollection.getPrimaryPrincipal();

        //超级管理员登录校验
        if(ConstantUtil.ADMIN_ID.equals(profile.getId()) && ConstantUtil.ADMIN_WEIGHT.equals(profile.getStatus())){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("admin");
            return info;
        }

        return null;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        // 登录
        AccountProfile profile = userService.login(usernamePasswordToken.getUsername(),String.valueOf(usernamePasswordToken.getPassword()));

        SecurityUtils.getSubject().getSession().setAttribute("profile",profile);

        // 传user属性，密码，当前realm的名称
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(profile,authenticationToken.getCredentials(),getName());
        return info;
    }

}
