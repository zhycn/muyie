package com.muyie.framework.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Advice that surrounds a join point such as a method invocation. This is the most powerful kind of
 * advice. Around advice can perform custom behavior before and after the method invocation. It is
 * also responsible for choosing whether to proceed to the join point or to shortcut the advised
 * method execution by returning its own return value or throwing an exception.
 *
 * @author larry.qi
 * @since 1.2.6
 */
public interface AroundAdvice extends Advice {

  /**
   * 环绕通知在一个方法执行之前和之后执行，环绕通知使用 @Around 注解来声明。通知的第一个参数必须是 ProceedingJoinPoint 类型。在通知体内，调用
   * ProceedingJoinPoint 的 proceed()方法会导致后台的连接点方法执行。proceed()方法也可能会被调用并且传入一个
   * Object[]对象-该数组中的值将被作为方法执行时的参数。
   *
   * @param joinPoint 连接点
   * @return 返回结果
   * @throws Throwable 异常
   */
  Object around(ProceedingJoinPoint joinPoint) throws Throwable;

}
