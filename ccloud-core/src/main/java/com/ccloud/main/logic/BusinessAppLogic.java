package com.ccloud.main.logic;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccloud.main.entity.BusinessAppBaseConfig;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.mapper.BusinessAppBaseConfigMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangjie
 */
@Service
public class BusinessAppLogic {

    @Resource
    private BusinessAppBaseConfigMapper businessAppBaseConfigMapper;


    public List<BusinessAppBaseConfig> selectCurrUserAppList(BusinessUser currentUser) {
        return businessAppBaseConfigMapper.selectList(new LambdaQueryWrapper<BusinessAppBaseConfig>()
                .eq(BusinessAppBaseConfig::getStatus, 0)
                .eq(BusinessAppBaseConfig::getBusinessUserId, currentUser.getId())
        );

    }
}
