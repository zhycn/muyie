package org.muyie.framework.aop;

import org.aspectj.lang.JoinPoint;
import org.springframework.core.annotation.Order;

/**
 * After throwing advice to be used in the Spring AOP framework.
 */
@Order(2)
public interface AfterThrowingAdvice extends Advice {

  void afterThrowing(JoinPoint joinPoint, Throwable throwable);

}
