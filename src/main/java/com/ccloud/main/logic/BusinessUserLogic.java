package com.ccloud.main.logic;

import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.mapper.BusinessUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 基础用户操作 Logic
 *
 * @author wangjie
 */
@Service
public class BusinessUserLogic {

    @Resource
    private BusinessUserMapper businessUserMapper;


    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    public BusinessUser findByName(String username) {
        return businessUserMapper.findByUserName(username);
    }
}
