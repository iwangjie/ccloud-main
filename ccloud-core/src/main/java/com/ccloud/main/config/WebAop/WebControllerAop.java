package com.ccloud.main.config.WebAop;

import com.ccloud.main.logic.BusinessRequestLogLogic;
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
public class WebControllerAop  {

    @Autowired
    private BusinessRequestLogLogic businessRequestLogLogic;

    @Pointcut("execution(public * com.ccloud.main.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        businessRequestLogLogic.addBusinessRequestLog(joinPoint);

    }
}
