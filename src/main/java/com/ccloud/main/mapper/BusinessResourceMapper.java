package com.ccloud.main.mapper;

import com.ccloud.main.entity.BusinessResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户基础资源表 Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2020-02-21
 */
public interface BusinessResourceMapper extends BaseMapper<BusinessResource> {

    /**
     * 根据角色 id 查询所有权限
     *
     * @param id
     * @return
     */
    List<BusinessResource> selectByRoleId(Integer id);
}
