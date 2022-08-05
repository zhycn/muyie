package org.muyie.framework.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.annotation.Order;

/**
 * 环绕通知，在目标方法之前和之后调用。
 */
@Order(1)
public interface AroundAdvice extends Advice {

  Object around(ProceedingJoinPoint joinPoint) throws Throwable;

}
