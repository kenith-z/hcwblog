package xyz.hcworld.hcwblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import xyz.hcworld.hcwblog.service.*;
import xyz.hcworld.hcwblog.shiro.AccountProfile;
import xyz.hcworld.hcwblog.shiro.AccountRealm;
import xyz.hcworld.hcwblog.util.RedisUtil;

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
    /**
     * 文章服务
     */
    @Autowired
    PostService postService;
    /**
     * 评论服务
     */
    @Autowired
    CommentService commentService;
    /**
     * 用户服务
     */
    @Autowired
    UserService userService;

    @Autowired
    AccountRealm accountRealm;
    /**
     * 用户消息服务
     */
    @Autowired
    UserMessageService messageService;
    /**
     * 文章收藏服务
     */
    @Autowired
    UserCollectionService userCollectionService;
    /**
     * 文章类型服务
     */
    @Autowired
    CategoryService categoryService;
    /**
     * 通用服务
     */
    @Autowired
    CurrencyService currencyService;
    /**
     * WebSocket服务
     */
    @Autowired
    WssService wssService;
    /**
     * 搜索服务
     */
    @Autowired
    SearchService searchService;

    /**
     * mq
     */
    @Autowired
    AmqpTemplate amqpTemplate;

    /**
     * mq
     */
    @Autowired
    ChatService chatService;
    /**
     * redis工具类
     */
    @Autowired
    RedisUtil redisUtil;

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
