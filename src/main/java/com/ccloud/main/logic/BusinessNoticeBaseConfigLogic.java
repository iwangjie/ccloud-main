package com.ccloud.main.logic;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccloud.main.entity.BusinessNoticeBaseConfig;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.mapper.BusinessNoticeBaseConfigMapper;
import com.ccloud.main.service.IBusinessNoticeBaseConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Generator
 * @since 2020-02-22
 */
@Service
public class BusinessNoticeBaseConfigLogic {


    public BusinessNoticeBaseConfig getLastNoticeByAppId(BusinessUser currentUser, String appId) {
        return null;
    }

    public List<BusinessNoticeBaseConfig> getAllNoticeByAppId(BusinessUser currentUser, String appId) {
        return null;
    }
}
