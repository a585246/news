package com.cskaoyan.news.controller.filter;

import com.cskaoyan.news.shiro.AdminRealm;
import com.cskaoyan.news.shiro.shiroCached.MyCachedManager;
import com.sun.javafx.collections.MappingChange;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.*;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import  java.util.*;
@Configuration
public class ShiroConfiguration {

    @Autowired
    AdminRealm adminRealm;
    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根下目录的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/home");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        HashMap<String, String> ret = new HashMap<>();
        ret.put("/","anon");
        ret.put("/fonts/**","anon");
        ret.put("/images/**","anon");
        ret.put("/scripts/**","anon");
        ret.put("/styles/**","anon");
        ret.put("/login/","anon");
        ret.put("/logout/","logout");
        ret.put("/reg/","anon");
        ret.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(ret);
        //自定义拦截器


        // 权限控制map.
        // 配置不会被拦截的链接 顺序判断
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        // 从数据库获取动态的权限
        // filterChainDefinitionMap.put("/add", "perms[权限添加]");
        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //logout这个拦截器是shiro已经实现好了的。
        // 从数据库获取
        /*List<SysPermissionInit> list = sysPermissionInitService.selectAll();

        for (SysPermissionInit sysPermissionInit : list) {
            filterChainDefinitionMap.put(sysPermissionInit.getUrl(),
                    sysPermissionInit.getPermissionInit());
        }*/


        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myShiroRealm());
        //自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
       // 注入记住我管理器;
    /*    securityManager.setRememberMeManager(rememberMeManager());*/
        return securityManager;
}

    public AdminRealm myShiroRealm(){
        return  adminRealm;
    }
    public SessionManager sessionManager(){
        DefaultWebSessionManager defaultSessionManager = new DefaultWebSessionManager();
    defaultSessionManager.setGlobalSessionTimeout(10*24*3600);
    defaultSessionManager.setSessionIdCookie(cookie());
    return defaultSessionManager;
    }
    public CacheManager cacheManager(){
      return   new MyCachedManager();
    }
    public Cookie cookie(){
        SimpleCookie simpleCookie = new SimpleCookie("shiro.session");
    simpleCookie.setPath("/");
    return simpleCookie;
    }
}