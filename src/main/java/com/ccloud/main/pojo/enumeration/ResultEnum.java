package com.ccloud.main.pojo.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 响应枚举
 *
 * @author wangjie
 */

@Getter
@AllArgsConstructor
public enum ResultEnum {
    SYSTEM_ERROR(-1, "系统错误"),
    //正常
    SUCCESS(0, "成功"),
    //正常，但数据是空的
    SUCCESS_NULL(0, "暂无数据"),

    /*
     * 错误：访问、参数
     */
    PARAMETER_FORMAT_ERROR(100000, "参数格式错误"),
    PARAMETER_ERROR(100001, "参数错误"),
    PARAMETER_VALID_FAILED(100002, "参数验证失败"),
    TOKEN_HANDLE_ERROR(100003, "Token错误"),
    TOKEN_CHECK_ERROR(100004, "Token校验失败"),
    PARAMETER_VALID_NULL(100005, "参数不能为空"),


    // 用户相关
    USER_NOT_EXIST(200001, "用户不存在"),
    USER_PASSWORD_ERROR(200002, "密码错误"),


    OTHER_ERROR(999999, "其他错误");


    private Integer code;
    private String msg;
}
