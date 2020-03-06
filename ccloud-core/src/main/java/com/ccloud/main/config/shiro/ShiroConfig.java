package com.ccloud.main.config.shiro;

import com.ccloud.main.config.jwt.client.ClientJwtFilter;
import com.ccloud.main.config.jwt.client.ClientJwtUtil;
import com.ccloud.main.config.jwt.pc.PcJwtFilter;
import com.ccloud.main.config.jwt.pc.PcJwtUtil;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangjie
 */

@Configuration
public class ShiroConfig {

    /**
     * AuthorizingRealm
     *
     * @param shiroJwtAuthorization
     * @return AuthorizingRealm
     */
    @Bean
    public AuthorizingRealm authorizingRealm(ShiroJwtAuthorization shiroJwtAuthorization) {
        return new UserRealm(shiroJwtAuthorization);
    }

    /**
     * securityManager
     *
     * @param authorizingRealm AuthorizingRealm bean
     * @return securityManager
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(AuthorizingRealm authorizingRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authorizingRealm);
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        return manager;
    }

    /**
     * shiroFilter
     *
     * @param securityManager securityManager bean
     * @param pcJwtUtil       jwt util bean
     * @return shiroFilter
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager, ShiroJwtCommonProperties shiroJwtCommonProperties, PcJwtUtil pcJwtUtil, ClientJwtUtil clientJwtUtil) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // define your filter and name it as jwt
        Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("pcJwt", new PcJwtFilter(pcJwtUtil));
        filterMap.put("clientJwt", new ClientJwtFilter(clientJwtUtil));
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        /*
         * difine custom URL rule
         * http://shiro.apache.org/web.html#urls-
         */
        Map<String, ShiroJwtCommonProperties.ShiroRole> filter = shiroJwtCommonProperties.getFilter();
        if (filter.size() > 0) {
            Map<String, String> filterRuleMap = filter.values().stream().
                    collect(Collectors.toMap(ShiroJwtCommonProperties.ShiroRole::getPath, ShiroJwtCommonProperties.ShiroRole::getRole));
            // 401 and 404 page does not forward to our filter
            factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        }
        if (shiroJwtCommonProperties.getFilterChainDefinitions() != null) {
            factoryBean.setFilterChainDefinitions(shiroJwtCommonProperties.getFilterChainDefinitions());
        }
        factoryBean.setLoginUrl(shiroJwtCommonProperties.getLoginUrl());
        factoryBean.setSuccessUrl(shiroJwtCommonProperties.getSuccessUrl());
        factoryBean.setUnauthorizedUrl(shiroJwtCommonProperties.getUnauthorizedUrl());
        pcJwtUtil.shiroJwtAuthorization.shiroFilterFactoryBean(factoryBean);
        return factoryBean;
    }

    /**
     * LifecycleBeanPostProcessor
     *
     * @return LifecycleBeanPostProcessor
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * AuthorizationAttributeSourceAdvisor
     *
     * @param securityManager securityManager bean
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
