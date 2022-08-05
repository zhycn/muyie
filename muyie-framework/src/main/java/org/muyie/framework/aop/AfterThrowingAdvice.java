package org.muyie.framework.aop;

import org.aspectj.lang.JoinPoint;
import org.springframework.core.annotation.Order;

/**
 * 异常通知，在目标方法抛出异常之后调用。
 */
@Order(2)
public interface AfterThrowingAdvice extends Advice {

  void afterThrowing(JoinPoint joinPoint, Throwable throwable);

}
