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
   *
   * @return message
   */
  String message() default "枚举值不匹配";

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
   * 枚举类，通过接口替代反射方式
   *
   * @return enumClass
   */
  Class<? extends Enum<? extends EnumCheck>> enumClass();

}
