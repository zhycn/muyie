package com.muyie.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CatchAndLog Annotation
 *
 * @author larry.qi
 * @since 1.2.6
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CatchAndLog {

  /**
   * 接口描述信息
   *
   * @return 接口描述信息
   */
  String value() default "";

  /**
   * 是否打印请求和响应日志
   *
   * @return true 表示打印日志
   */
  boolean logWatch() default true;

  /**
   * 方法执行完成后，清除当前线程的MDC数据（非 WEB 请求方法需设置为 true）
   *
   * @return true 清除当前线程的MDC数据
   */
  boolean flush() default false;

  /**
   * 开启日志后，指定日志序列化时忽略的字段
   *
   * @return 忽略的字段
   */
  String[] ignoreFields() default {};

  /**
   * 慢方法阀值设置，超时就会打印运行记录日志。单位毫秒
   *
   * @return 慢方法执行超时时间
   */
  int slowMethodMillis() default -1;

}
