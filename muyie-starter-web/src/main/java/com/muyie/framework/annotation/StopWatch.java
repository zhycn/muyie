package com.muyie.framework.annotation;

import java.lang.annotation.*;

/**
 * 监听方法的执行时间
 *
 * @author larry.qi
 * @since 2.7.13
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StopWatch {

  /**
   * 方法别名
   *
   * @return 方法别名
   */
  String value() default "";

  /**
   * 慢方法阀值设置，超时就会打印运行记录日志。单位毫秒
   *
   * @return 慢方法执行超时时间
   */
  int slowMethodMillis() default -1;

}
