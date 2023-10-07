package com.muyie.exception;

import static com.muyie.exception.ErrorCodeDefaults.S0400;

/**
 * 请求参数校验异常，有明确的错误语义，不需要记录错误（Error）日志，不需要重试（Retry）。
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class ValidationException extends BusinessException {

  /**
   * 请求参数校验异常
   */
  protected ValidationException() {
    super(S0400);
  }

  /**
   * 请求参数校验异常
   *
   * @param detail 错误详情
   */
  protected ValidationException(String detail) {
    super(S0400, detail);
  }

  /**
   * 请求参数校验异常
   *
   * @param cause 异常
   */
  protected ValidationException(Throwable cause) {
    super(S0400, cause);
  }

  /**
   * 请求参数校验异常
   *
   * @param detail 错误详情
   * @param cause  异常
   */
  protected ValidationException(String detail, Throwable cause) {
    super(S0400, detail, cause);
  }

  /**
   * 请求参数校验异常
   *
   * @return ValidationException
   */
  public static ValidationException of() {
    return new ValidationException();
  }

  /**
   * 请求参数校验异常
   *
   * @param detail 错误详情
   * @return ValidationException
   */
  public static ValidationException of(String detail) {
    return new ValidationException(detail);
  }

  /**
   * 请求参数校验异常
   *
   * @param cause 异常
   * @return ValidationException
   */
  public static ValidationException of(Throwable cause) {
    return new ValidationException(cause);
  }

  /**
   * 请求参数校验异常
   *
   * @param detail 错误详情
   * @param cause  异常
   * @return ValidationException
   */
  public static ValidationException of(String detail, Throwable cause) {
    return new ValidationException(detail, cause);
  }
}
