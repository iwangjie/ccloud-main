package com.ccloud.main.config.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author : wangjie
 */
public class ShiroJwtException extends AuthenticationException {

    public ShiroJwtException() {
    }

    public ShiroJwtException(String message) {
        super(message);
    }

    public ShiroJwtException(Throwable cause) {
        super(cause);
    }

    public ShiroJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
