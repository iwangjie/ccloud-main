package com.ccloud.main.logic;

import cn.hutool.core.text.escape.Html4Escape;
import com.ccloud.main.entity.BusinessRequestLog;
import com.ccloud.main.mapper.BusinessRequestLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@Slf4j
public class BusinessRequestLogLogic {

    @Autowired
    private BusinessRequestLogMapper businessRequestLogMapper;

    public void addBusinessRequestLog(JoinPoint joinPoint){

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取HttpServletRequest
        HttpServletRequest request = attributes.getRequest();

        BusinessRequestLog businessRequestLog = new BusinessRequestLog();

        //记录下请求内容
        log.info("目标方法名 : " + joinPoint.getSignature().getName());
        log.info("请求地址 : " + request.getRequestURL().toString());
        log.info("请求方式 : " + request.getMethod());
        log.info("所在包名 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("参数拼接 : " + StringUtils.join(joinPoint.getArgs(),","));
        log.info("请求端类型 :" + request.getHeader("User-Agent"));

        businessRequestLog.setMethodName(StringUtils.isNotEmpty(joinPoint.getSignature().getName())?joinPoint.getSignature().getName():StringUtils.EMPTY);
        businessRequestLog.setRequestUrl(StringUtils.isNotEmpty(request.getRequestURL())?request.getRequestURL().toString():StringUtils.EMPTY);
        businessRequestLog.setRequestWay(StringUtils.isNotEmpty(request.getMethod())?request.getMethod():StringUtils.EMPTY);
        businessRequestLog.setPackageName(StringUtils.isNotEmpty(joinPoint.getSignature().getDeclaringTypeName())?joinPoint.getSignature().getDeclaringTypeName():StringUtils.EMPTY);
        businessRequestLog.setParameterSplit(joinPoint.getArgs().length<=0?StringUtils.EMPTY:StringUtils.join(joinPoint.getArgs(),","));
        businessRequestLog.setAppId(1);
        businessRequestLog.setUserId(5);
        businessRequestLog.setStatus("normal");
        businessRequestLog.setCreateTime(LocalDateTime.now());
        businessRequestLog.setUpdateTime(LocalDateTime.now());

        //请求类型
        String type = request.getHeader("User-Agent");

        if(type.indexOf("android") != -1){
            //安卓
            businessRequestLog.setType("Android");
        }else if(type.indexOf("iphone") != -1 || type.indexOf("ipad") != -1 || type.indexOf("ipod") != -1){
            //苹果
            businessRequestLog.setType("Ios");
        }else{
            //电脑
            businessRequestLog.setType("Computer");
        }

        businessRequestLogMapper.insert(businessRequestLog);
    }
}
