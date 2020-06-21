package org.muyie.framework.exception;

import org.muyie.framework.context.ResponseCode;
import org.springframework.http.HttpStatus;

import cn.hutool.core.util.StrUtil;

/**
 * Runtime exception provider for MuYie
 */
public class MuyieException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final ResponseCode responseCode;

  private final HttpStatus status;

  public MuyieException(final ResponseCode responseCode) {
    this(responseCode, HttpStatus.OK);
  }

  public MuyieException(final ResponseCode responseCode, final HttpStatus status) {
    this.responseCode = responseCode;
    this.status = status;
  }

  public MuyieException(final ResponseCode responseCode, final HttpStatus status, final String message) {
    super(message);
    this.responseCode = responseCode;
    this.status = status;
  }

  public MuyieException(final ResponseCode responseCode, final HttpStatus status, final String message,
      final Throwable cause) {
    super(message, cause);
    this.responseCode = responseCode;
    this.status = status;
  }

  public MuyieException(final ResponseCode responseCode, final HttpStatus status, final Throwable cause) {
    super(cause);
    this.responseCode = responseCode;
    this.status = status;
  }

  public MuyieException(final ResponseCode responseCode, final String message) {
    this(responseCode, HttpStatus.OK, message);
  }

  public MuyieException(final ResponseCode responseCode, final String message, final Throwable cause) {
    this(responseCode, HttpStatus.OK, message, cause);
  }

  public MuyieException(final ResponseCode responseCode, final Throwable cause) {
    this(responseCode, HttpStatus.OK, cause);
  }

  @Override
  public String getMessage() {
    return StrUtil.format("ResponseCode '{}({})': throwing message = '{}'", responseCode.getMsg(),
        responseCode.getCode(), super.getMessage());
  }

  public ResponseCode getResponseCode() {
    return responseCode;
  }

  public HttpStatus getStatus() {
    return status;
  }

}
