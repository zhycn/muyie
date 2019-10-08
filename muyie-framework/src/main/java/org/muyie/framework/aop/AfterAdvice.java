package org.muyie.framework.aop;

import org.aspectj.lang.JoinPoint;
import org.springframework.core.annotation.Order;

/**
 * After advice to be used in the Spring AOP framework.
 */
@Order(1)
public interface AfterAdvice extends Advice {

  void after(JoinPoint joinPoint);

}
