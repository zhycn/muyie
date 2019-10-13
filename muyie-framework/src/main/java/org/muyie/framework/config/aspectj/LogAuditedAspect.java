package org.muyie.framework.config.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.muyie.framework.aop.AfterThrowingAdvice;
import org.muyie.framework.aop.AroundAdvice;
import org.muyie.framework.config.MuyieConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.hutool.core.util.StrUtil;

@Aspect
@Configuration
public class LogAuditedAspect implements AroundAdvice, AfterThrowingAdvice {

  private static final Logger log = LoggerFactory.getLogger(LogAuditedAspect.class);

  private final Environment env;

  public LogAuditedAspect(Environment env) {
    this.env = env;
  }

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
    LogAudited la = this.getMethod(joinPoint).getAnnotation(LogAudited.class);
    String value = la.value();
    if (StringUtils.isEmpty(value)) {
      value = StrUtil.format("{}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName());
    }

    if (env.acceptsProfiles(Profiles.of(MuyieConstants.SPRING_PROFILE_DEVELOPMENT,
        MuyieConstants.SPRING_PROFILE_DEBUG))) {
      log.error("LogAudited Exception in '{}' with cause = '{}' and exception = '{}'", value,
          e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
    } else {
      log.error("LogAudited Exception in '{}' with cause = {}", value,
          e.getCause() != null ? e.getCause() : "NULL");
    }
  }

  @Override
  @Around("setPointcut() && springBeanPointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    LogAudited la = this.getMethod(joinPoint).getAnnotation(LogAudited.class);
    String value = la.value();
    if (StringUtils.isEmpty(value)) {
      value = StrUtil.format("{}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName());
    }
    log.debug("LogAudited Enter: '{}' with argument[s] = {}", value,
        JSON.toJSONString(joinPoint.getArgs()));

    try {
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
