package com.ccloud.main.config.shiro;

import com.ccloud.main.config.BeanHeader;
import com.ccloud.main.config.jwt.JwtToken;
import com.ccloud.main.config.jwt.JwtUtil;
import com.ccloud.main.config.jwt.client.ClientJwtUtil;
import com.ccloud.main.config.jwt.pc.PcJwtUtil;
import com.ccloud.main.util.CloudUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
    private JwtUtil jwtUtil;
    private String REQUEST_TYPE_PC = "CC-Authorization";
    private String REQUEST_TYPE_CLIENT = "CL-Authorization";

    public UserRealm(ShiroJwtAuthorization shiroJwtAuthorization) {
        this.shiroJwtAuthorization = shiroJwtAuthorization;

    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (!CloudUtil.isClient()) {
            jwtUtil = BeanHeader.getBean(PcJwtUtil.class);
        } else {
            jwtUtil = BeanHeader.getBean(ClientJwtUtil.class);
        }
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userId = jwtUtil.getUserId(principals.toString());
        return shiroJwtAuthorization.getAuthorizationInfo(userId);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {

        String token = (String) auth.getCredentials();
        String userToken;

        userToken = jwtUtil.getUserId(token);
        if (userToken == null) {
            throw new ShiroJwtException("token invalid");
        }
        if (!shiroJwtAuthorization.verifyUser(userToken, token)) {
            throw new ShiroJwtException("verify user error!");
        }
        if (!jwtUtil.verify(token, userToken)) {
            throw new ShiroJwtException("token verify error!");
        }

        // 设置当前用户
        try {
            JsonNode jsonNode = shiroJwtAuthorization.objectMapper.readTree(userToken);
            int id = jsonNode.get("id").asInt();
            UserManager.setCurrentUser(shiroJwtAuthorization.iBusinessUserService.getById(id));
            UserManager.setClientCurrentUser(shiroJwtAuthorization.iClientUserService.getById(id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new SimpleAuthenticationInfo(token, token, "user_realm");
    }
}
