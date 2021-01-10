package xyz.hcworld.hcwblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import xyz.hcworld.hcwblog.service.*;
import xyz.hcworld.hcwblog.shiro.AccountProfile;
import xyz.hcworld.hcwblog.shiro.AccountRealm;

import javax.servlet.http.HttpServletRequest;


/**
 * 公共控制器
 * @ClassName: BaseController
 * @Author: 张红尘
 * @Date: 2020/7/12 1:23
 * @Version： 1.0
 */
public class BaseController {
    @Autowired
    HttpServletRequest req;
    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;
    @Autowired
    AccountRealm accountRealm;

    @Autowired
    UserMessageService messageService;

    @Autowired
    UserCollectionService userCollectionService;

    @Autowired
    CategoryService categoryService;

    /**
     * 设置分页信息
     * @return
     */
    public Page getPage(){
        // 页数
        int pn = ServletRequestUtils.getIntParameter(req,"pn",1);
        // 每页条数
        int size = ServletRequestUtils.getIntParameter(req,"size",2);
        return new Page(pn,size);
    }

    public AccountProfile getProfile(){
        return (AccountProfile)SecurityUtils.getSubject().getPrincipal();
    }
    protected Long getProfileId(){
        return  getProfile().getId();
    }

    /**
     * 设置shiro授权信息（更新用户信息）
     * @param accountProfile
     */
    protected void setProfile(AccountProfile accountProfile ){
        Subject subject = SecurityUtils.getSubject();
        String realmName = subject.getPrincipals().getRealmNames().iterator().next();
        //第一个参数为用户名,第二个参数为realmName,test想要操作权限的用户
        SimplePrincipalCollection principals = new SimplePrincipalCollection(accountProfile,realmName);
        subject.runAs(principals);
    }

}
