package com.ccloud.main.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessNoticeBaseConfig;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.logic.BusinessNoticeBaseConfigLogic;
import com.ccloud.main.pojo.query.NoticePageQueryVo;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.service.IBusinessNoticeBaseConfigService;
import com.ccloud.main.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告 Controller
 *
 * @author wangjie
 */
@RestController
@RequestMapping("/notice")
@Slf4j
public class BusinessNoticeBaseConfigController extends BaseController {

    @Resource
    private BusinessNoticeBaseConfigLogic businessNoticeBaseConfigLogic;

    @Resource
    private IBusinessNoticeBaseConfigService iBusinessNoticeBaseConfigService;

    /**
     * 获取 app 当前生效的最后一条公告
     *
     * @param appId
     * @return
     */
    @PostMapping("/last")
    public Result<Object> last(@RequestParam("appId") Integer appId) {
        BusinessUser currentUser = UserManager.getCurrentUser();
        BusinessNoticeBaseConfig businessNoticeBaseConfig = businessNoticeBaseConfigLogic.getLastNoticeByAppId(currentUser, appId);
        return ResultUtil.success(businessNoticeBaseConfig);
    }

    /**
     * 根据公告 id 获取公告信息
     *
     * @param appId
     * @param noticeId
     * @return
     */
    @PostMapping("/id")
    public Result<Object> getNotIceById(@RequestParam("appId") Integer appId, @RequestParam("noticeId") Integer noticeId) {
        BusinessUser currentUser = UserManager.getCurrentUser();
        BusinessNoticeBaseConfig businessNoticeBaseConfig = businessNoticeBaseConfigLogic.getLastNoticeById(currentUser, appId, noticeId);
        return ResultUtil.success(businessNoticeBaseConfig);
    }

    /**
     * 获取 app 所有公告
     *
     * @param noticePageQueryVo
     * @return
     */
    @PostMapping("/page")
    public Result<Object> page(NoticePageQueryVo noticePageQueryVo) {
        Page page = new Page<>(noticePageQueryVo.getCurrent(), noticePageQueryVo.getSize());
        BusinessUser currentUser = UserManager.getCurrentUser();
        List<BusinessNoticeBaseConfig> noticeBaseConfigs = businessNoticeBaseConfigLogic.getPageNoticeByAppId(page, currentUser, noticePageQueryVo.getAppId());
        return ResultUtil.success(noticeBaseConfigs);
    }


    /**
     * 获取 app 所有公告
     *
     * @param appId
     * @return
     */
    @PostMapping("/all")
    public Result<Object> all(@RequestParam("appId") Integer appId) {
        BusinessUser currentUser = UserManager.getCurrentUser();
        List<BusinessNoticeBaseConfig> noticeBaseConfigs = businessNoticeBaseConfigLogic.getAllNoticeByAppId(currentUser, appId);
        return ResultUtil.success(noticeBaseConfigs);
    }


    /**
     * 保存 app 公告
     *
     * @param businessNoticeBaseConfig
     * @return
     */
    @PostMapping("/save")
    public Result<Object> save(BusinessNoticeBaseConfig businessNoticeBaseConfig) {
        iBusinessNoticeBaseConfigService.save(businessNoticeBaseConfig);
        return ResultUtil.success();
    }

    /**
     * 更新公告
     *
     * @param businessNoticeBaseConfig
     * @return
     */
    @PostMapping("/update")
    public Result<Object> update(BusinessNoticeBaseConfig businessNoticeBaseConfig) {
        iBusinessNoticeBaseConfigService.updateById(businessNoticeBaseConfig);
        return ResultUtil.success();
    }

}
