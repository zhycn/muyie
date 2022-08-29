package org.muyie.framework.config.aspectj;

import com.google.common.base.Throwables;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.muyie.framework.aop.AfterAdvice;
import org.muyie.framework.aop.AfterThrowingAdvice;
import org.muyie.framework.aop.AroundAdvice;
import org.muyie.framework.config.SpringContextHolder;
import org.muyie.framework.desensitized.DesensitizedDataConfig;
import org.muyie.framework.desensitized.DesensitizedDataUtil;
import org.muyie.framework.logback.LogTraceIdConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;

@Aspect
@Component
public class ExtensionAspect implements AroundAdvice, AfterThrowingAdvice, AfterAdvice, WebMvcConfigurer {

  private static final Logger log = LoggerFactory.getLogger(ExtensionAspect.class);

  /**
   * 基于configBeanName获取序列化规则对象
   *
   * @param configBeanName 序列化信息脱敏规则配置的BeanName
   * @return 序列化信息脱敏规则对象
   */
  private static DesensitizedDataConfig get(String value, String configBeanName) {
    DesensitizedDataConfig config = DesensitizedDataUtil.DEFAULT_CONFIG; // 初始化
    try {
      if (StringUtils.isEmpty(configBeanName)) {
        config = SpringContextHolder.getApplicationContext().getBean(DesensitizedDataConfig.class);
      } else {
        config = SpringContextHolder.getApplicationContext().getBean(configBeanName, DesensitizedDataConfig.class);
      }
    } catch (Exception e) {
      if (StringUtils.isEmpty(configBeanName)) {
        log.warn("configBeanName未指定，多个DesensitizedDataConfig出现时，注意使用@Primary注解");
      } else {
        log.warn("方法【{}】指定的configBeanName【{}】无效", value, configBeanName);
      }
    }
    return config;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new HandlerInterceptor() {
      @Override
      public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // WEB请求执行完成后，清除当前线程的MDC数据
        LogTraceIdConverter.clear();
      }
    }).addPathPatterns("/**");
  }

  @Override
  @Pointcut("@annotation(org.muyie.framework.config.aspectj.Extension)")
  public void setPointcut() {
  }

  /**
   * Pointcut that matches all repositories, services and Web REST endpoints.
   */
  @Pointcut("within(@org.springframework.stereotype.Repository *)"
    + " || within(@org.springframework.stereotype.Component *)"
    + " || within(@org.springframework.stereotype.Controller *)"
    + " || within(@org.springframework.stereotype.Service *)"
    + " || within(@org.springframework.web.bind.annotation.RestController *)")
  public void springBeanPointcut() {
    // Method is empty as this is just a Pointcut, the implementations are in the
    // advices.
  }

  @Override
  @AfterThrowing(pointcut = "setPointcut() && springBeanPointcut()", throwing = "e")
  public void afterThrowing(final JoinPoint joinPoint, final Throwable e) {
    String value = this.getMethod(joinPoint).getAnnotation(Extension.class).value();

    if (StringUtils.isEmpty(value)) {
      value = StrUtil.format("{}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName());
    }

    log.error("Exception: '{}' with cause = {}", value, Throwables.getStackTraceAsString(e));
  }

  @Override
  @Around("setPointcut() && springBeanPointcut()")
  public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
    final Extension extension = this.getMethod(joinPoint).getAnnotation(Extension.class);
    String value = extension.value();
    DesensitizedDataConfig config = get(value, extension.configBeanName());
    String[] ignoreFields = extension.ignoreFields();

    if (StringUtils.isEmpty(value)) {
      value = StrUtil.format("{}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName());
    }

    // 启用监听
    final StopWatch stopWatch = new StopWatch(value);
    stopWatch.start();

    try {
      if (extension.logWatch()) {
        String json = DesensitizedDataUtil.toJson(joinPoint.getArgs(), config, ignoreFields);
        log.info("Extension Enter: '{}' with arguments = {}", value, json);
      }

      final Object result = joinPoint.proceed();

      if (extension.logWatch()) {
        String json = DesensitizedDataUtil.toJson(result, config, ignoreFields);
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
  @After("setPointcut() && springBeanPointcut()")
  public void after(JoinPoint joinPoint) {
    // 方法执行完成后，清除当前线程的MDC数据
    LogTraceIdConverter.clear();
  }
}
