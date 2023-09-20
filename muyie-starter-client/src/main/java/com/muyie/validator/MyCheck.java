package com.muyie.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义校验 @MyCheck
 *
 * @author larry
 * @since 2.7.14
 */
@Documented
@Constraint(validatedBy = {MyValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface MyCheck {
  /**
   * 错误信息
   *
   * @return message
   */
  String message() default "";

  /**
   * 分组
   *
   * @return groups
   */
  Class<?>[] groups() default {};

  /**
   * 负载
   *
   * @return payload
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * 对象校验处理器
   *
   * @return handler
   */
  Class<? extends MyHandler> handler();

}
