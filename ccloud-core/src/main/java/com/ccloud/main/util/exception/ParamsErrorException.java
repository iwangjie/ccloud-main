package com.ccloud.main.util.exception;

/**
 * Created by Junfu on 2019-02-28.
 * 参数错误异常
 */
public class ParamsErrorException extends RuntimeException {

    public ParamsErrorException() {
        super();
    }

    public ParamsErrorException(String message) {
        super(message);
    }

    public ParamsErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
