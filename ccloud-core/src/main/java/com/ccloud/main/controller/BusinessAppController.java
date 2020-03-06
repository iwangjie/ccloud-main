package com.ccloud.main.controller;

import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessAppBaseConfig;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.logic.BusinessAppLogic;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.util.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 关于 APP信息 通用接口
 *
 * @author wangjie
 */
@RestController
@RequestMapping("app")
public class BusinessAppController {

    @Resource
    private BusinessAppLogic businessAppLogic;


    /**
     * 获取当前用户 app 集合
     *
     * @return
     */
    @PostMapping("currUserAppList")
    public Result<Object> selectCurrUserAppList() {
        BusinessUser currentUser = UserManager.getCurrentUser();
        List<BusinessAppBaseConfig> list = businessAppLogic.selectCurrUserAppList(currentUser);
        return ResultUtil.success(list);
    }
}
