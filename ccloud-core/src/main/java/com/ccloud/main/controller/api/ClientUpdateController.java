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
     * @param versionId
     * @return
     */
    @PostMapping("/version")
    public Result version(@RequestJson("versionId") String versionId) {
        BusinessUpdateBaseConfig businessUpdateBaseConfig = businessUpdateBaseConfigLogic.getUpdateByVersionId(versionId);
        return ResultUtil.success(businessUpdateBaseConfig);
    }


    /**
     * 更新日志
     *
     * @param versionId
     * @return
     */
    @PostMapping("/log")
    public Result log(@RequestJson("versionId") String versionId) {
//        businessUpdateBaseConfigLogic.getUpdateLog();
        return ResultUtil.success();
    }


}
