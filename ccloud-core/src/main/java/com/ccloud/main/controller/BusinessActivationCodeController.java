package com.ccloud.main.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ccloud.main.config.shiro.UserManager;
import com.ccloud.main.entity.BusinessActivationCode;
import com.ccloud.main.entity.BusinessUser;
import com.ccloud.main.entity.ClientBusinessActivationLog;
import com.ccloud.main.entity.ClientUserCode;
import com.ccloud.main.logic.BusinessActivationCodeLogic;
import com.ccloud.main.pojo.enumeration.CloudUtilEnum;
import com.ccloud.main.pojo.enumeration.ResultEnum;
import com.ccloud.main.pojo.query.UpdatePageQueryVo;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.service.IBusinessActivationCodeService;
import com.ccloud.main.service.IClientBusinessActivationLogService;
import com.ccloud.main.service.IClientUserCodeService;
import com.ccloud.main.util.CloudUtil;
import com.ccloud.main.util.ResultUtil;
import com.ccloud.main.util.annotation.RequestJson;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * 登录控制器
 *
 * @author wangjie
 */
@RestController
@Slf4j
@Api(tags = {"B端激活码管理"})
@RequestMapping("/activationCode")
public class BusinessActivationCodeController {

    @Resource
    private BusinessActivationCodeLogic businessActivationCodeLogic;

    @Resource
    private IBusinessActivationCodeService iBusinessActivationCodeService;

    @Resource
    private IClientUserCodeService iClientUserCodeService;

    @Resource
    private IClientBusinessActivationLogService iClientBusinessActivationLogService;

    /**
     * 获取 app 所有激活码记录-分页
     *
     * @param activationCodePageQueryVo
     * @return
     */
    @PostMapping("/page")
    public Result page(@RequestBody UpdatePageQueryVo activationCodePageQueryVo) {
        BusinessUser currentUser = UserManager.getCurrentUser();
        IPage<BusinessActivationCode> data = businessActivationCodeLogic.getPageUpdateByAppId(currentUser, activationCodePageQueryVo);
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

    /**
     * @param count 生成注册码的个数
     * @param days  注册码的天数
     *              生成随机的32位大小写字母的组个字符串
     * @author 杨航
     */
    @PostMapping("/generate")
    public Result insert(@RequestJson("appId") int appId, @RequestJson("count") int count, @RequestJson("days") int days) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        try {
            if (count > 1000) {
                return ResultUtil.error(ResultEnum.PARAMETER_ERROR);
            }
            for (int j = 0; j < count; j++) {
                Random random = new Random();
                StringBuffer activationCode = new StringBuffer();
                for (int i = 0; i < 32; i++) {
                    int number = random.nextInt(51);
                    activationCode.append(str.charAt(number));
                }
                BusinessActivationCode businessActivationCode = new BusinessActivationCode();
                businessActivationCode.setActivationCode(activationCode.toString());
                businessActivationCode.setAppId(appId);
                businessActivationCode.setDays(days);
                businessActivationCode.setExt("{}");
                businessActivationCode.setStatus(0);
                businessActivationCode.setWriteOffStatus(0);
                iBusinessActivationCodeService.save(businessActivationCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }


    @PostMapping("/queryByActivationCode")
    public Result queryByActivationCode(@RequestJson("appId") int appId, @RequestJson("activationCode") String activationCode) {
        try {
            if (appId > 0 && StringUtils.isNotEmpty(activationCode)) {
                QueryWrapper<BusinessActivationCode> objectQueryWrapper = new QueryWrapper<>();
                objectQueryWrapper.eq("activation_code", activationCode);
                BusinessActivationCode businessActivationCode = iBusinessActivationCodeService.getOne(objectQueryWrapper);

                if (null != businessActivationCode) {

                    businessActivationCode.setAppId(appId);

                    boolean b = iBusinessActivationCodeService.saveOrUpdate(businessActivationCode);
                    if (!b) {
                        return ResultUtil.error(ResultEnum.PARAMETER_ERROR);
                    }

                    Object object = CloudUtil.get(CloudUtilEnum.CURR_USER_ID);
                    if (null == object) {
                        return ResultUtil.error(ResultEnum.PARAMETER_ERROR);
                    }
                    Integer userId = Integer.valueOf(object.toString());

                    //日志
                    ClientBusinessActivationLog clientBusinessActivationLog = new ClientBusinessActivationLog();
                    clientBusinessActivationLog.setActivationCode(businessActivationCode.getActivationCode());
                    clientBusinessActivationLog.setActivationCodeId(businessActivationCode.getId());
                    clientBusinessActivationLog.setCreateTime(LocalDateTime.now());
                    clientBusinessActivationLog.setStatus(0);
                    clientBusinessActivationLog.setUpdateTime(LocalDateTime.now());
                    clientBusinessActivationLog.setUserId(userId);
                    boolean save1 = iClientBusinessActivationLogService.save(clientBusinessActivationLog);
                    if (!save1) {
                        return ResultUtil.error(ResultEnum.PARAMETER_ERROR);
                    }

                    //查询注册码是否已经被使用
                    QueryWrapper<ClientUserCode> clientUserCodeQueryWrapper = new QueryWrapper<>();
                    clientUserCodeQueryWrapper.eq("activation_code_id", businessActivationCode.getId());
                    ClientUserCode clientUserCode = iClientUserCodeService.getOne(clientUserCodeQueryWrapper);
                    if (null == clientUserCode) {
                        ClientUserCode clientUserCode1 = new ClientUserCode();
                        clientUserCode1.setActivationCode(businessActivationCode.getActivationCode());
                        clientUserCode1.setActivationCodeId(businessActivationCode.getId());
                        clientUserCode1.setActivationEndTime(businessActivationCode.getCreateTime().plusDays(businessActivationCode.getDays()));
                        clientUserCode1.setUserId(userId);
                        clientUserCode1.setCreateTime(LocalDateTime.now());
                        clientUserCode1.setUpdateTime(LocalDateTime.now());
                        clientUserCode1.setStatus(0);
                        boolean save = iClientUserCodeService.save(clientUserCode1);
                        if (!save) {
                            //注册码已经存在，证明已经被使用了
                            return ResultUtil.error(ResultEnum.PARAMETER_ERROR);
                        }
                    } else {
                        return ResultUtil.error(ResultEnum.PARAMETER_ERROR);
                    }
                    return ResultUtil.success(businessActivationCode);
                }
            } else {
                return ResultUtil.error(ResultEnum.PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(null);
    }
}
