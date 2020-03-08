package com.ccloud.main.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessLeavemessageMain;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.logic.BusinessLeaveMessageLogic;
import com.ccloud.main.pojo.query.LeaveMessageQueryVo;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.util.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 留言反馈
 *
 * @author wangjie
 */
@RestController
@RequestMapping("feedback")
public class BusinessLeaveMessageController {


    @Resource
    private BusinessLeaveMessageLogic businessLeaveMessageLogic;

    /**
     * 分页获取留言反馈
     *
     * @param leaveMessageQueryVo
     * @return
     */
    @PostMapping("/page")
    public Result<Object> accessLogPage(@RequestBody LeaveMessageQueryVo leaveMessageQueryVo) {
        BusinessUser currentUser = UserManager.getCurrentUser();
        IPage<BusinessLeavemessageMain> iPage = businessLeaveMessageLogic.getPageLeaveMessageByAppId(leaveMessageQueryVo, currentUser);
        return ResultUtil.success(iPage);
    }

}
