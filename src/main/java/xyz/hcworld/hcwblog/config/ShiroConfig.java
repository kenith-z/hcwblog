package xyz.hcworld.hcwblog.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.hcworld.hcwblog.shiro.AccountRealm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: ShiroConfig
 * @Author: 张红尘
 * @Date: 2020/9/19 17:59
 * @Version： 1.0
 */
@Slf4j
@Configuration
public class ShiroConfig {
    /**
     * 安全管理器
     *
     * @param accountRealm
     * @return
     */
    @Bean
    public SecurityManager securityManager(AccountRealm accountRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm);
        log.info("--------------------->securityManager注入成功");
        return securityManager;
    }

    /**
     * shiro过滤器
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        //过滤器工厂
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        // 配置登录URl
        filterFactoryBean.setLoginUrl("/login");
        // 配置登录成功跳转的URL
        filterFactoryBean.setSuccessUrl("/user/center");
        filterFactoryBean.setUnauthorizedUrl("/error/403");
        /* 有序哈希表
         * 配置URL
         * 1.authc为拦截
         * 2.anon放行
         */
        Map<String, String> hasMap = new LinkedHashMap<>();
        hasMap.put("/user/**", "authc");
        hasMap.put("/login", "anon");

        filterFactoryBean.setFilterChainDefinitionMap(hasMap);
        return filterFactoryBean;
    }

}
