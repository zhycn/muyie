package org.muyie.framework.http;

import java.util.Map;

import cn.hutool.core.util.StrUtil;

/**
 * Builds of Result code
 */
public class ResultCodeBuilder implements ResultCode {

  public static ResultCodeBuilder of(ResultCode resultCode) {
    return of(resultCode.getCode(), resultCode.getMsg());
  }

  public static ResultCodeBuilder of(ResultCode resultCode, Map<?, ?> map) {
    return of(resultCode.getCode(), StrUtil.format(resultCode.getMsg(), map));
  }

  public static ResultCodeBuilder of(ResultCode resultCode, Object... params) {
    return of(resultCode.getCode(), StrUtil.format(resultCode.getMsg(), params));
  }

  public static ResultCodeBuilder of(String code, String msg) {
    return new ResultCodeBuilder(code, msg);
  }

  public static ResultCodeBuilder of(String code, String msg, Map<?, ?> map) {
    return new ResultCodeBuilder(code, StrUtil.format(msg, map));
  }

  public static ResultCodeBuilder of(String code, String msg, Object... params) {
    return new ResultCodeBuilder(code, StrUtil.format(msg, params));
  }

  private final String code;
  private final String msg;

  private ResultCodeBuilder(String code, String msg) {
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
