package com.ccloud.main.logic;

import com.ccloud.main.entity.BusinessNoticeBaseConfig;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.mapper.BusinessNoticeBaseConfigMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    @Resource
    private BusinessNoticeBaseConfigMapper businessNoticeBaseConfigMapper;

    public BusinessNoticeBaseConfig getLastNoticeByAppId(BusinessUser currentUser, String appId) {
        return businessNoticeBaseConfigMapper.getLastNoticeByAppId(currentUser.getId(), appId);
    }

    public List<BusinessNoticeBaseConfig> getAllNoticeByAppId(BusinessUser currentUser, String appId) {
        return businessNoticeBaseConfigMapper.getAllNoticeByAppId(currentUser.getId(), appId);
    }

    public BusinessNoticeBaseConfig getLastNoticeById(BusinessUser currentUser, String appId, String noticeId) {
        return businessNoticeBaseConfigMapper.selectById(noticeId);
    }
}
