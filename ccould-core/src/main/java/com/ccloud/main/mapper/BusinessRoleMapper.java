package com.ccloud.main.mapper;

import com.ccloud.main.entity.BusinessRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户基础角色表 Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2020-02-21
 */
public interface BusinessRoleMapper extends BaseMapper<BusinessRole> {

    /**
     *
     * @param id
     * @return
     */
    List<BusinessRole> selectByUserId(@Param("id") Integer id);
}
