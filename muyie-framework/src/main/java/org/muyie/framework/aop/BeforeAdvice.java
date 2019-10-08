package org.muyie.framework.aop;

import org.aspectj.lang.JoinPoint;
import org.springframework.core.annotation.Order;

/**
 * Before advice to be used in the Spring AOP framework.
 */
@Order(1)
public interface BeforeAdvice extends Advice {

  void before(JoinPoint joinPoint);

}
