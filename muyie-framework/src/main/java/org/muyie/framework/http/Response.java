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
 * Returns the Response
 */
public class Response implements Serializable {

  private static final long serialVersionUID = 1L;

  public static Response fail(@NonNull ResponseCode responseCode) {
    return fail(responseCode, null, null);
  }

  public static Response fail(@NonNull ResponseCode responseCode,
      @Nullable MultiValueMap<String, String> headers) {
    return fail(responseCode, null, headers);
  }

  public static Response fail(@NonNull ResponseCode responseCode, @Nullable Object result) {
    return fail(responseCode, result, null);
  }

  public static Response fail(@NonNull ResponseCode responseCode, @Nullable Object result,
      @Nullable MultiValueMap<String, String> headers) {
    Response response = new Response(responseCode.getCode(), responseCode.getMsg(), result);
    HttpHeaders tempHeaders = new HttpHeaders();
    if (headers != null) {
      tempHeaders.putAll(headers);
    }
    response.setHeaders(HttpHeaders.readOnlyHttpHeaders(tempHeaders));
    return response;
  }

  public static Response fail(String code, String msg, Map<?, ?> map) {
    ResponseCodeBuilder builder = ResponseCodeBuilder.of(code, StrUtil.format(msg, map));
    return fail(builder);
  }

  public static Response fail(String code, String msg, Object... params) {
    ResponseCodeBuilder builder = ResponseCodeBuilder.of(code, StrUtil.format(msg, params));
    return fail(builder);
  }

  public static Response success() {
    return success(null, null);
  }

  public static Response success(@Nullable MultiValueMap<String, String> headers) {
    return success(null, headers);
  }

  public static Response success(@Nullable Object result) {
    return success(result, null);
  }

  public static Response success(@Nullable Object result,
      @Nullable MultiValueMap<String, String> headers) {
    return fail(ResponseCodeDefaults.SC_200, result, headers);
  }

  private String code;

  private String msg;

  private Object result;

  @JsonIgnore
  private HttpHeaders headers;

  public Response() {
    super();
  }

  private Response(String code, String msg, Object result) {
    this.code = code;
    this.msg = msg;
    this.result = result;
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

  public Object getResult() {
    return Objects.nonNull(result) ? result : Maps.newConcurrentMap();
  }

  public boolean isSuccess() {
    return ResponseCodeDefaults.SC_200.getCode().equals(getCode());
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

  public void setResult(Object result) {
    this.result = result;
  }

}
