package com.ccloud.main.controller.api;

import com.ccloud.main.config.jwt.client.ClientJwtUtil;
import com.ccloud.main.entity.ClientUser;
import com.ccloud.main.logic.ClientUserLogic;
import com.ccloud.main.pojo.enumeration.ResultEnum;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.util.MD5Tools;
import com.ccloud.main.util.ResultUtil;
import com.ccloud.main.util.annotation.RequestJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 客户端登录注册控制器
 *
 * @author wangjie
 */
@RestController
@RequestMapping("/api")
public class ClientLoginController {

    @Resource
    private ClientUserLogic clientUserLogic;

    @Resource
    private ClientJwtUtil clientJwtUtil;

    @Resource
    private ObjectMapper objectMapper;


    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public Result login(@RequestJson("username") String username, @RequestJson("password") String password) throws JsonProcessingException {

        ClientUser user = clientUserLogic.findByName(username);
        if (user == null) {
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
        }
        String password_salt = MD5Tools.parseStrToMd5L32(password + username);
        if (!password_salt.equals(user.getPassword())) {
            return ResultUtil.error(ResultEnum.USER_PASSWORD_ERROR);
        }
        user.setPassword("");
        return ResultUtil.success(clientJwtUtil.sign(objectMapper.writeValueAsString(user)));
    }

}
