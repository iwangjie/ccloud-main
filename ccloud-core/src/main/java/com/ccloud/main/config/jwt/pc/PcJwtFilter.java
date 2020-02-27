package com.ccloud.main.config.jwt.pc;

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
public class PcJwtFilter extends BasicHttpAuthenticationFilter {

    private ShiroJwtAuthorization shiroJwtAuthorization;
    private PcJwtUtil pcJwtUtil;

    public PcJwtFilter(PcJwtUtil pcJwtUtil) {
        this.shiroJwtAuthorization = pcJwtUtil.shiroJwtAuthorization;
        this.pcJwtUtil = pcJwtUtil;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(pcJwtUtil.shiroJwtPcProperties.getHeader());
        return authorization != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(pcJwtUtil.shiroJwtPcProperties.getHeader());
        authorization = authorization.replaceAll("(?i)" + pcJwtUtil.shiroJwtPcProperties.getPrefix(), "");
        JwtToken token = new JwtToken(authorization);
        //verify token
        String userId = pcJwtUtil.getUserId(authorization);
        if (userId == null) {
            return false;
        }
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
