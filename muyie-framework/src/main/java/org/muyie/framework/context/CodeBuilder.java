package org.muyie.framework.context;

import java.util.Map;

import cn.hutool.core.util.StrUtil;

/**
 * 构建错误码
 */
public class CodeBuilder implements ICode {

  private final String code;
  private final String msg;

  private CodeBuilder(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public static CodeBuilder of(ICode code) {
    return of(code.getCode(), code.getMsg());
  }

  public static CodeBuilder of(ICode code, Map<?, ?> map) {
    return of(code.getCode(), StrUtil.format(code.getMsg(), map));
  }

  public static CodeBuilder of(ICode code, Object... params) {
    return of(code.getCode(), StrUtil.format(code.getMsg(), params));
  }

  public static CodeBuilder of(String code, String msg) {
    return new CodeBuilder(code, msg);
  }

  public static CodeBuilder of(String code, String msg, Map<?, ?> map) {
    return new CodeBuilder(code, StrUtil.format(msg, map));
  }

  public static CodeBuilder of(String code, String msg, Object... params) {
    return new CodeBuilder(code, StrUtil.format(msg, params));
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
