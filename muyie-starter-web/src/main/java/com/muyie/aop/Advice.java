package com.muyie.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 抽象的 Advice 接口，提供使用 AOP 时的常用方法。
 *
 * @author larry.qi
 * @since 1.2.6
 */
public interface Advice {

  /**
   * 获取方法对象
   *
   * @param joinPoint 切入点对象
   * @return Method
   */
  default Method getMethod(JoinPoint joinPoint) {
    return ((MethodSignature) joinPoint.getSignature()).getMethod();
  }

  /**
   * 获取方法对象
   *
   * @param proceedingJoinPoint 切入点对象
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
   * 获取应用程序上下文对象
   *
   * @return WebApplicationContext
   */
  default WebApplicationContext getApplicationContext() {
    return ContextLoader.getCurrentWebApplicationContext();
  }

  /**
   * 设置切入点
   */
  void setPointcut();

}
