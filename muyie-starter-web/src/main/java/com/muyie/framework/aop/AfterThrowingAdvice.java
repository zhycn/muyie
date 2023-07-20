package com.muyie.framework.aop;

import org.aspectj.lang.JoinPoint;

/**
 * Advice to be run if a method exits by throwing an exception.
 *
 * @author larry.qi
 * @since 2.7.13
 */
public interface AfterThrowingAdvice extends Advice {

  /**
   * 在一个方法抛出异常后执行，使用 @AfterThrowing 注解来声明。
   *
   * @param joinPoint 连接点
   * @param ex        异常对象
   */
  void afterThrowing(JoinPoint joinPoint, Throwable ex);

}
