package org.muyie.framework.exception;

import org.muyie.framework.context.ResponseCode;
import org.muyie.framework.context.ResponseCodeDefaults;
import org.springframework.http.HttpStatus;

public class ExceptionUtil {

  public static MuyieException error() {
    return new MuyieException(ResponseCodeDefaults.SC_9999);
  }

  public static MuyieException error(HttpStatus status) {
    return new MuyieException(ResponseCodeDefaults.SC_9999, status);
  }

  public static MuyieException error(HttpStatus status, String message) {
    return new MuyieException(ResponseCodeDefaults.SC_9999, status, message);
  }

  public static MuyieException error(HttpStatus status, String message, Throwable cause) {
    return new MuyieException(ResponseCodeDefaults.SC_9999, status, message, cause);
  }

  public static MuyieException error(HttpStatus status, Throwable cause) {
    return new MuyieException(ResponseCodeDefaults.SC_9999, status, cause);
  }

  public static MuyieException error(ResponseCode responseCode) {
    return new MuyieException(responseCode);
  }

  public static MuyieException error(ResponseCode responseCode, HttpStatus status) {
    return new MuyieException(responseCode, status);
  }

  public static MuyieException error(ResponseCode responseCode, HttpStatus status, String message) {
    return new MuyieException(responseCode, status, message);
  }

  public static MuyieException error(ResponseCode responseCode, HttpStatus status, String message,
      Throwable cause) {
    return new MuyieException(responseCode, status, message, cause);
  }

  public static MuyieException error(ResponseCode responseCode, HttpStatus status,
      Throwable cause) {
    return new MuyieException(responseCode, status, cause);
  }

  public static MuyieException error(ResponseCode responseCode, String message) {
    return new MuyieException(responseCode, message);
  }

  public static MuyieException error(ResponseCode responseCode, String message, Throwable cause) {
    return new MuyieException(responseCode, message, cause);
  }

  public static MuyieException error(ResponseCode responseCode, Throwable cause) {
    return new MuyieException(responseCode, cause);
  }

  public static MuyieException error(String message) {
    return new MuyieException(ResponseCodeDefaults.SC_9999, message);
  }

  public static MuyieException error(String message, Throwable cause) {
    return new MuyieException(ResponseCodeDefaults.SC_9999, message, cause);
  }

  public static MuyieException error(Throwable cause) {
    return new MuyieException(ResponseCodeDefaults.SC_9999, cause);
  }

}
