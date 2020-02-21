package com.ccloud.main.controller;

import com.ccloud.main.util.JsonUtils;
import com.ccloud.main.util.exception.HandleTokenException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

/**
 * 公共 Controller
 *
 * @author wangjie
 */
@Slf4j
@Controller
public class BaseController {


    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * 获取Cookie
     *
     * @param cookieName
     * @return
     */
    String getCookie(String cookieName) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 设置Cookie
     *
     * @param cookieName
     * @param value
     * @param age
     */
    void setCookie(String cookieName, String value, int age) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(age);
        // cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static String createTicket() {
        String ticket = "AUTH_TICKET_" + UUID.randomUUID().toString();
        return ticket;
    }

    /**
     * 处理请求参数（String转Json类型）检查token并在返回时添加当前用户userId
     *
     * @param paramData
     * @return
     */
    Map<String, Object> getJsonParam(String paramData) {
        return getJsonParam(JsonUtils.toMap(paramData));
    }


    /**
     * 处理请求参数（String转Json类型）检查token并在返回时添加当前用户userId
     *
     * @param paramData
     * @return
     */
    Map<String, Object> getJsonParam(Map<String, Object> paramData) {
        if ((null == paramData.get("token")) || (paramData.get("token").equals(StringUtils.EMPTY))) {
            throw new HandleTokenException("Token为Null或\"\"");
        }

        paramData.put("userId", getUserIdByToken(paramData.get("token").toString()));
        return paramData;
    }

    /**
     * 从token中获取userId
     *
     * @param token
     * @return
     */
    private long getUserIdByToken(@NotNull String token) {
        String[] array = token.split("-");
        if (!(array.length > 1)) {
            throw new HandleTokenException("从Token中获取userId失败(Token)：" + token);
        }
        try {
            return Long.valueOf(array[0]);
        } catch (NumberFormatException e) {
            throw new HandleTokenException("从Token中获取userId失败(Token)：" + token);
        }
    }

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired(required = false)
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
