package com.ccloud.main.controller;

import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessNoticeBaseConfig;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.logic.BusinessNoticeBaseConfigLogic;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.service.IBusinessNoticeBaseConfigService;
import com.ccloud.main.util.JSONObject;
import com.ccloud.main.util.JsonUtils;
import com.ccloud.main.util.ResultUtil;
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
public class BusinessNoticeBaseConfigController extends BaseController {

    @Resource
    private BusinessNoticeBaseConfigLogic businessNoticeBaseConfigLogic;

    @Resource
    private IBusinessNoticeBaseConfigService iBusinessNoticeBaseConfigService;

    /**
     * 获取 app 当前生效的最后一条公告
     *
     * @param json
     * @return
     */
    @PostMapping("/last")
    public Result<Object> first(@RequestBody String json) {
        JSONObject param = getJsonObject(json);
        String appId = param.getString("appId");
        BusinessUser currentUser = UserManager.getCurrentUser();
        BusinessNoticeBaseConfig businessNoticeBaseConfig = businessNoticeBaseConfigLogic.getLastNoticeByAppId(currentUser, appId);
        return ResultUtil.success(businessNoticeBaseConfig);
    }

    /**
     * 根据公告 id 获取公告信息
     *
     * @param json
     * @return
     */
    @PostMapping("/id")
    public Result<Object> getNotIceById(@RequestBody String json) {
        JSONObject param = getJsonObject(json);
        String appId = param.getNotNullString("appId");
        String noticeId = param.getString("noticeId");
        BusinessUser currentUser = UserManager.getCurrentUser();
        BusinessNoticeBaseConfig businessNoticeBaseConfig = businessNoticeBaseConfigLogic.getLastNoticeById(currentUser, appId, noticeId);
        return ResultUtil.success(businessNoticeBaseConfig);
    }

    /**
     * 获取 app 所有公告
     *
     * @param json
     * @return
     */
    @PostMapping("/all")
    public Result<Object> all(@RequestBody String json) {
        JSONObject param = getJsonObject(json);
        String appId = param.getNotNullString("appId");
        BusinessUser currentUser = UserManager.getCurrentUser();
        List<BusinessNoticeBaseConfig> noticeBaseConfigs = businessNoticeBaseConfigLogic.getAllNoticeByAppId(currentUser, appId);
        return ResultUtil.success(noticeBaseConfigs);
    }


    /**
     * 保存 app 公告
     *
     * @param json
     * @return
     */
    @PostMapping("/save")
    public Result<Object> save(@RequestBody String json) {
        JSONObject param = getJsonObject(json);
        Integer appId = param.getNotNullInteger("appId");
        BusinessNoticeBaseConfig businessNoticeBaseConfig = JsonUtils.toJavaObject(json, BusinessNoticeBaseConfig.class);
        businessNoticeBaseConfig.setAppId(appId);
        iBusinessNoticeBaseConfigService.save(businessNoticeBaseConfig);
        return ResultUtil.success();
    }

    /**
     * 更新公告
     *
     * @param json
     * @return
     */
    @PostMapping("/update")
    public Result<Object> update(@RequestBody String json) {
        JSONObject param = getJsonObject(json);
        Integer noticeId = param.getNotNullInteger("noticeId");
        BusinessNoticeBaseConfig businessNoticeBaseConfig = JsonUtils.toJavaObject(json, BusinessNoticeBaseConfig.class);
        businessNoticeBaseConfig.setId(noticeId);
        iBusinessNoticeBaseConfigService.updateById(businessNoticeBaseConfig);
        return ResultUtil.success();
    }

}
