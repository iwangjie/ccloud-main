package com.ccloud.main.mapper;

import com.ccloud.main.entity.BusinessUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户基础信息表 Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2020-02-21
 */
public interface BusinessUserMapper extends BaseMapper<BusinessUser> {

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    @Select("select * from business_user where username = #{username} and status = 0")
    BusinessUser findByUserName(@Param("username") String username);
}
