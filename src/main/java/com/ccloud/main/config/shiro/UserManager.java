package com.ccloud.main.config.shiro;

import com.ccloud.main.entity.BusinessUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * 用户对象缓存管理
 *
 * @author wangjie
 */
public class UserManager {
    public static final String CURRENT_USER = "CURRENT_USER";

    public static BusinessUser getCurrentUser() {
        Object object = getAttribute(CURRENT_USER);
        if (object != null && object instanceof BusinessUser) {
            return (BusinessUser) object;
        }
        return null;
    }

    public static void setCurrentUser(BusinessUser user) {
        setAttribute(CURRENT_USER, user);
    }

    private static void setAttribute(String key, Object value) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Session session = subject.getSession();
            session.setAttribute(key, value);
        }
    }

    private static Object getAttribute(String key) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Session session = subject.getSession();
            return session.getAttribute(key);
        }
        return null;
    }
}
