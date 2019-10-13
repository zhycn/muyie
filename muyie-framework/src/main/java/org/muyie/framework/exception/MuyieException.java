package org.muyie.framework.exception;

import org.muyie.framework.http.ResultCode;
import org.springframework.http.HttpStatus;

import cn.hutool.core.util.StrUtil;

/**
 * Runtime exception provider for MuYie
 */
public class MuyieException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final ResultCode resultCode;
  private final HttpStatus status;

  public MuyieException(ResultCode resultCode) {
    this(resultCode, HttpStatus.OK);
  }

  public MuyieException(ResultCode resultCode, String message) {
    this(resultCode, HttpStatus.OK, message);
  }

  public MuyieException(ResultCode resultCode, String message, Throwable cause) {
    this(resultCode, HttpStatus.OK, message, cause);
  }

  public MuyieException(ResultCode resultCode, Throwable cause) {
    this(resultCode, HttpStatus.OK, cause);
  }

  public MuyieException(ResultCode resultCode, HttpStatus status) {
    this.resultCode = resultCode;
    this.status = status;
  }

  public MuyieException(ResultCode resultCode, HttpStatus status, String message) {
    super(message);
    this.resultCode = resultCode;
    this.status = status;
  }

  public MuyieException(ResultCode resultCode, HttpStatus status, String message, Throwable cause) {
    super(message, cause);
    this.resultCode = resultCode;
    this.status = status;
  }

  public MuyieException(ResultCode resultCode, HttpStatus status, Throwable cause) {
    super(cause);
    this.resultCode = resultCode;
    this.status = status;
  }

  public ResultCode getResultCode() {
    return resultCode;
  }

  public HttpStatus getStatus() {
    return status;
  }

  @Override
  public String getMessage() {
    return StrUtil.format("ResultCode '{}({})': throwing message = '{}'", resultCode.getMsg(),
        resultCode.getCode(), super.getMessage());
  }

}
