package com.muyie.exception;

import org.hibernate.validator.HibernateValidator;
import org.springframework.validation.BindingResult;

import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import lombok.experimental.UtilityClass;

/**
 * 异常工具类（AssertUtil）的提供了一些静态方法，支持参数校验异常（validate）、业务处理异常（business）、已知的系统异常（system）、对象校验（validateObject）、Spring
 * Web 参数校验（BindingResult）。
 *
 * @author larry.qi
 * @since 1.2.10
 */
@UtilityClass
public class AssertUtil {

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @return BusinessException 业务异常
   */
  public static BusinessException business(ErrorCode errorCode) {
    return new BusinessException(errorCode);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @return BusinessException 业务异常
   */
  public static BusinessException business(ErrorCode errorCode, String detail) {
    return new BusinessException(errorCode, detail);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @param cause     异常信息
   * @return BusinessException 业务异常
   */
  public static BusinessException business(ErrorCode errorCode, Throwable cause) {
    return new BusinessException(errorCode, cause);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @param cause     异常信息
   * @return BusinessException 业务异常
   */
  public static BusinessException business(ErrorCode errorCode, String detail, Throwable cause) {
    return new BusinessException(errorCode, detail, cause);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @return SystemException 已知的系统异常
   */
  public static SystemException system(ErrorCode errorCode) {
    return new SystemException(errorCode);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @return SystemException 已知的系统异常
   */
  public static SystemException system(ErrorCode errorCode, String detail) {
    return new SystemException(errorCode, detail);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param cause     异常信息
   * @return SystemException 已知的系统异常
   */
  public static SystemException system(ErrorCode errorCode, Throwable cause) {
    return new SystemException(errorCode, cause);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @param cause     异常信息
   * @return SystemException 已知的系统异常
   */
  public static SystemException system(ErrorCode errorCode, String detail, Throwable cause) {
    return new SystemException(errorCode, detail, cause);
  }

  /**
   * 请求参数校验异常
   *
   * @return ValidationException 请求参数校验异常
   */
  public static ValidationException validate() {
    return new ValidationException();
  }

  /**
   * 请求参数校验异常
   *
   * @param detail 错误详情
   * @return ValidationException 请求参数校验异常
   */
  public static ValidationException validate(String detail) {
    return new ValidationException(detail);
  }

  /**
   * 请求参数校验异常
   *
   * @param cause 异常信息
   * @return ValidationException 请求参数校验异常
   */
  public static ValidationException validate(Throwable cause) {
    return new ValidationException(cause);
  }

  /**
   * 请求参数校验异常
   *
   * @param detail 错误详情
   * @param cause  异常信息
   * @return ValidationException 请求参数校验异常
   */
  public static ValidationException validate(String detail, Throwable cause) {
    return new ValidationException(detail, cause);
  }

  /**
   * 基于注解的对象校验，抛出业务异常
   *
   * @param errorCode 错误码
   * @param object    校验对象
   * @param groups    分组校验
   * @throws BusinessException 业务异常
   */
  public static void validateObject(ErrorCode errorCode, Object object, Class<?>... groups) {
    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
      .failFast(true).buildValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<Object>> sets = validator.validate(object, groups);
    for (ConstraintViolation<Object> o : sets) {
      AssertUtil.business(errorCode, o.getMessage()).doThrow();
    }
  }

  /**
   * 基于注解的对象校验，抛出业务异常
   *
   * @param errorCode 错误码
   * @param object    校验对象
   * @throws BusinessException 业务异常
   */
  public static void validateObject(ErrorCode errorCode, Object object) {
    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
      .failFast(true).buildValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<Object>> sets = validator.validate(object);
    for (ConstraintViolation<Object> o : sets) {
      AssertUtil.business(errorCode, o.getMessage()).doThrow();
    }
  }

  /**
   * 基于注解的对象校验，抛出请求参数异常
   *
   * @param object 校验对象
   * @param groups 分组校验
   * @throws ValidationException 请求参数校验异常
   */
  public static void validateObject(Object object, Class<?>... groups) {
    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
      .failFast(true).buildValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<Object>> sets = validator.validate(object, groups);
    for (ConstraintViolation<Object> o : sets) {
      AssertUtil.validate(o.getMessage()).doThrow();
    }
  }

  /**
   * 基于注解的对象校验，抛出请求参数异常
   *
   * @param object 校验对象
   * @throws ValidationException 请求参数校验异常
   */
  public static void validateObject(Object object) {
    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
      .failFast(true).buildValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<Object>> sets = validator.validate(object);
    for (ConstraintViolation<Object> o : sets) {
      AssertUtil.validate(o.getMessage()).doThrow();
    }
  }

  /**
   * 前端请求参数校验，抛出业务处理异常
   *
   * @param errorCode     错误码
   * @param bindingResult 前端请求参数校验
   * @throws ValidationException 请求参数校验异常
   */
  public static void validateBindingResult(ErrorCode errorCode, BindingResult bindingResult) {
    AssertUtil.business(errorCode, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()).doThrow(bindingResult.hasErrors());
  }

  /**
   * 前端请求参数校验，抛出请求参数异常
   *
   * @param bindingResult 前端请求参数校验
   * @throws ValidationException 请求参数校验异常
   */
  public static void validateBindingResult(BindingResult bindingResult) {
    AssertUtil.validate(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()).doThrow(bindingResult.hasErrors());
  }

}
