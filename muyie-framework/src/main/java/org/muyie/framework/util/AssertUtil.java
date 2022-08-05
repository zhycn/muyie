package org.muyie.framework.util;

import org.hibernate.validator.HibernateValidator;
import org.springframework.util.StringUtils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

/**
 * 实体对象验证工具类
 */
public final class AssertUtil {

  private AssertUtil() {
  }

  /**
   * Validates all constraints on {@code object}.
   *
   * @param object object to validate
   * @param groups the group or list of groups targeted for validation (defaults to
   *               {@link Default})
   * @throws IllegalArgumentException if object is {@code null} or if {@code null} is passed to the
   *                                  varargs groups
   * @throws ValidationException      if a non recoverable error happens during the validation
   *                                  process
   */
  public static void validate(Object object, Class<?>... groups) {
    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
      .failFast(true).buildValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<Object>> sets = validator.validate(object, groups);

    for (ConstraintViolation<Object> o : sets) {
      if (StringUtils.hasText(o.getMessage())) {
        throw new ValidationException(o.getMessage());
      } else {
        throw new ValidationException();
      }
    }
  }

}
