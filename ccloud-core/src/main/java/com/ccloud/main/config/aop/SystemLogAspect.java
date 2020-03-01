package com.ccloud.main.config.aop;

import com.ccloud.main.entity.BusinessRequestLog;
import com.ccloud.main.pojo.enumeration.CloudUtilEnum;
import com.ccloud.main.util.CloudUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 系统日志切面类
 *
 * Web切面统一处理日志(Aop日志记录之BlockingQueue队列)
 * @author 杨航
 */

@Slf4j
@Aspect
@Component
public class SystemLogAspect {

    ThreadLocal<BusinessRequestLog> businessRequestLogThreadLocal = new ThreadLocal<>();

    @Pointcut("execution(public * com.ccloud.main.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        /*int appId = 0;
        int useId = 0;*/
        JsonNode jsonNode = (JsonNode) CloudUtil.get(CloudUtilEnum.CURR_REQUEST_BODY);
        JsonNode appIdJsonNode = jsonNode.get("appId");
        JsonNode userIdJsonNode = jsonNode.get("userId");
        log.info("userId:{}", jsonNode.get("appId"));
        /*if(null != appIdJsonNode){
            appId = appIdJsonNode.intValue();
        }
        if(null != userIdJsonNode){
            useId = userIdJsonNode.intValue();
        }*/

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取HttpServletRequest
        HttpServletRequest request = attributes.getRequest();

        long beginTime = System.currentTimeMillis();
        BusinessRequestLog businessRequestLog = new BusinessRequestLog();
        businessRequestLogThreadLocal.set(businessRequestLog);

        //记录下请求内容
        log.info("目标方法名 : " + joinPoint.getSignature().getName());
        log.info("请求地址 : " + request.getRequestURL().toString());
        log.info("请求方式 : " + request.getMethod());
        log.info("所在包名 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求端类型 :" + request.getHeader("User-Agent"));


        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        List<String> parameterNamesList = Arrays.asList(parameterNames);

        String parameterNamesJoin = StringUtils.EMPTY;
        if(parameterNames.length==parameterNamesList.size()){
            List<String> addList = new ArrayList<>();
            for (int i = 0;i<parameterNamesList.size();i++){
                addList.add(parameterNamesList.get(i)+":"+Arrays.asList(joinPoint.getArgs()).get(i));
            }
            parameterNamesJoin = StringUtils.join(addList, ",");
            log.info("参数拼接 : " +parameterNamesJoin);
        }else{
            parameterNamesJoin = StringUtils.join(joinPoint.getArgs(), ",");
            log.info("参数拼接 : " +parameterNamesJoin);
        }

        businessRequestLog.setMethodName(StringUtils.isNotEmpty(joinPoint.getSignature().getName())?joinPoint.getSignature().getName():StringUtils.EMPTY);
        businessRequestLog.setRequestUrl(StringUtils.isNotEmpty(request.getRequestURL())?request.getRequestURL().toString():StringUtils.EMPTY);
        businessRequestLog.setRequestWay(StringUtils.isNotEmpty(request.getMethod())?request.getMethod():StringUtils.EMPTY);
        businessRequestLog.setPackageName(StringUtils.isNotEmpty(joinPoint.getSignature().getDeclaringTypeName())?joinPoint.getSignature().getDeclaringTypeName():StringUtils.EMPTY);
        businessRequestLog.setParameterSplit(parameterNamesJoin);
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

        businessRequestLog.setSpendTime(new BigDecimal(String.valueOf(beginTime)));

    }




    /**
     * 返回通知, 在方法返回结果之后执行
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE: {}", ret);
        BusinessRequestLog businessRequestLog = businessRequestLogThreadLocal.get();
        if(null != businessRequestLog){
            this.saveBusinessRequestLog();
        }
    }


    /**
     * 保存系统日志
     */
    private void saveBusinessRequestLog(){
        BusinessRequestLog businessRequestLog = businessRequestLogThreadLocal.get();
        long beginTime = Long.valueOf(businessRequestLog.getSpendTime().toString());
        //执行时长(秒)
        double spendTime = (System.currentTimeMillis() - beginTime) / 1000.0d;

        businessRequestLog.setSpendTime(new BigDecimal(String.valueOf(spendTime)));
        try {
            // 将系统日志放入到队列中分批处理
            SystemLogQueue.getInstance().push(businessRequestLog);
        } catch (Exception e) {
            log.error("添加队列失败,已超过队列最大长度：{}", e);
        } finally {
            businessRequestLogThreadLocal.remove();
        }
    }


    /**
     * 异常通知，当目标方法执行过程中出现异常时才会进行执行的代码
     *
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(throwing = "ex", pointcut = "webLog()")
    public void afterthrowinglogging(JoinPoint joinPoint, Exception ex) {
        BusinessRequestLog systemLog = businessRequestLogThreadLocal.get();
        if (null != systemLog) {
            this.saveBusinessRequestLog();
        }
    }
}
