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
import com.ccloud.main.util.annotation.RequestJson;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Slf4j
@Api(tags = {"公告管理"})
public class BusinessNoticeController extends BaseController {

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
    public Result<Object> last(@RequestJson("appId") Integer appId) {
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
    public Result<Object> getNotIceById(@RequestJson("appId") Integer appId, @RequestJson("noticeId") Integer noticeId) {
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
    public Result<Object> page(@RequestBody NoticePageQueryVo noticePageQueryVo) {
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
    public Result<Object> all(@RequestJson("appId") Integer appId) {
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
    public Result<Object> save(@RequestBody BusinessNoticeBaseConfig businessNoticeBaseConfig) {
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
    public Result<Object> update(@RequestBody BusinessNoticeBaseConfig businessNoticeBaseConfig) {
        iBusinessNoticeBaseConfigService.updateById(businessNoticeBaseConfig);
        return ResultUtil.success();
    }

}
