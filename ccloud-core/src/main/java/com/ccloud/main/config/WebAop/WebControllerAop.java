package com.ccloud.main.config.WebAop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Modifier;


/**
 * Web切面统一处理日志
 *
 * @author yanghang
 */

@Aspect
@Component
@Slf4j
public class WebControllerAop  {

    @Pointcut("execution(public * com.ccloud.main.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取HttpServletRequest
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        //目标方法名
        log.info("目标方法名 : " + joinPoint.getSignature().getName());
        log.info("请求地址 : " + request.getRequestURL().toString());
        log.info("请求方式 : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("所在包名 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        log.info("参数拼接 : " + StringUtils.join(joinPoint.getArgs(),","));

        log.info("请求端类型 :" + request.getHeader("User-Agent"));
        //请求类型
        String type = request.getHeader("User-Agent");

        if(type.indexOf("android") != -1){
            //安卓
        }else if(type.indexOf("iphone") != -1 || type.indexOf("ipad") != -1 || type.indexOf("ipod") != -1){
            //苹果
        }else{
            //电脑
        }





    }
}
