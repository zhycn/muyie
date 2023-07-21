package com.muyie.validator;

import org.springframework.beans.BeanUtils;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义校验器（正则表达式不好处理的情况下，可以通过自定义校验来解决）
 *
 * @author larry
 * @since 2.7.14
 */
public class MyValidator implements ConstraintValidator<MyCheck, Object> {

  private MyHandler myHandler;

  @Override
  public void initialize(MyCheck constraintAnnotation) {
    Class<? extends MyHandler> clazz = constraintAnnotation.handler();
    if (Objects.nonNull(clazz)) {
      this.myHandler = BeanUtils.instantiateClass(clazz);
    }
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (Objects.isNull(value) || Objects.isNull(myHandler)) {
      return true;
    }
    return myHandler.isValid(value);
  }

}
