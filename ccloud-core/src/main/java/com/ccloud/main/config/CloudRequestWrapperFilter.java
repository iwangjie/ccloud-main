package com.ccloud.main.config;

import com.ccloud.main.entity.BusinessAppBaseConfig;
import com.ccloud.main.pojo.enumeration.CloudUtilEnum;
import com.ccloud.main.pojo.enumeration.ResultEnum;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.service.IBusinessAppBaseConfigService;
import com.ccloud.main.util.CloudUtil;
import com.ccloud.main.util.ResultUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wangjie
 */
@Component
@WebFilter
@Slf4j
public class CloudRequestWrapperFilter implements Filter {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private IBusinessAppBaseConfigService iBusinessAppBaseConfigService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            CloudHttpServletRequestWrapper cloudHttpServletRequestWrapper = new CloudHttpServletRequestWrapper(
                    (HttpServletRequest) request);
            //遇到post方法才对request进行包装
            String methodType = httpRequest.getMethod();
            if (!"POST".equals(methodType)) {
                chain.doFilter(cloudHttpServletRequestWrapper, response);
                return;
            }

            // Token存储
            String cl_token = ((HttpServletRequest) request).getHeader("CL-Authorization");
            CloudUtil.set(CloudUtilEnum.CL_AUTHORIZATION, cl_token);
            String cc_token = ((HttpServletRequest) request).getHeader("CC-Authorization");
            CloudUtil.set(CloudUtilEnum.CC_AUTHORIZATION, cc_token);

            /***APP_ID 权限验证**/
            JsonNode requestBody = cloudHttpServletRequestWrapper.getRequestBody();
            JsonNode appIdNode = requestBody.get("appId");
            if (appIdNode == null || StringUtils.isBlank(appIdNode.asText())) {
                chain.doFilter(cloudHttpServletRequestWrapper, response);
                return;
            }
            String appId = appIdNode.asText();
            // 判断权限
            BusinessAppBaseConfig businessAppBaseConfig = iBusinessAppBaseConfigService.getById(appId);
            if (businessAppBaseConfig == null) {
                responseResult((HttpServletResponse) response, ResultUtil.error(ResultEnum.PERMISSION_NOT_EXIST));
                return;
            }
            String token = ((HttpServletRequest) request).getHeader("CC-Authorization");
            JwtUtil jwtUtil = new JwtUtil();
            String userId = jwtUtil.getUserId(token);
            if (businessAppBaseConfig.getBusinessUserId().equals(userId)) {
                responseResult((HttpServletResponse) response, ResultUtil.error(ResultEnum.PERMISSION_NOT_EXIST));
            }
            /***APPID 权限验证**/

            requestWrapper = cloudHttpServletRequestWrapper;
        }
        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }

    }

    @Override
    public void destroy() {

    }

    private void responseResult(HttpServletResponse response, Result<?> result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
//        ServletOutputStream out = null;
        PrintWriter writer = null;
        try {
            // JSON.toJSONString(result)要获取完整的json字符串，每一个字段都要有set和get方法，不然会缺少某个字段
//            response.getWriter().write(JSON.toJSONString(result));
            writer = response.getWriter();
//            writer.write(result.toString());
            writer.write(objectMapper.writeValueAsString(result));
            writer.flush();
        } catch (IOException ex) {
            log.error(ex.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
