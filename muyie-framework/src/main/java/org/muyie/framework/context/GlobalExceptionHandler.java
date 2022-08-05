package org.muyie.framework.context;

import com.google.common.base.Throwables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
@Configuration
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  // 业务异常
  @ExceptionHandler(BaseException.class)
  public IResult handleException(BaseException e, HttpServletResponse response) {
    response.setStatus(e.getStatus().value());
    log.error(Throwables.getStackTraceAsString(e));
    return IResult.fail(e.getCode());
  }

  // 系统异常
  @ExceptionHandler(Throwable.class)
  public IResult handleException(Throwable e, HttpServletResponse response) {
    log.error(Throwables.getStackTraceAsString(e));
    return IResult.fail(CodeDefaults.SC_99999);
  }

}
