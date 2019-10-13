package org.muyie.framework.http;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.hutool.core.util.StrUtil;

/**
 * Returns the Result
 */
public class Result implements Serializable {

  private static final long serialVersionUID = 1L;

  public static Result of(@NonNull ResultCode resultCode) {
    return of(resultCode, null, null);
  }

  public static Result of(@NonNull ResultCode resultCode,
      @Nullable MultiValueMap<String, String> headers) {
    return of(resultCode, null, headers);
  }

  public static Result of(@NonNull ResultCode resultCode, @Nullable Object bizContent) {
    return of(resultCode, bizContent, null);
  }

  public static Result of(@NonNull ResultCode resultCode, @Nullable Object bizContent,
      @Nullable MultiValueMap<String, String> headers) {
    Result result = new Result(resultCode.getCode(), resultCode.getMsg(), bizContent);
    HttpHeaders tempHeaders = new HttpHeaders();
    if (headers != null) {
      tempHeaders.putAll(headers);
    }
    result.setHeaders(HttpHeaders.readOnlyHttpHeaders(tempHeaders));
    return result;
  }

  public static Result of(String code, String msg, Map<?, ?> map) {
    ResultCodeBuilder builder = ResultCodeBuilder.of(code, StrUtil.format(msg, map));
    return of(builder);
  }

  public static Result of(String code, String msg, Object... params) {
    ResultCodeBuilder builder = ResultCodeBuilder.of(code, StrUtil.format(msg, params));
    return of(builder);
  }

  public static Result ok() {
    return ok(null, null);
  }

  public static Result ok(@Nullable MultiValueMap<String, String> headers) {
    return ok(null, headers);
  }

  public static Result ok(@Nullable Object bizContent) {
    return ok(bizContent, null);
  }

  public static Result ok(@Nullable Object bizContent,
      @Nullable MultiValueMap<String, String> headers) {
    return of(ResultCodeDefined.SC_200, bizContent, headers);
  }

  private String code;

  private String msg;

  private Object bizContent;

  @JsonIgnore
  private HttpHeaders headers;

  private String sign;

  public Result() {
    super();
  }

  private Result(String code, String msg, Object bizContent) {
    this.code = code;
    this.msg = msg;
    this.bizContent = bizContent;
  }

  public Object getBizContent() {
    return Objects.nonNull(bizContent) ? bizContent : Maps.newConcurrentMap();
  }

  public String getCode() {
    return Strings.nullToEmpty(code);
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  public String getMsg() {
    return Strings.nullToEmpty(msg);
  }

  public String getSign() {
    return Strings.nullToEmpty(sign);
  }

  public boolean isSuccess() {
    return ResultCodeDefined.SC_200.getCode().equals(getCode());
  }

  public void setBizContent(Object bizContent) {
    this.bizContent = bizContent;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setHeaders(HttpHeaders headers) {
    this.headers = headers;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

}
