package com.ccloud.main.config.filter;

import com.ccloud.main.config.jwt.client.ClientJwtUtil;
import com.ccloud.main.config.jwt.pc.PcJwtUtil;
import com.ccloud.main.entity.BusinessAppBaseConfig;
import com.ccloud.main.pojo.enumeration.CloudUtilEnum;
import com.ccloud.main.pojo.enumeration.ResultEnum;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.service.IBusinessAppBaseConfigService;
import com.ccloud.main.util.CloudUtil;
import com.ccloud.main.util.ResultUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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


    @Resource
    private PcJwtUtil pcJwtUtil;

    @Resource
    private ClientJwtUtil clientJwtUtil;


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

            // RequestBody
            JsonNode requestBody = cloudHttpServletRequestWrapper.getRequestBody();
            CloudUtil.set(CloudUtilEnum.CURR_REQUEST_BODY, requestBody);


            // 多端标识和用户 ID
            StringBuffer requestURL = ((HttpServletRequest) request).getRequestURL();
            if (requestURL.indexOf("/api/") != -1) {
                log.info("移动端接口");
                CloudUtil.set(CloudUtilEnum.IS_CLIENT, true);
                String client_token = ((HttpServletRequest) request).getHeader("CL-Authorization");
                if (StringUtils.isBlank(client_token)) {
                    chain.doFilter(cloudHttpServletRequestWrapper, response);
                    return;
                }
                String authorization = client_token.replaceAll("(?i)" + clientJwtUtil.shiroJwtClientProperties.getPrefix(), "");
                CloudUtil.set(CloudUtilEnum.CL_AUTHORIZATION, authorization);
                String userId = clientJwtUtil.getUserId(authorization);
                int id = objectMapper.readTree(userId).get("id").asInt();
                CloudUtil.set(CloudUtilEnum.CURR_USER_ID, id);

            } else {
                log.info("PC端接口");
                CloudUtil.set(CloudUtilEnum.IS_CLIENT, false);
                String pc_token = ((HttpServletRequest) request).getHeader("CC-Authorization");
                if (StringUtils.isBlank(pc_token)) {
                    chain.doFilter(cloudHttpServletRequestWrapper, response);
                    return;
                }
                String authorization = pc_token.replaceAll("(?i)" + pcJwtUtil.shiroJwtPcProperties.getPrefix(), "");
                CloudUtil.set(CloudUtilEnum.CC_AUTHORIZATION, authorization);
                String userId = pcJwtUtil.getUserId(authorization);
                int id = objectMapper.readTree(userId).get("id").asInt();
                CloudUtil.set(CloudUtilEnum.CURR_USER_ID, id);
            }


            /***APP_ID 权限验证**/
            JsonNode appIdNode = requestBody.get("appId");
            if (appIdNode == null || StringUtils.isBlank(appIdNode.asText())) {
                chain.doFilter(cloudHttpServletRequestWrapper, response);
                return;
            }
            Integer userId = (Integer) CloudUtil.get(CloudUtilEnum.CURR_USER_ID);
            String appId = appIdNode.asText();

            if (CloudUtil.isClient()) {

            } else {
                BusinessAppBaseConfig businessAppBaseConfig = iBusinessAppBaseConfigService.getById(appId);
                if (businessAppBaseConfig == null) {
                    responseResult((HttpServletResponse) response, ResultUtil.error(ResultEnum.PERMISSION_NOT_EXIST));
                    return;
                }
                if (!businessAppBaseConfig.getBusinessUserId().equals(userId)) {
                    responseResult((HttpServletResponse) response, ResultUtil.error(ResultEnum.PERMISSION_NOT_EXIST));
                }
            }
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
