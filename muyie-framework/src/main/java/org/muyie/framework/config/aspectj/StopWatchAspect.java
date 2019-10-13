package org.muyie.framework.config.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.muyie.framework.aop.AroundAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import cn.hutool.core.util.StrUtil;

@Aspect
@Configuration
public class StopWatchAspect implements AroundAdvice {

  private static final Logger log = LoggerFactory.getLogger(StopWatchAspect.class);

  @Override
  @Pointcut("@annotation(org.muyie.framework.config.aspectj.StopWatch)")
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
  @Around("setPointcut() && springBeanPointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    org.muyie.framework.config.aspectj.StopWatch sw =
        this.getMethod(joinPoint).getAnnotation(org.muyie.framework.config.aspectj.StopWatch.class);
    String value = sw.value();
    if (StringUtils.isEmpty(value)) {
      value = StrUtil.format("{}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName());
    }

    StopWatch stopWatch = new StopWatch(value);
    stopWatch.start();
    try {
      return joinPoint.proceed();
    } finally {
      stopWatch.stop();
      log.info(stopWatch.shortSummary());
    }
  }

}
