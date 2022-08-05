package org.muyie.framework.config.aspectj;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Extension Annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface Extension {

  /**
   * 接口名称或描述信息
   *
   * @return 接口名称
   */
  String value() default "";

  /**
   * 是否打印请求与响应日志，默认值：true
   *
   * @return true 表示打印日志
   */
  boolean logWatch() default true;

  /**
   * 开启日志后，日志序列化的信息脱敏规则
   *
   * @return 默认或指定的配置对象
   */
  String configBeanName() default "";

  /**
   * 开启日志后，日志序列化时忽略的字段
   *
   * @return 数组
   */
  String[] ignoreFields() default {};

}
