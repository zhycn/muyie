package org.muyie.framework.context;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import cn.hutool.core.util.StrUtil;

/**
 * Returns the Response
 */
public class IResult implements Serializable {

  private static final long serialVersionUID = 1L;
  private String code;
  private String msg;
  private Object result;
  @JsonIgnore
  private HttpHeaders headers;

  public IResult() {
    super();
  }

  private IResult(String code, String msg, Object result) {
    this.code = code;
    this.msg = msg;
    this.result = result;
  }

  public static IResult fail(@NonNull ICode code) {
    return fail(code, null, null);
  }

  public static IResult fail(@NonNull ICode code,
                             @Nullable MultiValueMap<String, String> headers) {
    return fail(code, null, headers);
  }

  public static IResult fail(@NonNull ICode code, @Nullable Object result) {
    return fail(code, result, null);
  }

  public static IResult fail(@NonNull ICode code, @Nullable Object result,
                             @Nullable MultiValueMap<String, String> headers) {
    IResult IResult = new IResult(code.getCode(), code.getMsg(), result);
    HttpHeaders tempHeaders = new HttpHeaders();
    if (headers != null) {
      tempHeaders.putAll(headers);
    }
    IResult.setHeaders(HttpHeaders.readOnlyHttpHeaders(tempHeaders));
    return IResult;
  }

  public static IResult fail(String code, String msg, Map<?, ?> map) {
    CodeBuilder builder = CodeBuilder.of(code, StrUtil.format(msg, map));
    return fail(builder);
  }

  public static IResult fail(String code, String msg, Object... params) {
    CodeBuilder builder = CodeBuilder.of(code, StrUtil.format(msg, params));
    return fail(builder);
  }

  public static IResult success() {
    return success(null, null);
  }

  public static IResult success(@Nullable MultiValueMap<String, String> headers) {
    return success(null, headers);
  }

  public static IResult success(@Nullable Object result) {
    return success(result, null);
  }

  public static IResult success(@Nullable Object result,
                                @Nullable MultiValueMap<String, String> headers) {
    return fail(CodeDefaults.SC_00000, result, headers);
  }

  public String getCode() {
    return Strings.nullToEmpty(code);
  }

  public void setCode(String code) {
    this.code = code;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  public void setHeaders(HttpHeaders headers) {
    this.headers = headers;
  }

  public String getMsg() {
    return Strings.nullToEmpty(msg);
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getResult() {
    return Objects.nonNull(result) ? result : Maps.newConcurrentMap();
  }

  public void setResult(Object result) {
    this.result = result;
  }

  public boolean isSuccess() {
    return StringUtils.equals(CodeDefaults.SC_00000.getCode(), getCode());
  }

}
