package com.ccloud.main.logic;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccloud.main.entity.BusinessLeavemessageMain;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.mapper.BusinessLeavemessageMainMapper;
import com.ccloud.main.pojo.query.LeaveMessageQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 留言反馈
 *
 * @author wangjie
 */
@Service
@Slf4j
public class BusinessLeaveMessageLogic {

    @Resource
    private BusinessLeavemessageMainMapper businessLeavemessageMainMapper;


    /**
     * 根据 APP ID 分页获取留言反馈
     *
     * @param leaveMessageQueryVo
     * @param currentUser
     * @return
     */
    public IPage<BusinessLeavemessageMain> getPageLeaveMessageByAppId(LeaveMessageQueryVo leaveMessageQueryVo, BusinessUser currentUser) {
        Page<BusinessLeavemessageMain> page = new Page<>(leaveMessageQueryVo.getPageNum(), leaveMessageQueryVo.getPageSize());
        Page<BusinessLeavemessageMain> businessLeaveMessageMainPage = businessLeavemessageMainMapper.selectPage(page, new LambdaQueryWrapper<BusinessLeavemessageMain>()
                .eq(BusinessLeavemessageMain::getStatus, 0)
                .eq(BusinessLeavemessageMain::getAppId, leaveMessageQueryVo.getAppId())
        );

        return businessLeaveMessageMainPage;
    }
}
