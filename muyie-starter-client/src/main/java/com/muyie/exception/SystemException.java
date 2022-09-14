package com.muyie.exception;

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
  public SystemException(ErrorCode errorCode) {
    super(errorCode);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   */
  public SystemException(ErrorCode errorCode, String detail) {
    super(errorCode, detail);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param cause     异常
   */
  public SystemException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  /**
   * 已知的系统异常
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @param cause     异常
   */
  public SystemException(ErrorCode errorCode, String detail, Throwable cause) {
    super(errorCode, detail, cause);
  }

}
