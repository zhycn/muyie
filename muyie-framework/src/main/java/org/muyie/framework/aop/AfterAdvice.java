package org.muyie.framework.aop;

import org.aspectj.lang.JoinPoint;
import org.springframework.core.annotation.Order;

/**
 * 前置通知，在目标方法之前调用。
 */
@Order(1)
public interface AfterAdvice extends Advice {

  void after(JoinPoint joinPoint);

}
