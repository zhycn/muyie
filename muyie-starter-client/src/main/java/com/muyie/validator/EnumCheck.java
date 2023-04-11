package com.muyie.validator;

/**
 * 使用自定义枚举校验需实现该接口
 *
 * @author larry
 */
public interface EnumCheck {

  /**
   * 判断枚举值是否有效
   *
   * @param value 请求校验参数
   * @return true-校验通过 false-校验失败
   */
  boolean isValid(String value);

}
