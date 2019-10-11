package org.muyie.framework.config.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.muyie.framework.aop.AroundAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Aspect
@Configuration
public class StopWatchConfiguration implements AroundAdvice {

  private static final Logger log = LoggerFactory.getLogger(StopWatchConfiguration.class);

  @Override
  @Pointcut("@annotation(org.muyie.framework.config.aspectj.StopWatch)")
  public void setPointcut() {}

  @Override
  @Around("setPointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    Method method = this.getMethod(joinPoint);
    org.muyie.framework.config.aspectj.StopWatch sw =
        method.getAnnotation(org.muyie.framework.config.aspectj.StopWatch.class);
    StopWatch stopWatch = new StopWatch(sw.value());
    stopWatch.start();
    try {
      return joinPoint.proceed();
    } finally {
      stopWatch.stop();
      log.info(stopWatch.shortSummary());
    }
  }

}
