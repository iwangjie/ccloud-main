package com.ccloud.main.config.WebAop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

/**
 * Web切面统一处理操作日志
 *
 * @author yanghang,suhui
 */

@Aspect
@Component
@Slf4j
public class WebControllerAop  {

    //private final static Logger logger = LoggerFactory.getLogger(WebControllerAop.class);

    @Pointcut("execution(public * com.ccloud.main.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取HttpServletRequest
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
       log.info("请求地址 : " + request.getRequestURL().toString());
       log.info("请求类型 : " + request.getMethod());
       log.info("IP : " + request.getRemoteAddr());
       log.info("所在包名 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());


    }

}
