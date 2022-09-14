package com.muyie.dto;

import com.muyie.exception.ErrorCode;
import com.muyie.exception.ErrorCodeDefaults;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * API Response with batch page record to return, usually use in page query.
 *
 * @author larry.qi
 * @since 1.2.10
 */
public class PageResponse<T> extends Response {

  private static final long serialVersionUID = 1L;

  /**
   * 总记录数
   */
  private long total;

  /**
   * 返回结果集
   */
  private final Collection<T> data;

  /**
   * 构造函数
   *
   * @param errorCode 错误码
   * @param data      泛型集合
   */
  public PageResponse(ErrorCode errorCode, Collection<T> data) {
    super(errorCode);
    this.data = data;
  }

  /**
   * 返回一个失败的结果
   *
   * @param errorCode 错误码
   * @return 结果
   */
  public static PageResponse<?> of(ErrorCode errorCode) {
    return of(errorCode, null);
  }

  /**
   * 返回一个失败的结果
   *
   * @param errorCode 错误码
   * @param data      泛型集合
   * @return 结果
   */
  public static <T> PageResponse<T> of(ErrorCode errorCode, Collection<T> data) {
    return new PageResponse<>(errorCode, data);
  }

  /**
   * 返回一个成功的结果
   *
   * @param data 泛型集合
   * @return 结果
   */
  public static <T> PageResponse<T> of(Collection<T> data) {
    return of(ErrorCodeDefaults.SUCCESS, data);
  }

  /**
   * 返回一个成功的结果
   *
   * @param data  泛型集合
   * @param total 总记录数
   * @return 结果
   */
  public static <T> PageResponse<?> of(Collection<T> data, long total) {
    return of(ErrorCodeDefaults.SUCCESS, data).setTotal(total);
  }

  /**
   * 返回一个成功的结果
   *
   * @return 结果
   */
  public static PageResponse<?> of() {
    return of(ErrorCodeDefaults.SUCCESS, null);
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
  public PageResponse<T> setHeaders(HttpHeaders headers) {
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
  public PageResponse setTotal(long total) {
    this.total = total;
    return this;
  }

  public List<T> getData() {
    if (null == data) {
      return Collections.emptyList();
    }
    if (data instanceof List) {
      return (List<T>) data;
    }
    return new ArrayList<>(data);
  }

}
