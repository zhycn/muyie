package com.muyie.dto;

import com.google.common.collect.Maps;

import com.muyie.exception.ErrorCode;
import com.muyie.exception.ErrorCodeDefaults;

import org.springframework.http.HttpHeaders;

import java.util.Map;
import java.util.Objects;

/**
 * Returns the Response
 */
public class ApiResponse extends Response {

  private static final long serialVersionUID = 1L;


  /**
   * 总记录数
   */
  private long total;

  /**
   * 返回结果对象
   */
  private final Object data;

  /**
   * 构造函数
   *
   * @param errorCode 错误码
   * @param data      对象
   */
  public ApiResponse(ErrorCode errorCode, Object data) {
    super(errorCode);
    this.data = data;
  }

  /**
   * 返回一个失败的结果
   *
   * @param errorCode 错误码
   * @return 结果
   */
  public static ApiResponse of(ErrorCode errorCode) {
    return of(errorCode, null);
  }

  /**
   * 返回一个失败的结果
   *
   * @param errorCode 错误码
   * @param data      结果对象
   * @return 结果
   */
  public static ApiResponse of(ErrorCode errorCode, Object data) {
    return new ApiResponse(errorCode, data);
  }

  /**
   * 返回一个成功的结果
   *
   * @param data 结果对象
   * @return 结果
   */
  public static ApiResponse of(Object data) {
    return of(ErrorCodeDefaults.SUCCESS, data);
  }

  /**
   * 返回一个成功的结果
   *
   * @param data  结果对象
   * @param total 总记录数
   * @return 结果
   */
  public static ApiResponse of(Object data, long total) {
    return of(ErrorCodeDefaults.SUCCESS, data).setTotal(total);
  }

  /**
   * 返回一个成功的结果
   *
   * @return 结果
   */
  public static ApiResponse of() {
    return of(ErrorCodeDefaults.SUCCESS, null);
  }

  /**
   * 重写错误信息 - 支持已定义的错误代码，重写错误信息
   *
   * @param message 错误信息
   * @return 结果
   */
  @Override
  public ApiResponse rewrite(String message) {
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
  public ApiResponse rewrite(String message, Object... params) {
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
  public ApiResponse rewrite(String message, Map<?, ?> map) {
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
  public ApiResponse setHeaders(HttpHeaders headers) {
    super.setHeaders(headers);
    return this;
  }

  public long getTotal() {
    return (total >= 0) ? total : 0L;
  }

  /**
   * 设置总记录数（可选）
   *
   * @param total 总记录数
   * @return 结果
   */
  public ApiResponse setTotal(long total) {
    this.total = total;
    return this;
  }

  public Object getData() {
    return Objects.isNull(data) ? Maps.newConcurrentMap() : data;
  }

}
