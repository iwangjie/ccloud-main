package com.ccloud.main.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessRequestLog;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.entity.ClientBusinessActivationLog;
import com.ccloud.main.logic.BusinessRequestLogLogic;
import com.ccloud.main.logic.ClientBusinessActivationLogLogic;
import com.ccloud.main.pojo.query.AccessLogQueryVo;
import com.ccloud.main.pojo.query.ActivationLogQueryVo;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.util.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 日志管理控制器
 *
 * @author wangjie
 */
@RequestMapping("/logAdmin")
@RestController
public class BusinessLogAdminController {

    @Resource
    private BusinessRequestLogLogic businessRequestLogLogic;


    @Resource
    private ClientBusinessActivationLogLogic clientBusinessActivationLogLogic;

    /**
     * 分页获取日志
     *
     * @param accessLogQueryVo
     * @return
     */
    @PostMapping("/accessLog/page")
    public Result<Object> accessLogPage(@RequestBody AccessLogQueryVo accessLogQueryVo) {
        BusinessUser currentUser = UserManager.getCurrentUser();
        IPage<BusinessRequestLog> noticeBaseConfigs = businessRequestLogLogic.getPageAccessLogByAppId(accessLogQueryVo, currentUser);
        return ResultUtil.success(noticeBaseConfigs);
    }


    /**
     * 分页获取日志
     *
     * @param activationLogQueryVo
     * @return
     */
    @PostMapping("/activationLog/page")
    public Result<Object> activationLogPage(@RequestBody ActivationLogQueryVo activationLogQueryVo) {
        BusinessUser currentUser = UserManager.getCurrentUser();
        IPage<ClientBusinessActivationLog> noticeBaseConfigs = clientBusinessActivationLogLogic.getPageActivationLogByAppId(activationLogQueryVo, currentUser);
        return ResultUtil.success(noticeBaseConfigs);
    }
}
