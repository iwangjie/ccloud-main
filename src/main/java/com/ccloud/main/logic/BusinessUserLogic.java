package com.ccloud.main.logic;

import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.entity.BusinessUserRole;
import com.ccloud.main.mapper.BusinessUserMapper;
import com.ccloud.main.mapper.BusinessUserRoleMapper;
import com.ccloud.main.util.MD5Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 基础用户操作 Logic
 *
 * @author wangjie
 */
@Slf4j
@Service
public class BusinessUserLogic {

    @Resource
    private BusinessUserMapper businessUserMapper;

    @Resource
    private BusinessUserRoleMapper businessUserRoleMapper;


    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    public BusinessUser findByName(String username) {
        return businessUserMapper.findByUserName(username);
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     */
    public void register(String username, String password) {
        String password_salt = MD5Tools.parseStrToMd5L32(password + username);

        // 创建用户
        BusinessUser user = new BusinessUser();
        user.setUsername(username);
        user.setPassword(password_salt);
        businessUserMapper.insert(user);
        log.info("注册完成:{}", user);

        //创建角色关系
        BusinessUserRole businessUserRole = new BusinessUserRole();
        businessUserRole.setUserId(user.getId());
        businessUserRole.setRoleId(2);
        businessUserRoleMapper.insert(businessUserRole);
        log.info("权限分配完成:{}", businessUserRole);


    }
}
