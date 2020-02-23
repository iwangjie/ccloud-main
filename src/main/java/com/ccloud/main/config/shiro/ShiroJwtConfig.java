package com.ccloud.main.config.shiro;

import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessResource;
import com.ccloud.main.entity.BusinessRole;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.logic.BusinessResourceLogic;
import com.ccloud.main.logic.BusinessRoleLogic;
import com.ccloud.main.logic.BusinessUserLogic;
import com.ccloud.main.util.exception.HandleTokenException;
import com.louislivi.fastdep.shirojwt.shiro.FastDepShiroJwtAuthorization;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wangjie
 */
@Component
@Slf4j
public class ShiroJwtConfig extends FastDepShiroJwtAuthorization {


    @Resource
    private com.ccloud.main.service.IBusinessUserService IBusinessUserService;

    @Resource
    private BusinessRoleLogic businessRoleLogic;

    @Resource
    private BusinessResourceLogic businessResourceLogic;

    @Override
    public SimpleAuthorizationInfo getAuthorizationInfo(String userId) {
        String[] idAndPass = userId.split(":");
        BusinessUser user = IBusinessUserService.getById(idAndPass[0]);

        // 比对密码字段
        if (!idAndPass[1].equals(user.getPassword())) {
        }
        // 判断用户状态
        if (user.getStatus() != 0) {
            throw new HandleTokenException();
        }

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

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        log.info("user:[{}],permissions:[{}]", userId, permissions);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }
}

