package com.ccloud.main.mapper;

import com.ccloud.main.entity.BusinessNoticeBaseConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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

    BusinessNoticeBaseConfig getLastNoticeByAppId(Integer id, String appId);

    List<BusinessNoticeBaseConfig> getAllNoticeByAppId(Integer id, String appId);

}
