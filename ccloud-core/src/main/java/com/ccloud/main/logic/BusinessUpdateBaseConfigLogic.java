package com.ccloud.main.logic;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccloud.main.entity.BusinessUpdateBaseConfig;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.mapper.BusinessUpdateBaseConfigMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 更新基本信息表 服务实现类
 * </p>
 *
 * @author Generator
 * @since 2020-02-25
 */
@Service
public class BusinessUpdateBaseConfigLogic {

    @Resource
    private BusinessUpdateBaseConfigMapper businessUpdateBaseConfigMapper;

    /**
     * 分页查询
     *
     * @param page
     * @param currentUser
     * @param appId
     * @return
     */
    public Page<BusinessUpdateBaseConfig> getPageUpdateByAppId(Page<BusinessUpdateBaseConfig> page, BusinessUser currentUser, Integer appId) {
        // 分页查询
        return businessUpdateBaseConfigMapper.selectPage(page, new LambdaQueryWrapper<BusinessUpdateBaseConfig>().
                eq(BusinessUpdateBaseConfig::getAppId, appId).
                eq(BusinessUpdateBaseConfig::getStatus, 0).
                orderByDesc(BusinessUpdateBaseConfig::getCreateTime));


    }


    public List<BusinessUpdateBaseConfig> getAllUpdateByAppId(BusinessUser currentUser, Integer appId) {
        return businessUpdateBaseConfigMapper.selectList(new LambdaQueryWrapper<BusinessUpdateBaseConfig>().
                eq(BusinessUpdateBaseConfig::getAppId, appId).
                eq(BusinessUpdateBaseConfig::getStatus, 0).
                orderByDesc(BusinessUpdateBaseConfig::getCreateTime));
    }
}
