package com.ccloud.main.util.exception;

/**
 * Created by Junfu on 2019-02-28.
 * 处理Token异常
 */
public class HandleTokenException extends RuntimeException {

    public HandleTokenException() {
        super();
    }

    public HandleTokenException(String message) {
        super(message);
    }

    public HandleTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
