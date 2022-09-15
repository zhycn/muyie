package com.muyie.exception;

import org.springframework.lang.NonNull;

import java.util.Map;

import cn.hutool.core.util.StrUtil;

/**
 * 自定义错误码构建类，增强错误码构建和错误信息重写。
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class ErrorCodeBuilder implements ErrorCode {

  /**
   * 错误码
   */
  private final String code;

  /**
   * 错误信息
   */
  private final String message;

  private ErrorCodeBuilder(String code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * 构建错误码和错误信息，针对已定义并使用占位符的错误信息进行格式化填充, {key} 表示占位符
   *
   * @param errorCode 错误码
   * @param map       对象集合
   * @return 错误码
   */
  public static ErrorCode of(@NonNull ErrorCode errorCode, Map<?, ?> map) {
    return of(errorCode.getCode(), StrUtil.format(errorCode.getMessage(), map));
  }

  /**
   * 构建错误码和错误信息，针对已定义并使用占位符的错误信息进行格式化填充, {} 表示占位符
   *
   * @param errorCode 错误码
   * @param params    可变数组
   * @return 错误码
   */
  public static ErrorCode of(@NonNull ErrorCode errorCode, Object... params) {
    return of(errorCode.getCode(), StrUtil.format(errorCode.getMessage(), params));
  }

  /**
   * 构建错误码和错误信息，针对已定义的错误码，重写错误信息
   *
   * @param errorCode 错误码
   * @param message   错误信息
   * @return 错误码
   */
  public static ErrorCode of(@NonNull ErrorCode errorCode, String message) {
    return of(errorCode.getCode(), message);
  }

  /**
   * 构建错误码和错误信息，针对已定义的错误码，重写错误信息
   *
   * @param errorCode 错误码
   * @param message   错误信息 - 格式化文本, {key} 表示占位符
   * @param map       对象集合
   * @return 错误码
   */
  public static ErrorCode of(@NonNull ErrorCode errorCode, String message, Map<?, ?> map) {
    return of(errorCode.getCode(), StrUtil.format(message, map));
  }

  /**
   * 构建错误码和错误信息，针对已定义的错误码，重写错误信息
   *
   * @param errorCode 错误码
   * @param message   错误信息 - 格式化文本, {} 表示占位符
   * @param params    可变数组
   * @return 错误码
   */
  public static ErrorCode of(@NonNull ErrorCode errorCode, String message, Object... params) {
    return of(errorCode.getCode(), StrUtil.format(message, params));
  }

  /**
   * 构建错误码和错误信息
   *
   * @param code    错误码
   * @param message 错误信息
   * @return 错误码
   */
  public static ErrorCode of(String code, String message) {
    return new ErrorCodeBuilder(code, message);
  }

  /**
   * 构建错误码和错误信息，支持使用占位符的错误信息进行格式化填充, {key} 表示占位符
   *
   * @param code    错误码
   * @param message 错误信息 - 格式化文本, {key} 表示占位符
   * @param map     对象集合
   * @return 错误码
   */
  public static ErrorCode of(String code, String message, Map<?, ?> map) {
    return new ErrorCodeBuilder(code, StrUtil.format(message, map));
  }

  /**
   * 构建错误码和错误信息，支持使用占位符的错误信息进行格式化填充, {} 表示占位符
   *
   * @param code    错误码
   * @param message 错误信息 - 格式化文本, {} 表示占位符
   * @param params  可变数组
   * @return 错误码
   */
  public static ErrorCode of(String code, String message, Object... params) {
    return new ErrorCodeBuilder(code, StrUtil.format(message, params));
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
