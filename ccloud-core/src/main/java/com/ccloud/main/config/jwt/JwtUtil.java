package com.ccloud.main.config.jwt;

public interface JwtUtil {

    /**
     * 获取token中的用户信息
     *
     * @param userToken
     * @return
     */
    String getUserId(String userToken);

    /**
     * 验证 用户TOKEN
     *
     * @param token
     * @param userToken
     * @return
     */
    boolean verify(String token, String userToken);
}
