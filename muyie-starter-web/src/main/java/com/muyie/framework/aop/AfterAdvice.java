package com.muyie.framework.aop;

import org.aspectj.lang.JoinPoint;

/**
 * Advice to be run regardless of the means by which a join point exits (normal or exceptional
 * return).
 *
 * @author larry.qi
 * @since 1.2.6
 */
public interface AfterAdvice extends Advice {

  /**
   * 不论一个方法是如何结束的，最终通知都会运行。使用 @After 注解来声明，最终通知必须准备处理正常返回和异常返回两种情况，通常用它来释放资源。
   *
   * @param joinPoint 连接点
   */
  void after(JoinPoint joinPoint);

}
