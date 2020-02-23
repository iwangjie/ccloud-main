package com.ccloud.main.controller;

import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.logic.BusinessUserLogic;
import com.ccloud.main.pojo.enumeration.ResultEnum;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.service.IBusinessUserService;
import com.ccloud.main.util.MD5Tools;
import com.ccloud.main.util.ResultUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

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
    private IBusinessUserService iBusinessUserService;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private DefaultKaptcha captchaProducer;

    private static final String VERIFY_CODE_KEY = "VERIFY_CODE";

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
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

        return ResultUtil.success(jwtUtil.sign(user.getId() + ":" + user.getPassword()));
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param code     验证码
     * @return
     */
    @PostMapping("/reg")
    public Result reg(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("code") String code) {

        String VERIFY_CODE = (String) request.getSession().getAttribute(VERIFY_CODE_KEY);
        if (!code.equals(VERIFY_CODE)) {
            return ResultUtil.error(ResultEnum.VERIFY_CODE_ERROR);
        }
        request.getSession().removeAttribute(VERIFY_CODE_KEY);

        BusinessUser user = businessUserLogic.findByName(username);
        if (user != null) {
            return ResultUtil.error(ResultEnum.USER_IS_EXIST);
        }
        businessUserLogic.register(username, password);
        return ResultUtil.success("注册成功");
    }


    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = captchaProducer.createText();
            log.info("verifyCode:" + createText);
            httpServletRequest.getSession().setAttribute("code", createText);
            httpServletRequest.getSession().setAttribute(VERIFY_CODE_KEY, createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = captchaProducer.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();

    }


    @PostMapping("/currUser")
    @RequiresPermissions("user:currUser")
    public Result currUser() {
        return ResultUtil.success(UserManager.getCurrentUser());
    }


}