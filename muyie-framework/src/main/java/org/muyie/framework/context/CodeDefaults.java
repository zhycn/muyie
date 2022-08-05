package org.muyie.framework.context;

/**
 * 默认的错误码信息
 */
public enum CodeDefaults implements ICode {

  // HTTP Response Code

  SC_00000("00000", "成功"),

  SC_00400("00400", "非法参数"),

  SC_00401("00401", "未授权"),

  SC_00403("00403", "请求被拒绝"),

  SC_00404("00404", "资源未找到"),

  SC_00405("00405", "请求方法不支持"),

  SC_00408("00408", "请求超时"),

  SC_00409("00409", "非法访问"),

  SC_00415("00415", "媒体类型不支持"),

  SC_00429("00429", "请求频次超限"),

  SC_00500("00500", "服务器内部错误"),

  SC_00504("00504", "网关超时"),

  // Customize Response Code

  SC_99990("99990", "记录不存在"),

  SC_99991("99991", "远程请求异常"),

  SC_99992("99992", "参数错误"),

  SC_99993("99993", "已超时，请重新登录"),

  SC_99994("99994", "未授权"),

  SC_99995("99995", "非法访问"),

  SC_99996("99996", "系统繁忙，请稍候再试"),

  SC_99997("99997", "登录名或密码错误"),

  SC_99998("99998", "查询失败"),

  SC_99999("99999", "系统错误"),

  ;

  private final String code;
  private final String msg;

  CodeDefaults(String code, String msg) {
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
