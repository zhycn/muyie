package com.muyie.problem;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.base.Throwables;
import com.muyie.dto.Response;
import com.muyie.exception.BusinessException;
import com.muyie.exception.ExceptionUtil;
import com.muyie.exception.SystemException;
import com.muyie.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

/**
 * 全局统一异常处理
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
   * Spring MVC 参数校验异常处理
   *
   * @param e        ConstraintViolationException 请求参数校验异常
   * @param response HttpServletResponse
   * @return 响应报文
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public Response handleException(ConstraintViolationException e, HttpServletResponse response) {
    ValidationException ve = ExceptionUtil.validate(e.getMessage(), e);
    log.info(ve.getMessage());
    log.debug(Throwables.getStackTraceAsString(e));
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    return Response.of(ve.getErrorCode());
  }

  /**
   * Spring MVC 参数校验异常处理
   *
   * @param e        BindException 请求参数校验异常
   * @param response HttpServletResponse
   * @return 响应报文
   */
  @ExceptionHandler(BindException.class)
  public Response handleException(BindException e, HttpServletResponse response) {
    String detail = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
    ValidationException ve = ExceptionUtil.validate(detail, e);
    log.info(ve.getMessage());
    log.debug(Throwables.getStackTraceAsString(e));
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    return Response.of(ve.getErrorCode());
  }

  /**
   * Spring MVC 请求参数格式错误处理
   *
   * @param e        InvalidFormatException 请求参数格式错误异常
   * @param response HttpServletResponse
   * @return 响应报文
   */
  @ExceptionHandler(InvalidFormatException.class)
  public Response handleException(InvalidFormatException e, HttpServletResponse response) {
    ValidationException ve = ExceptionUtil.validate(e.getMessage(), e);
    log.info(ve.getMessage());
    log.debug(Throwables.getStackTraceAsString(e));
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    return Response.of(ve.getErrorCode());
  }

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
    log.debug(Throwables.getStackTraceAsString(e));
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
    log.debug(Throwables.getStackTraceAsString(e));
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
    log.debug(Throwables.getStackTraceAsString(e));
    response.setStatus(e.getHttpStatus());
    return Response.of(e.getErrorCode());
  }

}
