package com.ccloud.main.config.shiro;

import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.logic.BusinessUserLogic;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * 赋值 Current User
 *
 * @author wangjie
 */
public class SettingFilter extends AuthorizationFilter {


    @Autowired
    private BusinessUserLogic businessUserLogic;


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (subject == null) {
            return false;
        }
        if (subject.isRemembered() && UserManager.getCurrentUser() == null) {
            BusinessUser user = businessUserLogic.findByName(subject.getPrincipal().toString());
            if (user != null) {
                UserManager.setCurrentUser(user);
            }
        }
        return true;
    }
}
