package com.ccloud.main.controller.api;


import com.ccloud.main.entity.BusinessUpdateBaseConfig;
import com.ccloud.main.logic.BusinessUpdateBaseConfigLogic;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.service.IBusinessUpdateBaseConfigService;
import com.ccloud.main.util.ResultUtil;
import com.ccloud.main.util.annotation.RequestJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 公告 Controller
 *
 * @author wangjie
 */
@RestController
@RequestMapping("/api/update")
@Slf4j
public class ClientUpdateController {

    @Resource
    private BusinessUpdateBaseConfigLogic businessUpdateBaseConfigLogic;


    @Resource
    private IBusinessUpdateBaseConfigService iBusinessUpdateBaseConfigService;


    /**
     * 检查更新
     *
     * @param appId
     * @param versionId
     * @return
     */
    @PostMapping("/version")
    public Result version(@RequestJson("appId") Integer appId, @RequestJson("versionId") String versionId) {
        BusinessUpdateBaseConfig businessUpdateBaseConfig = businessUpdateBaseConfigLogic.getUpdateByVersionId(appId, versionId);
        return ResultUtil.success(businessUpdateBaseConfig);
    }


    /**
     * 更新日志
     *
     * @param appId
     * @return
     */
    @PostMapping("/log")
    public Result log(@RequestJson("appId") Integer appId, @RequestJson("versionId") String versionId) {
        businessUpdateBaseConfigLogic.getUpdateLog(appId, versionId);
        return ResultUtil.success();
    }


}
