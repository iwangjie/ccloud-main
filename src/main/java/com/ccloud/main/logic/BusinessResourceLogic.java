package com.ccloud.main.logic;

import com.ccloud.main.entity.BusinessResource;
import com.ccloud.main.mapper.BusinessResourceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户基础资源表 服务实现类
 * </p>
 *
 * @author Generator
 * @since 2020-02-21
 */
@Service
public class BusinessResourceLogic {

    @Resource
    private BusinessResourceMapper businessResourceMapper;

    /**
     * 根据角色id查询所有的权限
     *
     * @param id
     * @return
     */
    public List<BusinessResource> selectByRoleId(Integer id) {
        return businessResourceMapper.selectByRoleId(id);
    }
}
