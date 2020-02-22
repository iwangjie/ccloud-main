package com.ccloud.main.controller;

import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.logic.BusinessUserLogic;
import com.ccloud.main.pojo.enumeration.ResultEnum;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.util.MD5Tools;
import com.ccloud.main.util.ResultUtil;
import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录控制器
 *
 * @author wangjie
 */
@RestController
@Slf4j
public class LoginController extends BaseController {

    @Resource
    private BusinessUserLogic businessUserLogic;

    @Resource
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {

        BusinessUser user = businessUserLogic.findByName(username);
        if (user == null) {
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
        }

        String password_salt = MD5Tools.parseStrToMd5L32(password + username);
        if (!password_salt.equals(user.getPassword())) {
            return ResultUtil.error(ResultEnum.USER_PASSWORD_ERROR);
        }

        return ResultUtil.success(jwtUtil.sign(user.getId() + ""));
    }

    @PostMapping("/currUser")
    @RequiresPermissions("user:currUser")
    public Result currUser() {
        return ResultUtil.success(UserManager.getCurrentUser());
    }


}