package com.muyie.framework.aspectj;

import com.muyie.framework.aop.AroundAdvice;
import com.muyie.framework.config.MuyieProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

/**
 * 监听方法的执行时间
 *
 * @author larry.qi
 * @since 2.7.13
 */
@Slf4j
@Aspect
public class StopWatchAspect implements AroundAdvice {

  private final MuyieProperties.StopWatch properties;

  public StopWatchAspect(MuyieProperties muyieProperties) {
    this.properties = muyieProperties.getStopWatch();
  }

  @Override
  @Pointcut("@annotation(com.muyie.framework.annotation.StopWatch)")
  public void setPointcut() {

  }

  @Override
  @Around("setPointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    final com.muyie.framework.annotation.StopWatch sw = this.getMethod(joinPoint).getAnnotation(com.muyie.framework.annotation.StopWatch.class);
    String value = this.getMethodAlias(joinPoint, sw.value());
    int globalSlowMethodMillis = properties.getSlowMethodMillis();
    int slowMethodMillis = sw.slowMethodMillis() > 0 ? sw.slowMethodMillis() : globalSlowMethodMillis;

    // 启用监听
    final StopWatch stopWatch = new StopWatch(value);
    stopWatch.start();

    try {
      return joinPoint.proceed();
    } finally {
      stopWatch.stop();
      // 打印方法执行时长
      if (stopWatch.getTotalTimeMillis() >= slowMethodMillis) {
        log.info("StopWatch '{}': running time = {} ms", stopWatch.getId(), stopWatch.getTotalTimeMillis());
      }
    }
  }
}
