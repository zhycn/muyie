package org.muyie.framework.context;

import org.springframework.http.HttpStatus;

/**
 * 异常工具类
 */
public class Exceptions {

  public static BaseException of() {
    return new BaseException(CodeDefaults.SC_99999);
  }

  public static BaseException of(HttpStatus status) {
    return new BaseException(CodeDefaults.SC_99999, status);
  }

  public static BaseException of(HttpStatus status, String message) {
    return new BaseException(CodeDefaults.SC_99999, status, message);
  }

  public static BaseException of(HttpStatus status, String message, Throwable cause) {
    return new BaseException(CodeDefaults.SC_99999, status, message, cause);
  }

  public static BaseException of(HttpStatus status, Throwable cause) {
    return new BaseException(CodeDefaults.SC_99999, status, cause);
  }

  public static BaseException of(ICode code) {
    return new BaseException(code);
  }

  public static BaseException of(ICode code, HttpStatus status) {
    return new BaseException(code, status);
  }

  public static BaseException of(ICode code, HttpStatus status, String message) {
    return new BaseException(code, status, message);
  }

  public static BaseException of(ICode code, HttpStatus status, String message,
                                 Throwable cause) {
    return new BaseException(code, status, message, cause);
  }

  public static BaseException of(ICode code, HttpStatus status,
                                 Throwable cause) {
    return new BaseException(code, status, cause);
  }

  public static BaseException of(ICode code, String message) {
    return new BaseException(code, message);
  }

  public static BaseException of(ICode code, String message, Throwable cause) {
    return new BaseException(code, message, cause);
  }

  public static BaseException of(ICode code, Throwable cause) {
    return new BaseException(code, cause);
  }

  public static BaseException of(String message) {
    return new BaseException(CodeDefaults.SC_99999, message);
  }

  public static BaseException of(String message, Throwable cause) {
    return new BaseException(CodeDefaults.SC_99999, message, cause);
  }

  public static BaseException of(Throwable cause) {
    return new BaseException(CodeDefaults.SC_99999, cause);
  }

  public static void throwEx() {
    throw new BaseException(CodeDefaults.SC_99999);
  }

  public static void throwEx(HttpStatus status) {
    throw new BaseException(CodeDefaults.SC_99999, status);
  }

  public static void throwEx(HttpStatus status, String message) {
    throw new BaseException(CodeDefaults.SC_99999, status, message);
  }

  public static void throwEx(HttpStatus status, String message, Throwable cause) {
    throw new BaseException(CodeDefaults.SC_99999, status, message, cause);
  }

  public static void throwEx(HttpStatus status, Throwable cause) {
    throw new BaseException(CodeDefaults.SC_99999, status, cause);
  }

  public static void throwEx(ICode code) {
    throw new BaseException(code);
  }

  public static void throwEx(ICode code, HttpStatus status) {
    throw new BaseException(code, status);
  }

  public static void throwEx(ICode code, HttpStatus status, String message) {
    throw new BaseException(code, status, message);
  }

  public static void throwEx(ICode code, HttpStatus status, String message,
                             Throwable cause) {
    throw new BaseException(code, status, message, cause);
  }

  public static void throwEx(ICode code, HttpStatus status,
                             Throwable cause) {
    throw new BaseException(code, status, cause);
  }

  public static void throwEx(ICode code, String message) {
    throw new BaseException(code, message);
  }

  public static void throwEx(ICode code, String message, Throwable cause) {
    throw new BaseException(code, message, cause);
  }

  public static void throwEx(ICode code, Throwable cause) {
    throw new BaseException(code, cause);
  }

  public static void throwEx(String message) {
    throw new BaseException(CodeDefaults.SC_99999, message);
  }

  public static void throwEx(String message, Throwable cause) {
    throw new BaseException(CodeDefaults.SC_99999, message, cause);
  }

  public static void throwEx(Throwable cause) {
    throw new BaseException(CodeDefaults.SC_99999, cause);
  }

}
