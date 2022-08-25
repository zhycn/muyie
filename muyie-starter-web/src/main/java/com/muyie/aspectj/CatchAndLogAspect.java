package com.muyie.aspectj;

import com.google.common.base.Throwables;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.muyie.MuyieConstants;
import com.muyie.annotation.CatchAndLog;
import com.muyie.aop.AfterAdvice;
import com.muyie.aop.AfterThrowingAdvice;
import com.muyie.aop.AroundAdvice;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * WEB请求执行完成后，将清除当前线程的MDC缓存数据。非WEB请求，需要添加 @CatchAndLog 注解来清除当前线程的MDC缓存数据。
 *
 * @author larry.qi
 * @since 1.2.6
 */
@Slf4j
@Aspect
public class CatchAndLogAspect implements AfterAdvice, AroundAdvice, AfterThrowingAdvice, WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new HandlerInterceptor() {
      @Override
      public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // WEB请求执行完成后，清除当前线程的MDC数据
        MDC.remove(MuyieConstants.LOG_TRACE_ID);
      }
    }).addPathPatterns("/**");
  }

  @Override
  @Pointcut("@annotation(com.muyie.annotation.CatchAndLog) && execution(public * *(..))")
  public void setPointcut() {
  }

  @Override
  @AfterThrowing(pointcut = "setPointcut()", throwing = "e")
  public void afterThrowing(final JoinPoint joinPoint, final Throwable e) {
    String value = this.getMethod(joinPoint).getAnnotation(CatchAndLog.class).value();

    if (StringUtils.isBlank(value)) {
      value = StrUtil.format("{}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName());
    }

    log.error("Exception: '{}' with cause = {}", value, Throwables.getStackTraceAsString(e));
  }

  @Override
  @Around("setPointcut()")
  public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
    final CatchAndLog catchAndLog = this.getMethod(joinPoint).getAnnotation(CatchAndLog.class);
    String value = catchAndLog.value();
    String[] ignoreFields = catchAndLog.ignoreFields();

    if (StringUtils.isBlank(value)) {
      value = StrUtil.format("{}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName());
    }

    // 启用监听
    final StopWatch stopWatch = new StopWatch(value);
    stopWatch.start();

    try {
      if (catchAndLog.logWatch()) {
        String json = JSON.toJSONString(joinPoint.getArgs(), SerializerFeature.IgnoreErrorGetter);
        log.info("Extension Enter: '{}' with arguments = {}", value, json);
      }

      final Object result = joinPoint.proceed();

      if (catchAndLog.logWatch()) {
        String json = JSON.toJSONString(result, SerializerFeature.IgnoreErrorGetter);
        log.info("Extension Exit: '{}' with result = {}", value, json);
      }

      return result;
    } catch (final IllegalArgumentException e) {
      log.error("Extension Illegal argument: '{}' with cause = {}", value,
        Throwables.getStackTraceAsString(e));
      throw e;
    } finally {
      stopWatch.stop();
      log.info("StopWatch '" + stopWatch.getId() + "': running time = "
        + stopWatch.getTotalTimeMillis() + " ms");
    }
  }

  @Override
  @After("setPointcut()")
  public void after(JoinPoint joinPoint) {
    // 方法执行完成后，清除当前线程的MDC数据
    MDC.remove(MuyieConstants.LOG_TRACE_ID);
  }

}
