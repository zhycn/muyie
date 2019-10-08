package org.muyie.framework.aop;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public interface Advice {

  default Method getMethod(JoinPoint joinPoint) {
    return ((MethodSignature) joinPoint.getSignature()).getMethod();
  }

  default Method getMethod(ProceedingJoinPoint joinPoint) {
    return ((MethodSignature) joinPoint.getSignature()).getMethod();
  }

  default HttpServletRequest getRequest() {
    return getRequestAttributes().getRequest();
  }

  default ServletRequestAttributes getRequestAttributes() {
    return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
  }

  default HttpServletResponse getResponse() {
    return getRequestAttributes().getResponse();
  }

  default ServletContext getServletContext() {
    return ContextLoader.getCurrentWebApplicationContext().getServletContext();
  }

  void setPointcut();

}
