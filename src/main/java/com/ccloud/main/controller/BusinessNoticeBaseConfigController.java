package com.ccloud.main.controller;

import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessNoticeBaseConfig;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.logic.BusinessNoticeBaseConfigLogic;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.util.ResultUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告 Controller
 *
 * @author wangjie
 */
@RestController
@RequestMapping("/notice")
public class BusinessNoticeBaseConfigController extends BaseController {

    @Resource
    private BusinessNoticeBaseConfigLogic businessNoticeBaseConfigLogic;

    /**
     * 获取 app 当前生效的最后一条公告
     *
     * @param appId
     * @return
     */
    @PostMapping("/{appId}/last")
    public Result<Object> first(@PathVariable String appId) {
        BusinessUser currentUser = UserManager.getCurrentUser();
        BusinessNoticeBaseConfig businessNoticeBaseConfig = businessNoticeBaseConfigLogic.getLastNoticeByAppId(currentUser, appId);
        return ResultUtil.success(businessNoticeBaseConfig);
    }

    /**
     * 获取 app 所有公告
     *
     * @param appId
     * @return
     */
    @PostMapping("/{appId}/all")
    public Result<Object> all(@PathVariable String appId) {
        BusinessUser currentUser = UserManager.getCurrentUser();
        List<BusinessNoticeBaseConfig> noticeBaseConfigs = businessNoticeBaseConfigLogic.getAllNoticeByAppId(currentUser, appId);
        return ResultUtil.success(noticeBaseConfigs);
    }

}
