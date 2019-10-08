package org.muyie.framework.aop;

import org.aspectj.lang.JoinPoint;
import org.springframework.core.annotation.Order;

/**
 * After returning advice to be used in the Spring AOP framework.
 */
@Order(1)
public interface AfterReturningAdvice<T> extends Advice {

  void afterReturning(JoinPoint joinPoint, T result);

}
