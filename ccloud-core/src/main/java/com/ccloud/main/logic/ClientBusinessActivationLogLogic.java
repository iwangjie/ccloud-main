package com.ccloud.main.logic;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.entity.ClientBusinessActivationLog;
import com.ccloud.main.mapper.ClientBusinessActivationLogMapper;
import com.ccloud.main.pojo.query.ActivationLogQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 客户端激活码激活日志
 *
 * @author wangjie
 */
@Service
@Slf4j
public class ClientBusinessActivationLogLogic {
    @Resource
    private ClientBusinessActivationLogMapper clientBusinessActivationLogMapper;


    /**
     * 根据 appId 获取激活码激活日志
     *
     * @param activationLogQueryVo
     * @param currentUser
     * @return
     */
    public IPage<ClientBusinessActivationLog> getPageActivationLogByAppId(ActivationLogQueryVo activationLogQueryVo, BusinessUser currentUser) {
        Page<ClientBusinessActivationLog> page = new Page<>(activationLogQueryVo.getPageNum(), activationLogQueryVo.getPageSize());
        Page<ClientBusinessActivationLog> businessRequestLogPage = clientBusinessActivationLogMapper.selectPage(page, new LambdaQueryWrapper<ClientBusinessActivationLog>()
                .eq(ClientBusinessActivationLog::getStatus, 0)
                .eq(ClientBusinessActivationLog::getAppId, activationLogQueryVo.getAppId()));
        return businessRequestLogPage;
    }
}
