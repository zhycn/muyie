package com.muyie.framework.aop;

import org.aspectj.lang.JoinPoint;

/**
 * Advice to be run after a join point completes normally (for example, if a method returns without
 * throwing an exception).
 *
 * @author larry.qi
 * @since 2.7.13
 */
public interface AfterReturningAdvice<T> extends Advice {

  /**
   * 通常在一个匹配的方法返回的时候执行，使用 @AfterReturning 注解来声明。
   *
   * @param joinPoint 连接点
   * @param result    返回结果
   */
  void afterReturning(JoinPoint joinPoint, T result);

}
