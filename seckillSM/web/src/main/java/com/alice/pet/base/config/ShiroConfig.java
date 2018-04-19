package com.alice.pet.base.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.alice.pet.base.common.constants.RedisConstants;
import org.apache.shiro.mgt.SecurityManager;
import com.alice.pet.base.shiro.*;
import com.alice.pet.base.shiro.AuthcRealm;
import com.alice.pet.base.utils.PasswordUtil;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro相关配置
 *
 * @author xudong.cheng
 * @date 2018/1/16 下午4:40
 */
@Configuration
public class ShiroConfig {

    /**
     * Shiro默认提供了三种 AuthenticationStrategy实现:
     * AtLeastOneSuccessfulStrategy :其中一个通过则成功
     * FirstSuccessfulStrategy :其中一个通过则成功，但只返回第一个通过的Realm提供的验证信息
     * AllSuccessfulStrategy :凡是配置到应用中的Realm都必须全部通过
     * authenticationStrategy
     */
    @Bean
    public AuthenticationStrategy authenticationStrategy() {
        return new FirstSuccessfulStrategy();
    }

    @Bean("authcCredentialsMatcher")
    public CredentialsMatcher authcCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(PasswordUtil.HASH_ALGORITHM_NAME);
        credentialsMatcher.setHashIterations(PasswordUtil.HASH_ITERATIONS);
        return credentialsMatcher;
    }

    @Bean("unionCredentialsMatcher")
    public CredentialsMatcher unionCredentialsMatcher() {
        return (token, info) -> true;
    }

    @Bean
    public AuthcRealm authcRealm(
            @Qualifier("authcCredentialsMatcher") CredentialsMatcher credentialsMatcher,
            CacheManager cacheManager
    ) {
        AuthcRealm realm = new AuthcRealm();
        realm.setCredentialsMatcher(credentialsMatcher);
        realm.setCacheManager(cacheManager);
        return realm;
    }

    @Bean
    public UnionRealm unionRealm(
            @Qualifier("unionCredentialsMatcher") CredentialsMatcher credentialsMatcher,
            CacheManager cacheManager
    ) {
        UnionRealm realm = new UnionRealm();
        realm.setCredentialsMatcher(credentialsMatcher);
        realm.setCacheManager(cacheManager);
        return realm;
    }

    @Bean("realmsMap")
    public Map<String, Realm> realmsMap(
            AuthcRealm authcRealm,
            UnionRealm unionRealm
    ) {
        Map<String, Realm> realmsMap = new HashMap<>(2);
        realmsMap.put(AuthcRealm.REALM_NAME, authcRealm);
        realmsMap.put(UnionRealm.REALM_NAME, unionRealm);
        return realmsMap;
    }

    @Bean
    public Authenticator authenticator(
            @Qualifier("realmsMap") Map<String, Realm> realmsMap,
            AuthenticationStrategy authenticationStrategy
    ) {
        MultiRealmAuthenticator authenticator = new MultiRealmAuthenticator();
        authenticator.setRealms(realmsMap.values());
        authenticator.setDefinedRealms(realmsMap);
        authenticator.setAuthenticationStrategy(authenticationStrategy);
        return authenticator;
    }

    @Bean
    public Authorizer authorizer(
            @Qualifier("realmsMap") Map<String, Realm> realmsMap
    ) {
        MultiRealmAuthorizer authorizer = new MultiRealmAuthorizer();
        authorizer.setDefinedRealms(realmsMap);
        return authorizer;
    }

    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new RandomSessionIdGenerator();
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisManager redisManager(RedisProperties redisProperties) {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisProperties.getHost());
        redisManager.setPort(redisProperties.getPort());
        redisManager.setExpire(RedisConstants.SPRING_SESSION_EXPIRE);
        redisManager.setTimeout(redisProperties.getTimeout());
        redisManager.setPassword(redisProperties.getPassword());
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setKeyPrefix(RedisConstants.SPRING_SESSION_MY);
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public SessionDAO sessionDAO(RedisManager redisManager, SessionIdGenerator sessionIdGenerator) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        redisSessionDAO.setKeyPrefix(RedisConstants.SPRING_SESSION_MY);
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator);
        return redisSessionDAO;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        /**
         * anon（匿名）  -org.apache.shiro.web.filter.authc.AnonymousFilter
         * authc（身份验证）-org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         * authcBasic（http基本验证）-org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
         * logout（退出）-org.apache.shiro.web.filter.authc.LogoutFilter
         * noSessionCreation（不创建session）-org.apache.shiro.web.filter.session.NoSessionCreationFilter
         * perms(许可验证)-org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
         * port（端口验证）-org.apache.shiro.web.filter.authz.PortFilter
         * rest(rest方面)-org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
         * roles（权限验证）-org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
         * ssl（ssl方面）-org.apache.shiro.web.filter.authz.SslFilter
         * member（用户方面）-org.apache.shiro.web.filter.authc.UserFilter
         * user-表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
         */

        chainDefinition.addPathDefinition("/static/**", "anon");
        chainDefinition.addPathDefinition("/druid/**", "anon");
        chainDefinition.addPathDefinition("/metrics/**", "anon");
        chainDefinition.addPathDefinition("/health/**", "anon");

        chainDefinition.addPathDefinition("/error/**", "anon");
        chainDefinition.addPathDefinition("/login", "anon");
        chainDefinition.addPathDefinition("/adminLogin", "anon");
        chainDefinition.addPathDefinition("/logout", "anon");
        chainDefinition.addPathDefinition("/captcha", "anon");

        chainDefinition.addPathDefinition("/**", "custom");
        return chainDefinition;
    }

    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        //单位为毫秒（1秒=1000毫秒） 3600000毫秒为1个小时
        sessionManager.setSessionValidationInterval(RedisConstants.SPRING_SESSION_EXPIRE * 1000);
        //3600000 milliseconds = 1 hour
        sessionManager.setGlobalSessionTimeout(RedisConstants.SPRING_SESSION_EXPIRE * 1000);
        Cookie cookie = new SimpleCookie("si");
        //more secure, protects against XSS attacks
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    @Bean(name = "securityManager")
    public SecurityManager securityManager(
            Authenticator authenticator,
            Authorizer authorizer,
            CacheManager cacheManager,
            SessionManager sessionManager
    ) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setAuthenticator(authenticator);
        manager.setAuthorizer(authorizer);
        manager.setCacheManager(cacheManager);
        manager.setSessionManager(sessionManager);
        return manager;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(
            SecurityManager manager,
            ShiroFilterChainDefinition shiroFilterChainDefinition
    ) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/adminLogin");
        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/error/403");
        bean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

        Map<String, Filter> filters = new LinkedHashMap<>(2);
        filters.put("custom", new CustomAuthenticationFilter());
        bean.setFilters(filters);

        return bean;
    }

    /**
     * FilterRegistrationBean
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetBeanName("shiroFilter");
        proxy.setTargetFilterLifecycle(true);
        filterRegistrationBean.setFilter(proxy);
//        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR);
        return filterRegistrationBean;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @ConditionalOnMissingBean
    @Bean(name = "defaultAdvisorAutoProxyCreator")
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * ShiroDialect thymeleaf里使用shiro的标签
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


}
