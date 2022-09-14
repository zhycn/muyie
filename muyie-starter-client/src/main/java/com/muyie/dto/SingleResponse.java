package com.muyie.dto;

import com.muyie.exception.ErrorCode;
import com.muyie.exception.ErrorCodeDefaults;

import org.springframework.http.HttpHeaders;

import java.util.Map;

/**
 * API Response with single record to return.
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class SingleResponse<T> extends Response {

  private static final long serialVersionUID = 1L;

  /**
   * 返回结果对象（泛型对象）
   */
  private final T data;

  /**
   * 构造函数
   *
   * @param errorCode 错误码
   * @param data      泛型对象
   */
  public SingleResponse(ErrorCode errorCode, T data) {
    super(errorCode);
    this.data = data;
  }

  /**
   * 返回一个失败的结果
   *
   * @param errorCode 错误码
   * @return 结果
   */
  public static SingleResponse<?> of(ErrorCode errorCode) {
    return of(errorCode, null);
  }

  /**
   * 返回一个失败的结果
   *
   * @param errorCode 错误码
   * @param data      泛型对象
   * @param <T>       对象类型
   * @return 结果
   */
  public static <T> SingleResponse<T> of(ErrorCode errorCode, T data) {
    return new SingleResponse<>(errorCode, data);
  }

  /**
   * 返回一个成功的结果
   *
   * @param data 泛型对象
   * @param <T>  对象类型
   * @return 结果
   */
  public static <T> SingleResponse<T> of(T data) {
    return of(ErrorCodeDefaults.SUCCESS, data);
  }

  /**
   * 返回一个成功的结果
   *
   * @return 结果
   */
  public static SingleResponse<?> of() {
    return of(ErrorCodeDefaults.SUCCESS, null);
  }

  /**
   * 重写错误信息 - 支持已定义的错误代码，重写错误信息
   *
   * @param message 错误信息
   * @return 结果
   */
  @Override
  public SingleResponse<T> rewrite(String message) {
    super.rewrite(message);
    return this;
  }

  /**
   * 重写错误信息 - 支持已定义的错误代码，重写错误信息
   *
   * @param message 错误信息 - 格式化文本, {} 表示占位符
   * @param params  可变数组
   * @return 结果
   */
  @Override
  public SingleResponse<T> rewrite(String message, Object... params) {
    super.rewrite(message, params);
    return this;
  }

  /**
   * 重写错误信息 - 支持已定义的错误代码，重写错误信息
   *
   * @param message 错误信息 - 格式化文本, {key} 表示占位符
   * @param map     对象集合
   * @return 结果
   */
  @Override
  public SingleResponse<T> rewrite(String message, Map<?, ?> map) {
    super.rewrite(message, map);
    return this;
  }

  /**
   * 设置响应头信息（可选）
   *
   * @param headers 响应头信息
   * @return 结果
   */
  @Override
  public SingleResponse<T> setHeaders(HttpHeaders headers) {
    super.setHeaders(headers);
    return this;
  }

  public T getData() {
    return data;
  }

}
