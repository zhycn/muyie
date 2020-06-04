package org.muyie.framework.context;

import java.util.Map;

import cn.hutool.core.util.StrUtil;

/**
 * Builds of Response code
 */
public class ResponseCodeBuilder implements ResponseCode {

  public static ResponseCodeBuilder of(ResponseCode responseCode) {
    return of(responseCode.getCode(), responseCode.getMsg());
  }

  public static ResponseCodeBuilder of(ResponseCode responseCode, Map<?, ?> map) {
    return of(responseCode.getCode(), StrUtil.format(responseCode.getMsg(), map));
  }

  public static ResponseCodeBuilder of(ResponseCode responseCode, Object... params) {
    return of(responseCode.getCode(), StrUtil.format(responseCode.getMsg(), params));
  }

  public static ResponseCodeBuilder of(String code, String msg) {
    return new ResponseCodeBuilder(code, msg);
  }

  public static ResponseCodeBuilder of(String code, String msg, Map<?, ?> map) {
    return new ResponseCodeBuilder(code, StrUtil.format(msg, map));
  }

  public static ResponseCodeBuilder of(String code, String msg, Object... params) {
    return new ResponseCodeBuilder(code, StrUtil.format(msg, params));
  }

  private final String code;
  private final String msg;

  private ResponseCodeBuilder(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMsg() {
    return msg;
  }

}
