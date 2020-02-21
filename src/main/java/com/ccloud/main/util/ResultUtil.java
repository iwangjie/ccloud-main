package com.ccloud.main.util;

import com.ccloud.main.pojo.enumeration.ResultEnum;
import com.ccloud.main.pojo.system.Result;
import org.apache.commons.lang3.StringUtils;


/**
 * 响应工具类
 *
 * @author wangjie
 */
public class ResultUtil {

    /**
     * 成功但不带数据
     **/
    public static Result<Object> success() {
        return success(null);
    }

    /**
     * 成功且带数据
     **/
    public static Result<Object> success(Object object) {
        return null != object ? new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), object) : new Result<>(ResultEnum.SUCCESS_NULL.getCode(), ResultEnum.SUCCESS_NULL.getMsg(), StringUtils.EMPTY);
    }

    /**
     * 失败
     **/
    public static Result error(Integer code, String msg) {
        return new Result<>(code, msg, StringUtils.EMPTY);
    }

    /**
     * 失败
     **/
    public static Result<?> error(ResultEnum resultEnum) {
        return new Result<>(resultEnum.getCode(), resultEnum.getMsg(), StringUtils.EMPTY);
    }

//    /**
//     * 失败
//     **/
//    public static Result<?> error(BindingResult bindingResult) {
//        return new Result<>(ResultEnum.PARAMETER_VALID_FAILED.getCode(), ResultEnum.PARAMETER_VALID_FAILED.getMsg() + "：" + bindingResult.getFieldError().getDefaultMessage(), StringUtil.EMPTY);
//    }

    /**
     * 自定义
     **/
    public static Result<Object> success(Integer code, String msg, Object object) {
        return null != object ? new Result<>(code, msg, object) : new Result<>(code, msg, StringUtils.EMPTY);
    }
}