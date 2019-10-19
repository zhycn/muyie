package org.muyie.framework.http;

/**
 * Defaults of Response code
 */
public enum ResponseCodeDefaults implements ResponseCode {

  // HTTP Response Code

  SC_200("200", "成功"),

  SC_400("400", "请求参数错误"),

  SC_401("401", "未授权"),

  SC_403("403", "服务器拒绝请求"),

  SC_404("404", "未找到"),

  SC_405("405", "请求方法不支持"),

  SC_408("408", "请求超时"),

  SC_409("409", "访问冲突"),

  SC_415("415", "媒体类型不支持"),

  SC_429("429", "请求频率超限"),

  SC_500("500", "服务器内部错误"),

  SC_504("504", "网关超时"),

  // Customize Response Code

  SC_991("991", "操作错误"),

  SC_992("992", "非法访问"),

  SC_993("993", "资源已存在"),

  SC_994("994", "网络请求超时"),

  SC_995("995", "网络异常"),

  SC_996("996", "系统繁忙，请稍候再试！"),

  SC_997("997", "远程服务错误"),

  SC_998("998", "数据库错误"),

  SC_999("999", "未知错误"),

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
