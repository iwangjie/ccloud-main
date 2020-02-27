package com.ccloud.main.config.WebAop;

import com.ccloud.main.logic.BusinessRequestLogLogic;
import com.ccloud.main.pojo.enumeration.CloudUtilEnum;
import com.ccloud.main.util.CloudUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Web切面统一处理日志
 *
 * @author yanghang
 */

@Aspect
@Component
@Slf4j
public class WebControllerAop {

    @Autowired
    private BusinessRequestLogLogic businessRequestLogLogic;

    @Pointcut("execution(public * com.ccloud.main.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        JsonNode jsonNode = (JsonNode) CloudUtil.get(CloudUtilEnum.CURR_REQUEST_BODY);
        JsonNode appId = jsonNode.get("appId");
        JsonNode userId = jsonNode.get("userId");
        log.info("userId:{}", jsonNode.get("appId"));

        businessRequestLogLogic.addBusinessRequestLog(joinPoint);

    }
}
