package com.ccloud.main.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessActivationCode;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.logic.BusinessActivationCodeLogic;
import com.ccloud.main.pojo.query.UpdatePageQueryVo;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.service.IBusinessActivationCodeService;
import com.ccloud.main.util.ResultUtil;
import com.ccloud.main.util.annotation.RequestJson;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录控制器
 *
 * @author wangjie
 */
@RestController
@Slf4j
@Api(tags = {"B端激活码管理"})
public class BusinessActivationCodeController {

    @Resource
    private BusinessActivationCodeLogic businessActivationCodeLogic;


    @Resource
    private IBusinessActivationCodeService iBusinessActivationCodeService;


    /**
     * 获取 app 所有激活码记录-分页
     *
     * @param updatePageQueryVo
     * @return
     */
    @PostMapping("/page")
    public Result page(@RequestBody UpdatePageQueryVo updatePageQueryVo) {
        Page<BusinessActivationCode> page = new Page<BusinessActivationCode>(updatePageQueryVo.getCurrent(), updatePageQueryVo.getSize());
        BusinessUser currentUser = UserManager.getCurrentUser();
        Page<BusinessActivationCode> data = businessActivationCodeLogic.getPageUpdateByAppId(page, currentUser, updatePageQueryVo.getAppId());
        return ResultUtil.success(data);
    }


    /**
     * 获取 app 所有激活码记录-不分页
     *
     * @param appId
     * @return
     */
    @PostMapping("/all")
    public Result all(@RequestJson("appId") Integer appId) {
        BusinessUser currentUser = UserManager.getCurrentUser();
        List<BusinessActivationCode> noticeBaseConfigs = businessActivationCodeLogic.getAllUpdateByAppId(currentUser, appId);
        return ResultUtil.success(noticeBaseConfigs);
    }


}
