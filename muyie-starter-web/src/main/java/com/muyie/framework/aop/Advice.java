package com.muyie.framework.aop;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 抽象的 Advice 接口，提供使用 AOP 时的常用方法。
 *
 * @author larry.qi
 * @since 2.7.13
 */
public interface Advice {

  /**
   * 获取方法对象
   *
   * @param joinPoint 连接点
   * @return Method
   */
  default Method getMethod(JoinPoint joinPoint) {
    return ((MethodSignature) joinPoint.getSignature()).getMethod();
  }

  /**
   * 获取方法对象
   *
   * @param proceedingJoinPoint 连接点
   * @return Method
   */
  default Method getMethod(ProceedingJoinPoint proceedingJoinPoint) {
    return ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
  }

  /**
   * 获取请求对象
   *
   * @return HttpServletRequest
   */
  default HttpServletRequest getHttpServletRequest() {
    return getServletRequestAttributes().getRequest();
  }

  /**
   * 获取请求属性
   *
   * @return ServletRequestAttributes
   */
  default ServletRequestAttributes getServletRequestAttributes() {
    return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
  }

  /**
   * 获取响应对象
   *
   * @return HttpServletResponse
   */
  default HttpServletResponse getHttpServletResponse() {
    return getServletRequestAttributes().getResponse();
  }

  /**
   * 获取请求上下文对象
   *
   * @return ServletContext
   */
  default ServletContext getServletContext() {
    return getApplicationContext().getServletContext();
  }

  /**
   * 获取应用上下文对象
   *
   * @return WebApplicationContext
   */
  default WebApplicationContext getApplicationContext() {
    return ContextLoader.getCurrentWebApplicationContext();
  }

  /**
   * 如果注解中未指定方法别名，则获取方法名称
   *
   * @param joinPoint 连接点
   * @param value     方法别名
   * @return 方法别名
   */
  default String getMethodAlias(JoinPoint joinPoint, String value) {
    return Optional.ofNullable(Strings.emptyToNull(value)).orElseGet(() ->
      StrUtil.format("{}.{}()", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName()));
  }

  /**
   * 设置切入点
   */
  void setPointcut();

}
