package org.muyie.framework.aop;

import org.aspectj.lang.JoinPoint;
import org.springframework.core.annotation.Order;

/**
 * 返回通知，在目标方法成功执行之后调用。
 */
@Order(1)
public interface AfterReturningAdvice<T> extends Advice {

  void afterReturning(JoinPoint joinPoint, T result);

}
