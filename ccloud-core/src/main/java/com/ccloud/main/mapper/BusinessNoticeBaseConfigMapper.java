package com.ccloud.main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccloud.main.entity.BusinessNoticeBaseConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2020-02-22
 */
public interface BusinessNoticeBaseConfigMapper extends BaseMapper<BusinessNoticeBaseConfig> {

    BusinessNoticeBaseConfig getLastNoticeByAppId(@Param("userId") Integer userId, @Param("appId") Integer appId);

    List<BusinessNoticeBaseConfig> getAllNoticeByAppId(@Param("userId") Integer userId, @Param("appId") Integer appId);

}
