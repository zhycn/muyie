package com.muyie.dto;

import com.google.common.collect.Maps;
import com.muyie.exception.ErrorCode;
import com.muyie.exception.ErrorCodeDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.Objects;

/**
 * Commons API Response to caller.
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class ApiResponse extends Response {

  /**
   * 返回结果数据
   */
  private final Object data;
  /**
   * 总记录数（分页时可用）
   */
  private long total;

  /**
   * 构造函数
   *
   * @param errorCode 错误码
   * @param data      对象
   */
  private ApiResponse(@NonNull ErrorCode errorCode, @Nullable Object data) {
    super(errorCode);
    this.data = data;
  }

  /**
   * 返回一个指定错误码的结果
   *
   * @param errorCode 错误码
   * @return 结果
   */
  public static ApiResponse of(@NonNull ErrorCode errorCode) {
    return of(errorCode, null);
  }

  /**
   * 返回一个指定错误码的结果
   *
   * @param errorCode 错误码
   * @param data      结果数据
   * @return 结果
   */
  public static ApiResponse of(@NonNull ErrorCode errorCode, @Nullable Object data) {
    return new ApiResponse(errorCode, data);
  }

  /**
   * 返回一个成功的结果
   *
   * @param data 结果数据
   * @return 结果
   */
  public static ApiResponse of(@Nullable Object data) {
    return of(ErrorCodeDefaults.SUCCESS, data);
  }

  /**
   * 返回一个成功的结果
   *
   * @param data  结果数据
   * @param total 总记录数
   * @return 结果
   */
  public static ApiResponse of(@Nullable Object data, long total) {
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
  public ApiResponse setHeaders(@Nullable HttpHeaders headers) {
    super.setHeaders(headers);
    return this;
  }

  /**
   * 获取总记录数（分页时可用）
   *
   * @return 总记录数
   */
  public long getTotal() {
    return Math.max(total, 0L);
  }

  /**
   * 设置总记录数（分页时可用）
   *
   * @param total 总记录数
   * @return 结果
   */
  public ApiResponse setTotal(long total) {
    this.total = total;
    return this;
  }

  /**
   * 获取结果数据
   *
   * @return 结果数据
   */
  public Object getData() {
    return Objects.isNull(data) ? Maps.newConcurrentMap() : data;
  }

}
