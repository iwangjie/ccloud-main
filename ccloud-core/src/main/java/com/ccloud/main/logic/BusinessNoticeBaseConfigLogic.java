package com.ccloud.main.logic;

import com.ccloud.main.entity.BusinessNoticeBaseConfig;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.mapper.BusinessNoticeBaseConfigMapper;
import com.ccloud.main.pojo.query.NoticePageQueryVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

    public BusinessNoticeBaseConfig getLastNoticeByAppId(BusinessUser currentUser, Integer appId) {
        return businessNoticeBaseConfigMapper.getLastNoticeByAppId(currentUser.getId(), appId);
    }

    public List<BusinessNoticeBaseConfig> getAllNoticeByAppId(BusinessUser currentUser, Integer appId) {
        return businessNoticeBaseConfigMapper.getAllNoticeByAppId(currentUser.getId(), appId);
    }

    public Page<BusinessNoticeBaseConfig> getPageNoticeByAppId(NoticePageQueryVo noticePageQueryVo, BusinessUser currentUser) {
        Page<BusinessNoticeBaseConfig> configPage = PageHelper.startPage(noticePageQueryVo.getPageNum(), noticePageQueryVo.getPageSize())
                .doSelectPage(() -> businessNoticeBaseConfigMapper.getAllNoticeByAppId(currentUser.getId(), noticePageQueryVo.getAppId()));
        return configPage;
    }

    public BusinessNoticeBaseConfig getLastNoticeById(BusinessUser currentUser, Integer appId, Integer noticeId) {
        return businessNoticeBaseConfigMapper.selectById(noticeId);
    }
}
