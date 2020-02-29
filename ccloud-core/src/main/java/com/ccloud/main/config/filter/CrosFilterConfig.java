package com.ccloud.main.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域配置
 * @author wangjie
 * @date 2020-02-21
 */
@Slf4j
@Component
@WebFilter(filterName = "CROSFilter")
public class CrosFilterConfig implements Filter {

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
        log.info("Init CROSFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equals("OPTIONS")) {
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            res.setHeader("Access-Control-Allow-Credentials", "true");

        }


//        if (!org.springframework.util.StringUtils.isEmpty(origin)) {
//            //带cookie的时候，origin必须是全匹配，不能使用*
//        }
//        res.addHeader("Access-Control-Allow-Methods", "*");
//        String headers = req.getHeader("Access-Control-Request-Headers");
//        if (!org.springframework.util.StringUtils.isEmpty(headers)) {
//            // 支持所有自定义头
//            res.addHeader("Access-Control-Allow-Headers", headers);
//        }
//        res.addHeader("Access-Control-Max-Age", "3600");
//        // enable cookie
//        res.addHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("CROSFilter destroyed");
    }
}
