package com.ccloud.main.config.jwt.pc;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ccloud.main.config.shiro.ShiroJwtAuthorization;
import com.ccloud.main.config.shiro.pc.ShiroJwtPcProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Date;

import static org.apache.shiro.SecurityUtils.getSubject;


/**
 * JWTUtil
 *
 * @author : wangjie
 */
@Configuration
public class PcJwtUtil {
    @Resource
    public ShiroJwtPcProperties shiroJwtPcProperties;
    @Resource
    public ShiroJwtAuthorization shiroJwtAuthorization;

    /**
     * verify token
     *
     * @param token token
     * @return true or false
     */
    public boolean verify(String token, String userId) {
        try {
            String secret = shiroJwtAuthorization.getSecret(userId) == null ? shiroJwtPcProperties.getSecret() : null;
            assert secret != null;
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject(userId)
                    .build();
            verifier.verify(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * get user id
     *
     * @return user id
     */
    public String getUserId() {
        try {
            DecodedJWT jwt = JWT.decode(getSubject().getPrincipal().toString());
            return jwt.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get user id
     *
     * @param token jwt token
     * @return user id
     */
    public String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * generate token
     *
     * @param userId user identifier
     * @return token
     */
    public String sign(String userId) {
        Date date = new Date(System.currentTimeMillis() + shiroJwtPcProperties.getExpireTime());
        String secret = shiroJwtAuthorization.getSecret(userId) == null ? shiroJwtPcProperties.getSecret() : null;
        assert secret != null;
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return shiroJwtPcProperties.getSignPrefix() + JWT.create()
                .withSubject(userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
