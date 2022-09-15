package com.muyie.exception;

import org.springframework.lang.NonNull;

/**
 * 已知的系统异常，需要记录错误（Error）日志，可以重试（Retry）。
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class SystemException extends BaseException {
  private static final long serialVersionUID = 1L;

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   */
  protected SystemException(@NonNull ErrorCode errorCode) {
    super(errorCode);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   */
  protected SystemException(@NonNull ErrorCode errorCode, String detail) {
    super(errorCode, detail);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param cause     异常
   */
  protected SystemException(@NonNull ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @param cause     异常
   */
  protected SystemException(@NonNull ErrorCode errorCode, String detail, Throwable cause) {
    super(errorCode, detail, cause);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   */
  public static SystemException of(@NonNull ErrorCode errorCode) {
    return new SystemException(errorCode);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   */
  public static SystemException of(@NonNull ErrorCode errorCode, String detail) {
    return new SystemException(errorCode, detail);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param cause     异常
   */
  public static SystemException of(@NonNull ErrorCode errorCode, Throwable cause) {
    return new SystemException(errorCode, cause);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @param cause     异常
   */
  public static SystemException of(@NonNull ErrorCode errorCode, String detail, Throwable cause) {
    return new SystemException(errorCode, detail, cause);
  }

}
