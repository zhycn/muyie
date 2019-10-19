package org.muyie.framework.exception;

import org.muyie.framework.http.ResponseCode;
import org.springframework.http.HttpStatus;

import cn.hutool.core.util.StrUtil;

/**
 * Runtime exception provider for MuYie
 */
public class MuyieException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final ResponseCode responseCode;
  private final HttpStatus status;

  public MuyieException(ResponseCode responseCode) {
    this(responseCode, HttpStatus.OK);
  }

  public MuyieException(ResponseCode responseCode, String message) {
    this(responseCode, HttpStatus.OK, message);
  }

  public MuyieException(ResponseCode responseCode, String message, Throwable cause) {
    this(responseCode, HttpStatus.OK, message, cause);
  }

  public MuyieException(ResponseCode responseCode, Throwable cause) {
    this(responseCode, HttpStatus.OK, cause);
  }

  public MuyieException(ResponseCode responseCode, HttpStatus status) {
    this.responseCode = responseCode;
    this.status = status;
  }

  public MuyieException(ResponseCode responseCode, HttpStatus status, String message) {
    super(message);
    this.responseCode = responseCode;
    this.status = status;
  }

  public MuyieException(ResponseCode responseCode, HttpStatus status, String message,
      Throwable cause) {
    super(message, cause);
    this.responseCode = responseCode;
    this.status = status;
  }

  public MuyieException(ResponseCode responseCode, HttpStatus status, Throwable cause) {
    super(cause);
    this.responseCode = responseCode;
    this.status = status;
  }

  public ResponseCode getResponseCode() {
    return responseCode;
  }

  public HttpStatus getStatus() {
    return status;
  }

  @Override
  public String getMessage() {
    return StrUtil.format("ResponseCode '{}({})': throwing message = '{}'", responseCode.getMsg(),
        responseCode.getCode(), super.getMessage());
  }

}
