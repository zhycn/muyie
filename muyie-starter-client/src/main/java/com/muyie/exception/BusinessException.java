package com.muyie.exception;

import org.springframework.lang.NonNull;

/**
 * 业务处理异常，有明确的业务语义，不需要记录错误（Error） 日志，不需要重试（Retry）。
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class BusinessException extends BaseException {
  private static final long serialVersionUID = 1L;

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   */
  protected BusinessException(@NonNull ErrorCode errorCode) {
    super(errorCode);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   */
  protected BusinessException(@NonNull ErrorCode errorCode, String detail) {
    super(errorCode, detail);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @param cause     异常
   */
  protected BusinessException(@NonNull ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @param cause     异常
   */
  protected BusinessException(@NonNull ErrorCode errorCode, String detail, Throwable cause) {
    super(errorCode, detail, cause);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   */
  public static BusinessException of(@NonNull ErrorCode errorCode) {
    return new BusinessException(errorCode);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   */
  public static BusinessException of(@NonNull ErrorCode errorCode, String detail) {
    return new BusinessException(errorCode, detail);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @param cause     异常
   */
  public static BusinessException of(@NonNull ErrorCode errorCode, Throwable cause) {
    return new BusinessException(errorCode, cause);
  }

  /**
   * 业务异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @param cause     异常
   */
  public static BusinessException of(@NonNull ErrorCode errorCode, String detail, Throwable cause) {
    return new BusinessException(errorCode, detail, cause);
  }

}
