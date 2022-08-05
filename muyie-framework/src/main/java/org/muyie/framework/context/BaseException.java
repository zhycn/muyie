package org.muyie.framework.context;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import cn.hutool.core.util.StrUtil;

/**
 * Runtime exception provider for MuYie
 */
public class BaseException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final ICode code;

  private final HttpStatus status;

  public BaseException(final ICode code) {
    this(code, HttpStatus.OK);
  }

  public BaseException(final ICode code, final HttpStatus status) {
    this.code = code;
    this.status = status;
  }

  public BaseException(final ICode code, final HttpStatus status, final String message) {
    super(message);
    this.code = code;
    this.status = status;
  }

  public BaseException(final ICode code, final HttpStatus status, final String message,
                       final Throwable cause) {
    super(message, cause);
    this.code = code;
    this.status = status;
  }

  public BaseException(final ICode code, final HttpStatus status, final Throwable cause) {
    super(cause);
    this.code = code;
    this.status = status;
  }

  public BaseException(final ICode code, final String message) {
    this(code, HttpStatus.OK, message);
  }

  public BaseException(final ICode code, final String message, final Throwable cause) {
    this(code, HttpStatus.OK, message, cause);
  }

  public BaseException(final ICode code, final Throwable cause) {
    this(code, HttpStatus.OK, cause);
  }

  @Override
  public String getMessage() {
    StringBuffer sb = new StringBuffer();
    sb.append(StrUtil.format("{}({})", code.getMsg(), code.getCode()));
    if (StringUtils.isNotBlank(super.getMessage())) {
      sb.append(" - " + super.getMessage());
    }
    return sb.toString();
  }

  public ICode getCode() {
    return code;
  }

  public HttpStatus getStatus() {
    return status;
  }

}
