package com.ccloud.main.config;

import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessAppBaseConfig;
import com.ccloud.main.service.IBusinessAppBaseConfigService;
import com.ccloud.main.util.exception.ParamsErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * appId 权限拦截器
 *
 * @author wangjie
 */
@Configuration
@Slf4j
public class AppIdPermissionsInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private IBusinessAppBaseConfigService iBusinessAppBaseConfigService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String appId = (String) pathVariables.get("appId");
        log.info("appId:{}", appId);
        if (StringUtils.isBlank(appId)) {
            return true;
        }

        // 判断权限
        BusinessAppBaseConfig businessAppBaseConfig = iBusinessAppBaseConfigService.getById(appId);
        if (businessAppBaseConfig == null) {
            throw new ParamsErrorException();
        }
        if (businessAppBaseConfig.getBusinessUserId().equals(UserManager.getCurrentUser().getId())) {
            throw new ParamsErrorException();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
