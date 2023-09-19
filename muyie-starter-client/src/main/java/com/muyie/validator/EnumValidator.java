package com.muyie.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 自定义枚举校验器
 *
 * @author larry
 * @since 2.7.13
 */
public class EnumValidator implements ConstraintValidator<EnumValue, String> {

  private Class<? extends Enum<? extends EnumCheck>> enumClass;
  private Enum<? extends EnumCheck>[] enumConstants;

  @Override
  public void initialize(EnumValue constraintAnnotation) {
    this.enumClass = constraintAnnotation.enumClass();
    this.enumConstants = enumClass.getEnumConstants();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(value) || Objects.isNull(enumClass) || Objects.isNull(enumConstants)) {
      return true;
    }
    for (Enum<? extends EnumCheck> e : enumConstants) {
      if (((EnumCheck) e).isValid(value)) {
        return true;
      }
    }
    return false;
  }
}
