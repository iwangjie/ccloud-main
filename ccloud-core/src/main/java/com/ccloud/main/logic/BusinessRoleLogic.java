package com.ccloud.main.logic;

import com.ccloud.main.entity.BusinessRole;
import com.ccloud.main.mapper.BusinessRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户基础角色表 服务实现类
 * </p>
 *
 * @author Generator
 * @since 2020-02-21
 */
@Service
public class BusinessRoleLogic {

    @Resource
    private BusinessRoleMapper businessRoleMapper;

    public List<BusinessRole> selectByUserId(Integer id) {
        return businessRoleMapper.selectByUserId(id);
    }
}
