package org.muyie.framework.config.aspectj;

import static org.muyie.framework.config.MuyieConstants.SPRING_PROFILE_DEBUG;
import static org.muyie.framework.config.MuyieConstants.SPRING_PROFILE_DEVELOPMENT;

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
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;

import cn.hutool.core.util.StrUtil;

@Aspect
@Profile({SPRING_PROFILE_DEVELOPMENT, SPRING_PROFILE_DEBUG})
public class LogAuditedAspect implements AroundAdvice, AfterThrowingAdvice {

  private static final Logger log = LoggerFactory.getLogger(LogAuditedAspect.class);

  @Override
  @Pointcut("@annotation(org.muyie.framework.config.aspectj.LogAudited)")
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
    String value = this.getMethod(joinPoint).getAnnotation(LogAudited.class).value();

    if (StringUtils.isEmpty(value)) {
      value = StrUtil.format("{}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName());
    }

    log.error("LogAudited Exception: '{}' with cause = {}", value,
        Throwables.getStackTraceAsString(e));
  }

  @Override
  @Around("setPointcut() && springBeanPointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    String value = this.getMethod(joinPoint).getAnnotation(LogAudited.class).value();

    if (StringUtils.isEmpty(value)) {
      value = StrUtil.format("{}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName());
    }

    try {
      log.debug("LogAudited Enter: '{}' with arguments = {}", value,
          JSON.toJSONString(joinPoint.getArgs()));

      Object result = joinPoint.proceed();

      log.debug("LogAudited Exit: '{}' with result = {}", value, JSON.toJSONString(result));

      return result;
    } catch (IllegalArgumentException e) {
      log.error("LogAudited Illegal argument: '{}' in {}", JSON.toJSONString(joinPoint.getArgs()),
          value);

      throw e;
    }
  }

}
