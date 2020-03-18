package org.muyie.framework.config.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.muyie.framework.aop.AfterThrowingAdvice;
import org.muyie.framework.aop.AroundAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;

import cn.hutool.core.util.StrUtil;

@Aspect
@Component
public class ExtensionAspect implements AroundAdvice, AfterThrowingAdvice {

  private static final Logger log = LoggerFactory.getLogger(ExtensionAspect.class);

  @Override
  @Pointcut("@annotation(org.muyie.framework.config.aspectj.Extension)")
  public void setPointcut() {}

  /**
   * Pointcut that matches all repositories, services and Web REST endpoints.
   */
  @Pointcut("within(@org.springframework.stereotype.Repository *)"
      + " || within(@org.springframework.stereotype.Service *)"
      + " || within(@org.springframework.web.bind.annotation.RestController *)")
  public void springBeanPointcut() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  @Override
  @AfterThrowing(pointcut = "setPointcut() && springBeanPointcut()", throwing = "e")
  public void afterThrowing(JoinPoint joinPoint, Throwable e) {
    String value = this.getMethod(joinPoint).getAnnotation(Extension.class).value();

    if (StringUtils.isEmpty(value)) {
      value = StrUtil.format("{}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName());
    }

    log.error("Exception: '{}' with cause = {}", value, Throwables.getStackTraceAsString(e));
  }

  @Override
  @Around("setPointcut() && springBeanPointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    Extension extension = this.getMethod(joinPoint).getAnnotation(Extension.class);
    String value = extension.value();

    if (StringUtils.isEmpty(value)) {
      value = StrUtil.format("{}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName());
    }

    StopWatch stopWatch = new StopWatch(value);
    stopWatch.start();

    try {
      if (extension.logger()) {
        log.info("Extension Enter: '{}' with arguments = {}", value,
            JSON.toJSONString(joinPoint.getArgs()));
      }

      Object result = joinPoint.proceed();

      if (extension.logger()) {
        log.info("Extension Exit: '{}' with result = {}", value, JSON.toJSONString(result));
      }

      return result;
    } catch (IllegalArgumentException e) {
      log.error("Extension Illegal argument: '{}' with cause = {}", value,
          Throwables.getStackTraceAsString(e));

      throw e;
    } finally {
      stopWatch.stop();
      log.info(stopWatch.shortSummary());
    }
  }

}
