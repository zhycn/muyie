package com.muyie.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义枚举类型校验 @EnumValue
 *
 * @author larry
 */
@Documented
@Constraint(validatedBy = {EnumValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface EnumValue {
  /**
   * 提示信息
   */
  String message() default "枚举值不匹配";

  /**
   * 分组
   */
  Class<?>[] groups() default {};

  /**
   * 负载
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * 枚举类，通过接口替代反射方式
   */
  Class<? extends Enum<? extends EnumCheck>> enumClass();

}
