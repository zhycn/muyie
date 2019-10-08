package org.muyie.framework.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.annotation.Order;

/**
 * Around advice to be used in the Spring AOP framework.
 */
@Order(1)
public interface AroundAdvice extends Advice {

  Object around(ProceedingJoinPoint joinPoint) throws Throwable;

}
