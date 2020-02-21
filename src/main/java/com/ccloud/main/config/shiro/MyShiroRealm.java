package com.ccloud.main.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccloud.main.entity.BusinessResource;
import com.ccloud.main.entity.BusinessRole;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.logic.BusinessResourceLogic;
import com.ccloud.main.logic.BusinessRoleLogic;
import com.ccloud.main.logic.BusinessRoleResourceLogic;
import com.ccloud.main.logic.BusinessUserLogic;
import com.ccloud.main.service.IBusinessUserService;
import com.ccloud.main.util.MD5Tools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 实现AuthorizingRealm接口用户用户认证
 *
 * @author wangjie
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private IBusinessUserService IBusinessUserService;

    @Resource
    private BusinessUserLogic businessUserLogic;

    @Resource
    private BusinessRoleResourceLogic businessRoleResourceLogic;

    @Resource
    private BusinessRoleLogic businessRoleLogic;

    @Resource
    private BusinessResourceLogic businessResourceLogic;


    /**
     * 角色权限和对应权限添加
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取用户输入的登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        if (StringUtils.isBlank(name)) {
            return null;
        }
        //查询用户名称
        BusinessUser user = businessUserLogic.findByName(name);
        if (user == null) {
            log.info("用户不存在[]", name);
            return simpleAuthorizationInfo;
        }

        //用户的角色集合
        List<BusinessRole> businessRoles = businessRoleLogic.selectByUserId(user.getId());
        if (businessRoles == null) {
            log.info("用户角色不存在[]", user.getId());
            return simpleAuthorizationInfo;
        }

        Set<String> roleSet = new HashSet<String>();
        List<String> permissions = new ArrayList<>();
        for (BusinessRole businessRole : businessRoles) {
            roleSet.add(businessRole.getRoleCode());
            List<BusinessResource> businessResources = businessResourceLogic.selectByRoleId(businessRole.getId());
            permissions = businessResources.stream().map(businessResource -> businessResource.getResourceCode()).collect(Collectors.toList());
        }

        simpleAuthorizationInfo.setRoles(roleSet);
        simpleAuthorizationInfo.addStringPermissions(permissions);

        return simpleAuthorizationInfo;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        String password_salt = MD5Tools.parseStrToMd5L32(password + username);
        BusinessUser businessUser = IBusinessUserService.getOne(new LambdaQueryWrapper<BusinessUser>().eq(BusinessUser::getUsername, username).eq(BusinessUser::getPassword, password_salt));
        //用户不存在
        if (businessUser == null) {
            throw new AuthenticationException();
        }
        UserManager.setCurrentUser(businessUser);
        // 获取盐
        ByteSource salt = ByteSource.Util.bytes(username);
        String md5Pwd = new Md5Hash(password, salt).toHex();
        return new SimpleAuthenticationInfo(businessUser, md5Pwd, salt, getName());

    }
}
