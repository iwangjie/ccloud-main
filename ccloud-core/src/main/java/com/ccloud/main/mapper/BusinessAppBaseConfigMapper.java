package com.ccloud.main.mapper;

import com.ccloud.main.entity.BusinessAppBaseConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccloud.main.entity.BusinessNoticeBaseConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * B端应用基本信息配置表 Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2020-02-22
 */
public interface BusinessAppBaseConfigMapper extends BaseMapper<BusinessAppBaseConfig> {

    BusinessNoticeBaseConfig getLastNoticeByAppId(@Param("userId") Integer userId, @Param("appId") String appId);

    List<BusinessNoticeBaseConfig> getAllNoticeByAppId(@Param("userId") Integer userId, @Param("appId") String appId);
}
