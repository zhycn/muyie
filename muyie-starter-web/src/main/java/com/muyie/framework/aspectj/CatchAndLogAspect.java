package com.muyie.framework.aspectj;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import com.alibaba.fastjson2.filter.PropertyFilter;
import com.muyie.framework.annotation.CatchAndLog;
import com.muyie.framework.aop.AroundAdvice;
import com.muyie.framework.config.MuyieConstants;
import com.muyie.framework.config.MuyieProperties;
import com.muyie.framework.logging.LogTraceIdConverter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * WEB请求执行完成后，将清除当前线程的MDC缓存数据。非WEB请求，需要添加 @CatchAndLog 注解来清除当前线程的MDC缓存数据。
 *
 * @author larry.qi
 * @since 1.2.6
 */
@Slf4j
@Aspect
@Configuration
public class CatchAndLogAspect implements AroundAdvice, WebMvcConfigurer {

  private final MuyieProperties.StopWatch properties;

  public CatchAndLogAspect(MuyieProperties muyieProperties) {
    this.properties = muyieProperties.getStopWatch();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new HandlerInterceptor() {

      @Override
      public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        LogTraceIdConverter.set(request.getHeader(MuyieConstants.REQUEST_ID));
        return HandlerInterceptor.super.preHandle(request, response, handler);
      }

      @Override
      public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        // WEB请求执行完成后，添加响应头信息，并清除当前线程的MDC数据
        response.addHeader(MuyieConstants.REQUEST_ID, LogTraceIdConverter.get());
        LogTraceIdConverter.close();
      }
    }).addPathPatterns("/**");
  }

  @Override
  @Pointcut("@annotation(com.muyie.framework.annotation.CatchAndLog)")
  public void setPointcut() {

  }

  @Override
  @Around("setPointcut()")
  public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
    final CatchAndLog catchAndLog = this.getMethod(joinPoint).getAnnotation(CatchAndLog.class);
    String value = this.getMethodAlias(joinPoint, catchAndLog.value());
    int globalSlowMethodMillis = properties.getSlowMethodMillis();
    int slowMethodMillis = catchAndLog.slowMethodMillis() > 0 ? catchAndLog.slowMethodMillis() : globalSlowMethodMillis;
    String[] ignoreFields = catchAndLog.ignoreFields();

    // 启用监听
    final StopWatch stopWatch = new StopWatch(value);
    stopWatch.start();

    try {
      // 日志中忽略属性过滤器
      Filter filter = (PropertyFilter) (o, k, v) -> !Arrays.asList(ignoreFields).contains(k);
      if (catchAndLog.logWatch()) {
        String json = JSON.toJSONString(joinPoint.getArgs(), filter, JSONWriter.Feature.IgnoreErrorGetter);
        log.info("CatchAndLog Enter: '{}' with arguments = {}", value, json);
      }
      final Object result = joinPoint.proceed();
      if (catchAndLog.logWatch()) {
        String json = JSON.toJSONString(result, filter, JSONWriter.Feature.IgnoreErrorGetter);
        log.info("CatchAndLog Exit: '{}' with result = {}", value, json);
      }
      return result;
    } finally {
      stopWatch.stop();
      // 打印方法执行时长
      if (stopWatch.getTotalTimeMillis() >= slowMethodMillis) {
        log.info("StopWatch '{}': running time = {} ms", stopWatch.getId(), stopWatch.getTotalTimeMillis());
      }
      // 方法执行完成后，清除当前线程的MDC数据
      if (catchAndLog.flush()) {
        LogTraceIdConverter.close();
      }
    }
  }

}
