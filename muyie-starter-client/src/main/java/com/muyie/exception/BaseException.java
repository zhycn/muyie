package com.muyie.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import lombok.Getter;

/**
 * 异常基类（抽象类），自定义异常继承该类，便于全局异常处理。
 *
 * @author larry.qi
 * @since 1.2.10
 */
@Getter
public abstract class BaseException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  /**
   * 异常的错误详情（可选）
   */
  private final String detail;
  /**
   * 异常的错误码（必须）
   */
  private ErrorCode errorCode;
  /**
   * 异常的响应状态码（默认值：200）
   */
  private int httpStatus = 200;

  /**
   * 异常基类
   *
   * @param errorCode 错误码
   */
  public BaseException(ErrorCode errorCode) {
    this(errorCode, null, null);
  }

  /**
   * 异常基类
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   */
  public BaseException(ErrorCode errorCode, String detail) {
    this(errorCode, detail, null);
  }

  /**
   * 异常基类
   *
   * @param errorCode 错误码
   * @param cause     异常信息
   */
  public BaseException(ErrorCode errorCode, Throwable cause) {
    this(errorCode, null, cause);
  }

  /**
   * 异常基类
   *
   * @param errorCode 错误码
   * @param detail    错误详情
   * @param cause     异常信息
   */
  public BaseException(ErrorCode errorCode, String detail, Throwable cause) {
    super(detail, cause);
    this.errorCode = errorCode;
    this.detail = detail;
  }

  /**
   * 获取异常的的错误详情
   *
   * @return 错误详情
   */
  @Override
  public String getMessage() {
    StringBuffer sb = new StringBuffer();
    sb.append(errorCode.getMessage()).append("(").append(errorCode.getCode()).append(")");
    Optional.ofNullable(super.getMessage()).ifPresent(s -> sb.append(" - ").append(s));
    return sb.toString();
  }

  /**
   * 重写异常错误码的错误信息
   *
   * @param message 错误信息
   * @return BaseException 异常基类
   */
  public BaseException rewrite(String message) {
    this.errorCode = ErrorCodeBuilder.of(errorCode, message);
    return this;
  }

  /**
   * 重写异常错误码的错误信息
   *
   * @param message 错误信息 - 格式化文本, {} 表示占位符
   * @param params  可变数组
   * @return BaseException 异常基类
   */
  public BaseException rewrite(String message, Object... params) {
    this.errorCode = ErrorCodeBuilder.of(errorCode, message, params);
    return this;
  }

  /**
   * 重写异常错误码的错误信息
   *
   * @param message 错误信息 - 格式化文本, {key} 表示占位符
   * @param map     对象集合
   * @return BaseException 异常基类
   */
  public BaseException rewrite(String message, Map<?, ?> map) {
    this.errorCode = ErrorCodeBuilder.of(errorCode, message, map);
    return this;
  }

  /**
   * 设置异常的响应状态码（默认值：200）
   *
   * @param httpStatus 响应状态码
   * @return BaseException 异常基类
   */
  public BaseException setHttpStatus(int httpStatus) {
    if (httpStatus > 0) {
      this.httpStatus = httpStatus;
    }
    return this;
  }

  /**
   * 设置异常的响应状态码（默认值：200）
   *
   * @param httpStatus 响应状态码
   * @return BaseException 异常基类
   */
  public BaseException setHttpStatus(HttpStatus httpStatus) {
    Optional.ofNullable(httpStatus).ifPresent(s -> setHttpStatus(s.value()));
    return this;
  }

  /**
   * 抛出异常
   */
  public void doThrow() {
    throw this;
  }

  /**
   * 断言一个表达式，true 则抛出异常
   *
   * @param expression 表达式
   */
  public void doThrow(boolean expression) {
    if (expression) {
      throw this;
    }
  }

  /**
   * 断言一个函数返回结果，true 则抛出异常
   *
   * @param booleanSupplier 函数返回结果
   */
  public void doThrow(BooleanSupplier booleanSupplier) {
    doThrow(booleanSupplier.getAsBoolean());
  }

}
