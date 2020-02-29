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
 *
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
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "*");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Allow-Credentials", "true");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("CROSFilter destroyed");
    }
}
