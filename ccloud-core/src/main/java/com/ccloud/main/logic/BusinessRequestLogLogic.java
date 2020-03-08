package com.ccloud.main.logic;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccloud.main.entity.BusinessRequestLog;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.mapper.BusinessRequestLogMapper;
import com.ccloud.main.pojo.query.AccessLogQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class BusinessRequestLogLogic {

    @Resource
    private BusinessRequestLogMapper businessRequestLogMapper;

    /**
     * 根据 appId 查询所有日志
     *
     * @param accessLogQueryVo
     * @param currentUser
     * @return
     */
    public IPage<BusinessRequestLog> getPageAccessLogByAppId(AccessLogQueryVo accessLogQueryVo, BusinessUser currentUser) {
        Page<BusinessRequestLog> page = new Page<>(accessLogQueryVo.getPageNum(), accessLogQueryVo.getPageSize());
        Page<BusinessRequestLog> businessRequestLogPage = businessRequestLogMapper.selectPage(page, new LambdaQueryWrapper<BusinessRequestLog>()
                .eq(BusinessRequestLog::getStatus, 0)
                .eq(BusinessRequestLog::getAppId, accessLogQueryVo.getAppId()));
        return businessRequestLogPage;
    }
}
