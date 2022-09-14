package com.muyie.problem;

import com.google.common.base.Throwables;

import com.muyie.dto.Response;
import com.muyie.exception.BusinessException;
import com.muyie.exception.ErrorCodeDefaults;
import com.muyie.exception.SystemException;
import com.muyie.exception.ValidationException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 *
 * @author larry.qi
 * @since 1.2.10
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  /**
   * 请求参数校验异常
   *
   * @param e        ValidationException 请求参数校验异常
   * @param response HttpServletResponse
   * @return 响应报文
   */
  @ExceptionHandler(ValidationException.class)
  public Response handleException(ValidationException e, HttpServletResponse response) {
    log.info(e.getMessage());
    response.setStatus(e.getHttpStatus());
    return Response.of(e.getErrorCode());
  }

  /**
   * 业务处理异常
   *
   * @param e        BusinessException 业务处理异常
   * @param response HttpServletResponse
   * @return 响应报文
   */
  @ExceptionHandler(BusinessException.class)
  public Response handleException(BusinessException e, HttpServletResponse response) {
    log.info(e.getMessage());
    response.setStatus(e.getHttpStatus());
    return Response.of(e.getErrorCode());
  }

  /**
   * 已知的系统异常
   *
   * @param e        SystemException 已知的系统异常
   * @param response HttpServletResponse
   * @return 响应报文
   */
  @ExceptionHandler(SystemException.class)
  public Response handleException(SystemException e, HttpServletResponse response) {
    log.error(e.getMessage());
    response.setStatus(e.getHttpStatus());
    return Response.of(e.getErrorCode());
  }

  /**
   * 未知的系统异常
   *
   * @param e        Throwable 未知的系统异常
   * @param response HttpServletResponse
   * @return 响应报文
   */
  @ExceptionHandler(Throwable.class)
  public Response handleException(Throwable e, HttpServletResponse response) {
    log.error(Throwables.getStackTraceAsString(e));
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    return Response.of(ErrorCodeDefaults.S0500);
  }

}
