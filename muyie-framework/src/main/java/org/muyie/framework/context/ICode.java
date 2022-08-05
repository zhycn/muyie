package org.muyie.framework.context;

/**
 * 返回错误码接口定义
 */
public interface ICode {
  /**
   * 错误码
   *
   * @return 错误码
   */
  String getCode();

  /**
   * 错误信息
   *
   * @return 错误信息
   */
  String getMsg();

}
