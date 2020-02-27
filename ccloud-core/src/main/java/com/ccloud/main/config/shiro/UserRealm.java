package com.ccloud.main.config.shiro;

import com.ccloud.main.config.jwt.JwtToken;
import com.ccloud.main.config.jwt.pc.PcJwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * UserRealm
 *
 * @author : wangjie
 */
public class UserRealm extends AuthorizingRealm {
    private ShiroJwtAuthorization shiroJwtAuthorization;
    private PcJwtUtil pcJwtUtil;

    public UserRealm(PcJwtUtil pcJwtUtil) {
        this.shiroJwtAuthorization = pcJwtUtil.shiroJwtAuthorization;
        this.pcJwtUtil = pcJwtUtil;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userId = pcJwtUtil.getUserId(principals.toString());
        return shiroJwtAuthorization.getAuthorizationInfo(userId);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        String userId = pcJwtUtil.getUserId(token);
        if (userId == null) {
            throw new ShiroJwtException("token invalid");
        }
        if (!shiroJwtAuthorization.verifyUser(userId, token)) {
            throw new ShiroJwtException("verify user error!");
        }
        if (!pcJwtUtil.verify(token, userId)) {
            throw new ShiroJwtException("token verify error!");
        }
        return new SimpleAuthenticationInfo(token, token, "user_realm");
    }
}
