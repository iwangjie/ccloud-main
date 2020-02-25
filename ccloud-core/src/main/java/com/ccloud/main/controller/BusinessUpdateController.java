package com.ccloud.main.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessUpdateBaseConfig;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.logic.BusinessUpdateBaseConfigLogic;
import com.ccloud.main.pojo.query.UpdatePageQueryVo;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.service.IBusinessUpdateBaseConfigService;
import com.ccloud.main.util.ResultUtil;
import com.ccloud.main.util.annotation.RequestJson;
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
@RequestMapping("/update")
@Slf4j
public class BusinessUpdateController {

    @Resource
    private BusinessUpdateBaseConfigLogic businessUpdateBaseConfigLogic;


    @Resource
    private IBusinessUpdateBaseConfigService iBusinessUpdateBaseConfigService;


    /**
     * 获取 app 所有更新记录-分页
     *
     * @param updatePageQueryVo
     * @return
     */
    @PostMapping("/page")
    public Result page(@RequestBody UpdatePageQueryVo updatePageQueryVo) {
        Page<BusinessUpdateBaseConfig> page = new Page<>(updatePageQueryVo.getCurrent(), updatePageQueryVo.getSize());
        BusinessUser currentUser = UserManager.getCurrentUser();
        Page<BusinessUpdateBaseConfig> data = businessUpdateBaseConfigLogic.getPageUpdateByAppId(page, currentUser, updatePageQueryVo.getAppId());
        return ResultUtil.success(data);
    }


    /**
     * 获取 app 所有更新记录
     *
     * @param appId
     * @return
     */
    @PostMapping("/all")
    public Result all(@RequestJson("appId") Integer appId) {
        BusinessUser currentUser = UserManager.getCurrentUser();
        List<BusinessUpdateBaseConfig> noticeBaseConfigs = businessUpdateBaseConfigLogic.getAllUpdateByAppId(currentUser, appId);
        return ResultUtil.success(noticeBaseConfigs);
    }


    /**
     * 保存
     *
     * @param businessUpdateBaseConfig
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody BusinessUpdateBaseConfig businessUpdateBaseConfig) {
        iBusinessUpdateBaseConfigService.save(businessUpdateBaseConfig);
        return ResultUtil.success();
    }

    /**
     * 更新
     *
     * @param businessUpdateBaseConfig
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody BusinessUpdateBaseConfig businessUpdateBaseConfig) {
        iBusinessUpdateBaseConfigService.updateById(businessUpdateBaseConfig);
        return ResultUtil.success();
    }

}
