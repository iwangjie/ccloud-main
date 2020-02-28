package com.ccloud.main.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccloud.main.entity.BusinessResource;
import com.ccloud.main.entity.BusinessRole;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.entity.ClientUser;
import com.ccloud.main.logic.BusinessResourceLogic;
import com.ccloud.main.logic.BusinessRoleLogic;
import com.ccloud.main.service.IBusinessUserService;
import com.ccloud.main.service.IClientUserService;
import com.ccloud.main.util.CloudUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ShiroJwtAuthorization
 *
 * @author : wangjie
 */
@RestControllerAdvice
@Configuration
public class ShiroJwtAuthorization implements ShiroJwtAuthorizationImp {

    @Resource
    private IBusinessUserService iBusinessUserService;

    @Resource
    private IClientUserService iClientUserService;


    @Resource
    private BusinessRoleLogic businessRoleLogic;

    @Resource
    private BusinessResourceLogic businessResourceLogic;

    @Resource
    private ObjectMapper objectMapper;


    /**
     * Get secret key according to user id
     *
     * @param userId user id
     * @return secret
     */
    @Override
    public String getSecret(String userId) {
        return null;
    }

    /**
     * Get authorization info according to user id
     *
     * @param userId user id
     * @return authorizationInfo
     */
    @Override
    public SimpleAuthorizationInfo getAuthorizationInfo(String userId) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        try {
            JsonNode tokenNode = objectMapper.readTree(userId);
            if (tokenNode != null) {
                String id = tokenNode.get("id").asText();

                if (CloudUtil.isClient()) {
                    //移动端
                    ClientUser user = iClientUserService.getOne(new LambdaQueryWrapper<ClientUser>().eq(ClientUser::getId, id).eq(ClientUser::getStatus, 0));
                    UserManager.setClientCurrentUser(user);
                } else if (!CloudUtil.isClient()) {
                    //PC端
                    BusinessUser user = iBusinessUserService.getOne(new LambdaQueryWrapper<BusinessUser>().eq(BusinessUser::getId, id).eq(BusinessUser::getStatus, 0));
                    UserManager.setCurrentUser(user);
                    //用户的角色集合
                    List<BusinessRole> businessRoles = businessRoleLogic.selectByUserId(user.getId());

                    Set<String> roleSet = new HashSet<>();
                    Set<String> permissions = new HashSet<>();
                    for (BusinessRole businessRole : businessRoles) {
                        roleSet.add(businessRole.getRoleCode());
                        List<BusinessResource> businessResources = businessResourceLogic.selectByRoleId(businessRole.getId());
                        permissions = businessResources.stream().map(businessResource -> businessResource.getResourceCode()).collect(Collectors.toSet());
                    }
                    simpleAuthorizationInfo.addStringPermissions(permissions);
                    return simpleAuthorizationInfo;

                } else {
                    // 如果匹配错误，那么无任何权限
                    return simpleAuthorizationInfo;
                }

            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return simpleAuthorizationInfo;
        }

        return simpleAuthorizationInfo;
    }


    /**
     * verify user legitimacy
     *
     * @param userId user id
     * @param token  token
     * @return boolean
     */
    @Override
    public boolean verifyUser(String userId, String token) {
        return true;
    }

    /**
     * Authentication exception handel
     *
     * @param request                 ServletRequest
     * @param response                ServletResponse
     * @param authenticationException AuthenticationException
     */
    @Override
    public void authenticationExceptionHandel(ServletRequest request, ServletResponse
            response, AuthenticationException authenticationException) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonObject = new HashMap<>(2);
        jsonObject.put("code", 401);
        jsonObject.put("msg", authenticationException.getMessage());
        try {
            String result = mapper.writeValueAsString(jsonObject);
            PrintWriter out = null;
            HttpServletResponse res = (HttpServletResponse) response;
            try {
                res.setCharacterEncoding("UTF-8");
                res.setContentType("application/json");
                out = response.getWriter();
                out.println(result);
            } catch (Exception ignored) {
            } finally {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }
        } catch (JsonProcessingException ignored) {
        }
    }

    /**
     * Authorization exception handel
     *
     * @param request                ServletRequest
     * @param response               ServletResponse
     * @param authorizationException AuthenticationException
     */
    @ExceptionHandler(value = AuthorizationException.class)
    @Override
    public void authorizationExceptionHandel(HttpServletRequest request, HttpServletResponse
            response, AuthorizationException authorizationException) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonObject = new HashMap<>(2);
        jsonObject.put("code", 403);
        jsonObject.put("msg", authorizationException.getMessage());
        try {
            String result = mapper.writeValueAsString(jsonObject);
            PrintWriter out = null;
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                out = response.getWriter();
                out.println(result);
            } catch (Exception ignored) {
            } finally {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }
        } catch (JsonProcessingException ignored) {
        }
    }

    /**
     * Shiro filter factory bean setting
     *
     * @param factoryBean ShiroFilterFactoryBean
     */
    @Override
    public void shiroFilterFactoryBean(ShiroFilterFactoryBean factoryBean) {

    }
}
