package com.muyie.exception;

/**
 * 请求参数校验异常，有明确的错误语义，不需要记录错误（Error）日志，不需要重试（Retry）。
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class ValidationException extends BusinessException {
  private static final long serialVersionUID = 1L;

  /**
   * 请求参数校验异常
   */
  public ValidationException() {
    super(ErrorCodeDefaults.S0400);
  }

  /**
   * 请求参数校验异常
   *
   * @param detail 错误详情
   */
  public ValidationException(String detail) {
    super(ErrorCodeDefaults.S0400, detail);
  }

  /**
   * 请求参数校验异常
   *
   * @param cause 异常
   */
  public ValidationException(Throwable cause) {
    super(ErrorCodeDefaults.S0400, cause);
  }

  /**
   * 请求参数校验异常
   *
   * @param detail 错误详情
   * @param cause  异常
   */
  public ValidationException(String detail, Throwable cause) {
    super(ErrorCodeDefaults.S0400, detail, cause);
  }

}
