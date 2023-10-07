package com.muyie.exception;

import cn.hutool.core.collection.CollectionUtil;
import lombok.experimental.UtilityClass;
import org.hibernate.validator.HibernateValidator;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 异常工具类（ExceptionUtil）的提供了一些静态方法，支持参数校验异常（validate）、业务处理异常（business）、已知的系统异常（system）、对象校验（validateObject）、Spring
 * Web 参数校验（BindingResult）。
 *
 * @author larry.qi
 * @since 1.2.10
 */
@UtilityClass
public class ExceptionUtil {

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @return BusinessException 业务异常
   */
  public static BusinessException business(@NonNull ErrorCode errorCode) {
    return BusinessException.of(errorCode);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @return BusinessException 业务异常
   */
  public static BusinessException business(@NonNull ErrorCode errorCode, String detail) {
    return BusinessException.of(errorCode, detail);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @param cause     异常信息
   * @return BusinessException 业务异常
   */
  public static BusinessException business(@NonNull ErrorCode errorCode, Throwable cause) {
    return BusinessException.of(errorCode, cause);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @param cause     异常信息
   * @return BusinessException 业务异常
   */
  public static BusinessException business(@NonNull ErrorCode errorCode, String detail, Throwable cause) {
    return BusinessException.of(errorCode, detail, cause);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @return SystemException 已知的系统异常
   */
  public static SystemException system(@NonNull ErrorCode errorCode) {
    return SystemException.of(errorCode);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @return SystemException 已知的系统异常
   */
  public static SystemException system(@NonNull ErrorCode errorCode, String detail) {
    return SystemException.of(errorCode, detail);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param cause     异常信息
   * @return SystemException 已知的系统异常
   */
  public static SystemException system(@NonNull ErrorCode errorCode, Throwable cause) {
    return SystemException.of(errorCode, cause);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @param cause     异常信息
   * @return SystemException 已知的系统异常
   */
  public static SystemException system(@NonNull ErrorCode errorCode, String detail, Throwable cause) {
    return SystemException.of(errorCode, detail, cause);
  }

  /**
   * 请求参数校验异常
   *
   * @return ValidationException 请求参数校验异常
   */
  public static ValidationException validate() {
    return ValidationException.of();
  }

  /**
   * 请求参数校验异常
   *
   * @param detail 错误详情
   * @return ValidationException 请求参数校验异常
   */
  public static ValidationException validate(String detail) {
    return ValidationException.of(detail);
  }

  /**
   * 请求参数校验异常
   *
   * @param cause 异常信息
   * @return ValidationException 请求参数校验异常
   */
  public static ValidationException validate(Throwable cause) {
    return ValidationException.of(cause);
  }

  /**
   * 请求参数校验异常
   *
   * @param detail 错误详情
   * @param cause  异常信息
   * @return ValidationException 请求参数校验异常
   */
  public static ValidationException validate(String detail, Throwable cause) {
    return ValidationException.of(detail, cause);
  }

  /**
   * 基于注解的对象校验，抛出业务异常
   *
   * @param errorCode 错误码
   * @param object    校验对象
   * @param groups    分组校验
   * @throws BusinessException 业务异常
   */
  public static void validateObject(@NonNull ErrorCode errorCode, Object object, Class<?>... groups) {
    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<Object>> sets = validator.validate(object, groups);
    ExceptionUtil.business(errorCode, toString(sets)).doThrow(CollectionUtil.isNotEmpty(sets));
  }

  /**
   * 基于注解的对象校验，抛出业务异常
   *
   * @param errorCode 错误码
   * @param object    校验对象
   * @throws BusinessException 业务异常
   */
  public static void validateObject(@NonNull ErrorCode errorCode, Object object) {
    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<Object>> sets = validator.validate(object);
    ExceptionUtil.business(errorCode, toString(sets)).doThrow(CollectionUtil.isNotEmpty(sets));
  }

  /**
   * 基于注解的对象校验，抛出请求参数异常
   *
   * @param object 校验对象
   * @param groups 分组校验
   * @throws ValidationException 请求参数校验异常
   */
  public static void validateObject(Object object, Class<?>... groups) {
    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<Object>> sets = validator.validate(object, groups);
    ExceptionUtil.validate(toString(sets)).doThrow(CollectionUtil.isNotEmpty(sets));
  }

  /**
   * 基于注解的对象校验，抛出请求参数异常
   *
   * @param object 校验对象
   * @throws ValidationException 请求参数校验异常
   */
  public static void validateObject(Object object) {
    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<Object>> sets = validator.validate(object);
    ExceptionUtil.validate(toString(sets)).doThrow(CollectionUtil.isNotEmpty(sets));
  }

  /**
   * 前端请求参数校验，抛出业务处理异常
   *
   * @param errorCode     错误码
   * @param bindingResult 前端请求参数校验
   * @throws ValidationException 请求参数校验异常
   */
  public static void validateObject(@NonNull ErrorCode errorCode, @NonNull BindingResult bindingResult) {
    ExceptionUtil.business(errorCode, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()).doThrow(bindingResult.hasErrors());
  }

  /**
   * 前端请求参数校验，抛出请求参数异常
   *
   * @param bindingResult 前端请求参数校验
   * @throws ValidationException 请求参数校验异常
   */
  public static void validateObject(@NonNull BindingResult bindingResult) {
    ExceptionUtil.validate(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()).doThrow(bindingResult.hasErrors());
  }

  private static String toString(Set<? extends ConstraintViolation<?>> constraintViolations) {
    return constraintViolations.stream()
      .map(cv -> Objects.isNull(cv) ? "null" : cv.getPropertyPath() + ": " + cv.getMessage())
      .collect(Collectors.joining(", "));
  }

}
