package com.ccloud.main.pojo.bean;

import lombok.Data;

/**
 * 验证码
 * @author wangjie
 */
@Data
public class VerifyCode {

    private String code;

    private byte[] imgBytes;

    private long expireTime;
}
