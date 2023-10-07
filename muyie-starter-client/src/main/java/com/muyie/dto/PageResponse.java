package com.muyie.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.muyie.exception.ErrorCode;
import com.muyie.exception.ErrorCodeDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

/**
 * API Response with batch page record to return, usually use in page query.
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class PageResponse<T> extends Response {

  /**
   * 返回结果数据
   */
  private final List<T> data;
  /**
   * 总记录数（分页时可用）
   */
  private long total;

  /**
   * 构造函数
   *
   * @param errorCode 错误码
   * @param data      结果数据
   */
  private PageResponse(@NonNull ErrorCode errorCode, @Nullable List<T> data) {
    super(errorCode);
    this.data = data;
  }

  /**
   * 返回一个指定错误码的结果
   *
   * @param errorCode 错误码
   * @return 结果
   */
  public static PageResponse<?> of(@NonNull ErrorCode errorCode) {
    return of(errorCode, Lists.newArrayList());
  }

  /**
   * 返回一个指定错误码的结果
   *
   * @param errorCode 错误码
   * @param data      结果数据
   * @param <T>       泛型对象
   * @return 结果
   */
  public static <T> PageResponse<T> of(@NonNull ErrorCode errorCode, @Nullable List<T> data) {
    return new PageResponse<>(errorCode, data);
  }

  /**
   * 返回一个成功的结果
   *
   * @param data 结果数据
   * @param <T>  泛型对象
   * @return 结果
   */
  public static <T> PageResponse<T> of(@Nullable List<T> data) {
    return of(ErrorCodeDefaults.SUCCESS, data);
  }

  /**
   * 返回一个成功的结果
   *
   * @param data  泛型集合
   * @param total 总记录数
   * @param <T>   泛型对象
   * @return 结果
   */
  public static <T> PageResponse<T> of(@Nullable List<T> data, long total) {
    return of(ErrorCodeDefaults.SUCCESS, data).setTotal(total);
  }

  /**
   * 返回一个成功的结果
   *
   * @return 结果
   */
  public static PageResponse<?> of() {
    return of(ErrorCodeDefaults.SUCCESS, Lists.newArrayList());
  }

  /**
   * 重写错误信息 - 支持已定义的错误代码，重写错误信息
   *
   * @param message 错误信息
   * @return 结果
   */
  @Override
  public PageResponse<T> rewrite(String message) {
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
  public PageResponse<T> rewrite(String message, Object... params) {
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
  public PageResponse<T> rewrite(String message, Map<?, ?> map) {
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
  public PageResponse<T> setHeaders(@Nullable HttpHeaders headers) {
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
  public PageResponse<T> setTotal(long total) {
    this.total = total;
    return this;
  }

  /**
   * 获取结果数据
   *
   * @return 结果数据
   */
  public List<T> getData() {
    return CollectionUtil.emptyIfNull(data);
  }

}
