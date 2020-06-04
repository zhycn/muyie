package org.muyie.framework.exception;

import javax.servlet.http.HttpServletResponse;

import org.muyie.framework.context.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.common.base.Throwables;

@Configuration
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MuyieException.class)
  public Response handleException(MuyieException e, HttpServletResponse response) {
    response.setStatus(e.getStatus().value());
    log.error(Throwables.getStackTraceAsString(e));
    return Response.fail(e.getResponseCode());
  }

}
