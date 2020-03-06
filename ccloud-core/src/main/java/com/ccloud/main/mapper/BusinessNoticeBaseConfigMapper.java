package com.ccloud.main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccloud.main.entity.BusinessNoticeBaseConfig;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2020-02-22
 */
public interface BusinessNoticeBaseConfigMapper extends BaseMapper<BusinessNoticeBaseConfig> {

    BusinessNoticeBaseConfig getLastNoticeByAppId(@Param("userId") Integer userId, @Param("appId") Integer appId);

    IPage<BusinessNoticeBaseConfig> getAllNoticeByAppId(Page<BusinessNoticeBaseConfig> page, @Param("userId") Integer userId, @Param("appId") Integer appId);

}
