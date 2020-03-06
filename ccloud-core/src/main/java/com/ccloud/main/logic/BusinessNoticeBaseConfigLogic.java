package com.ccloud.main.logic;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccloud.main.entity.BusinessNoticeBaseConfig;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.mapper.BusinessNoticeBaseConfigMapper;
import com.ccloud.main.pojo.query.NoticePageQueryVo;
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
//        return businessNoticeBaseConfigMapper.getAllNoticeByAppId(null, currentUser.getId(), appId);
        return null;
    }

    public IPage<BusinessNoticeBaseConfig> getPageNoticeByAppId(NoticePageQueryVo noticePageQueryVo, BusinessUser currentUser) {
        Page<BusinessNoticeBaseConfig> page = new Page<>(noticePageQueryVo.getPageNum(), noticePageQueryVo.getPageSize());
        IPage<BusinessNoticeBaseConfig> pageData = businessNoticeBaseConfigMapper.getAllNoticeByAppId(page, currentUser.getId(), noticePageQueryVo.getAppId());
        return pageData;
    }

    public BusinessNoticeBaseConfig getLastNoticeById(BusinessUser currentUser, Integer appId, Integer noticeId) {
        return businessNoticeBaseConfigMapper.selectById(noticeId);
    }
}
