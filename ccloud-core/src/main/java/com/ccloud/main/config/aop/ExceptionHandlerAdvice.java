package com.ccloud.main.config.aop;

import com.ccloud.main.controller.BaseController;
import com.ccloud.main.pojo.enumeration.ResultEnum;
import com.ccloud.main.pojo.system.Result;
import com.ccloud.main.util.ResultUtil;
import com.ccloud.main.util.exception.HandleTokenException;
import com.ccloud.main.util.exception.ParamsErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @author wangjie
 * @date 2020-02-21
 */
@Slf4j
@Configuration
@RestControllerAdvice(basePackageClasses = BaseController.class)
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    Result<?> exceptionHandle(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        if (StringUtils.isBlank(ex.getMessage())) {
            return ResultUtil.error(ResultEnum.SYSTEM_ERROR);
        } else {
            return ResultUtil.error(500, ex.getMessage());
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    Result<?> JSONExceptionHandle(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        return ResultUtil.error(ResultEnum.PERMISSION_NOT_EXIST);
    }

    @ExceptionHandler(ParamsErrorException.class)
    Result<?> ParamsErrorExceptionHandle(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        return ResultUtil.error(ResultEnum.PARAMETER_ERROR);
    }

    @ExceptionHandler(HandleTokenException.class)
    Result<?> HandleTokenExceptionHandle(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        return ResultUtil.error(ResultEnum.TOKEN_HANDLE_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    Result<?> constraintViolationExceptionHandle(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        return ResultUtil.error(ResultEnum.TOKEN_HANDLE_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

}
