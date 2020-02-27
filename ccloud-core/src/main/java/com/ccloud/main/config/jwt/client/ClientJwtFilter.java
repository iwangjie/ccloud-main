package com.ccloud.main.config.jwt.client;

import com.ccloud.main.config.jwt.JwtToken;
import com.ccloud.main.config.shiro.ShiroJwtAuthorization;
import com.ccloud.main.config.shiro.ShiroJwtException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * JWTFilter
 *
 * @author : wangjie
 */
public class ClientJwtFilter extends BasicHttpAuthenticationFilter {

    private ShiroJwtAuthorization shiroJwtAuthorization;
    private ClientJwtUtil clientJwtUtil;

    public ClientJwtFilter(ClientJwtUtil clientJwtUtil) {
        this.shiroJwtAuthorization = clientJwtUtil.shiroJwtAuthorization;
        this.clientJwtUtil = clientJwtUtil;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(clientJwtUtil.shiroJwtClientProperties.getHeader());
        return authorization != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(clientJwtUtil.shiroJwtClientProperties.getHeader());
        authorization = authorization.replaceAll("(?i)" + clientJwtUtil.shiroJwtClientProperties.getPrefix(), "");
        JwtToken token = new JwtToken(authorization);
        //verify token
        String tokenValue = clientJwtUtil.getUserId(authorization);
        if (tokenValue == null) {
            return false;
        }
//        try {
//            JsonNode jsonNode = clientJwtUtil.objectMapper.readTree(tokenValue);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return false;
//        }
        getSubject(request, response).login(token);
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                return executeLogin(request, response);
            } catch (AuthenticationException e) {
                this.shiroJwtAuthorization.authenticationExceptionHandel(request, response, e);
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws AuthenticationException {
        this.shiroJwtAuthorization.authenticationExceptionHandel(request, response, new ShiroJwtException("Access denied !"));
        return false;
    }
}
