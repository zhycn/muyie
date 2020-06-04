package org.muyie.framework.context;

/**
 * Defaults of Response code
 */
public enum ResponseCodeDefaults implements ResponseCode {

  // HTTP Response Code

  SC_200("200", "成功"),

  SC_400("400", "非法参数错误"),

  SC_401("401", "未授权"),

  SC_403("403", "请求被拒绝"),

  SC_404("404", "资源未找到"),

  SC_405("405", "请求方法不支持"),

  SC_408("408", "请求超时"),

  SC_409("409", "访问冲突"),

  SC_415("415", "媒体类型不支持"),

  SC_429("429", "请求频率超限"),

  SC_500("500", "服务器内部错误"),

  SC_504("504", "网关超时"),

  // Customize Response Code

  SC_9996("9996", "系统繁忙，请稍候再试！"),

  SC_9999("9999", "系统错误"),

  ;

  private final String code;
  private final String msg;

  private ResponseCodeDefaults(String code, String msg) {
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
